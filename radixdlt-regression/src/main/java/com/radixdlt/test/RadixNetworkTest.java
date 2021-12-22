/* Copyright 2021 Radix Publishing Ltd incorporated in Jersey (Channel Islands).
 *
 * Licensed under the Radix License, Version 1.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at:
 *
 * radixfoundation.org/licenses/LICENSE-v1
 *
 * The Licensor hereby grants permission for the Canonical version of the Work to be
 * published, distributed and used under or by reference to the Licensor’s trademark
 * Radix ® and use of any unregistered trade names, logos or get-up.
 *
 * The Licensor provides the Work (and each Contributor provides its Contributions) on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
 * including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT,
 * MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Whilst the Work is capable of being deployed, used and adopted (instantiated) to create
 * a distributed ledger it is your responsibility to test and validate the code, together
 * with all logic and performance of that code under all foreseeable scenarios.
 *
 * The Licensor does not make or purport to make and hereby excludes liability for all
 * and any representation, warranty or undertaking in any form whatsoever, whether express
 * or implied, to any entity or person, including any representation, warranty or
 * undertaking, as to the functionality security use, value or other characteristics of
 * any distributed ledger nor in respect the functioning or value of any tokens which may
 * be created stored or transferred using the Work. The Licensor does not warrant that the
 * Work or any use of the Work complies with any law or regulation in any territory where
 * it may be implemented or used or that it will be appropriate for any specific purpose.
 *
 * Neither the licensor nor any current or former employees, officers, directors, partners,
 * trustees, representatives, agents, advisors, contractors, or volunteers of the Licensor
 * shall be liable for any direct or indirect, special, incidental, consequential or other
 * losses of any kind, in tort, contract or otherwise (including but not limited to loss
 * of revenue, income or profits, or loss of use or data, or loss of reputation, or loss
 * of any economic or other opportunity of whatsoever nature or howsoever arising), arising
 * out of or in connection with (without limitation of any use, misuse, of any ledger system
 * or use made or its functionality or any performance or operation of any code or protocol
 * caused by bugs or programming or logic errors or otherwise);
 *
 * A. any offer, purchase, holding, use, sale, exchange or transmission of any
 * cryptographic keys, tokens or assets created, exchanged, stored or arising from any
 * interaction with the Work;
 *
 * B. any failure in a transmission or loss of any token or assets keys or other digital
 * artefacts due to errors in transmission;
 *
 * C. bugs, hacks, logic errors or faults in the Work or any communication;
 *
 * D. system software or apparatus including but not limited to losses caused by errors
 * in holding or transmitting tokens by any third-party;
 *
 * E. breaches or failure of security including hacker attacks, loss or disclosure of
 * password, loss of private key, unauthorised use or misuse of such passwords or keys;
 *
 * F. any losses including loss of anticipated savings or other benefits resulting from
 * use of the Work or any changes to the Work (however implemented).
 *
 * You are solely responsible for; testing, validating and evaluation of all operation
 * logic, functionality, security and appropriateness of using the Work for any commercial
 * or non-commercial purpose and for any reproduction or redistribution by You of the
 * Work. You assume all risks associated with Your use of the Work and the exercise of
 * permissions under this License.
 */

package com.radixdlt.test;

import static org.awaitility.Awaitility.await;

import com.google.common.collect.Lists;
import com.radixdlt.application.tokens.Amount;
import com.radixdlt.client.lib.api.AccountAddress;
import com.radixdlt.client.lib.dto.Balance;
import com.radixdlt.identifiers.AID;
import com.radixdlt.test.account.Account;
import com.radixdlt.test.network.RadixNetwork;
import com.radixdlt.test.network.checks.CheckFailureException;
import com.radixdlt.test.network.checks.Checks;
import com.radixdlt.test.utils.TransactionUtils;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Durations;

public abstract class RadixNetworkTest {

  private static final Logger logger = LogManager.getLogger();

  protected final RadixNetwork radixNetwork;
  protected final Checks checks;
  private final List<Account> accounts;
  protected final Account account1;
  protected final Account account2;

  // since test steps happen synchronously, this variable is used to store some state between steps
  protected AID txBuffer;

  public RadixNetworkTest() {
    radixNetwork = RadixNetwork.initializeFromEnv();
    checks =
        Checks.forNodesAndCheckConfiguration(
            radixNetwork.getNodes(), radixNetwork.getConfiguration());
    accounts = Lists.newArrayList();
    IntStream.range(0, 6)
        .forEach(
            i -> {
              var account = radixNetwork.generateNewAccount();
              accounts.add(account);
            });
    account1 = accounts.get(0);
    account2 = accounts.get(1);
  }

  public Account getTestAccount(int number) {
    return accounts.get(number);
  }

  public String faucet(AccountAddress to) {
    return radixNetwork.faucet(to);
  }

  public RadixNetwork getNetwork() {
    return radixNetwork;
  }

  /**
   * Calls the faucet for the given account and waits for transaction confirmation
   *
   * @return the txID of the faucet's transaction
   */
  public String faucet(Account to) {
    Balance balanceBeforeFaucet = to.getOwnNativeTokenBalance();
    String txID = faucet(to.getAddress());
    TransactionUtils.waitForConfirmation(to, AID.from(txID));
    await()
        .atMost(Durations.TEN_SECONDS)
        .until(
            () ->
                // wait until the account's balance increases, just to be sure that the faucet
                // delivered something
                balanceBeforeFaucet.getAmount().compareTo(to.getOwnNativeTokenBalance().getAmount())
                    < 0);
    return txID;
  }

  /** Repeatedly calls the faucet until the given amount is credited */
  public void faucet(Account to, Amount amount) {
    Balance originalBalance = to.getOwnNativeTokenBalance();
    while (to.getOwnNativeTokenBalance()
            .getAmount()
            .subtract(originalBalance.getAmount())
            .compareTo(amount.toSubunits())
        < 0) {
      faucet(to);
    }
  }

  /**
   * @throws IllegalArgumentException if such a check does not exist
   * @throws CheckFailureException if the check failed
   */
  public void runCheck(String name, Object... variables) {
    boolean result = checks.runCheck(name, variables);
    if (!result) {
      throw new CheckFailureException(name);
    }
    logger.info("Check '{}' passed", name);
  }
}
