/*
 * Radix Gateway API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 0.9.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.radixdlt.api.gateway.openapitools.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.radixdlt.api.gateway.openapitools.JSON;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * AccountStakesResponseSuccess
 */
@JsonPropertyOrder({
  AccountStakesResponseSuccess.JSON_PROPERTY_LEDGER_STATE,
  AccountStakesResponseSuccess.JSON_PROPERTY_STAKES
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T18:09:44.037426-06:00[America/Chicago]")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = AccountStakesResponseError.class, name = "AccountStakesResponseError"),
  @JsonSubTypes.Type(value = AccountStakesResponseSuccess.class, name = "AccountStakesResponseSuccess"),
})

public class AccountStakesResponseSuccess extends AccountStakesResponse {
  public static final String JSON_PROPERTY_LEDGER_STATE = "ledger_state";
  private LedgerState ledgerState;

  public static final String JSON_PROPERTY_STAKES = "stakes";
  private List<AccountStakeEntry> stakes = new ArrayList<>();


  public AccountStakesResponseSuccess ledgerState(LedgerState ledgerState) {
    this.ledgerState = ledgerState;
    return this;
  }

   /**
   * Get ledgerState
   * @return ledgerState
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_LEDGER_STATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public LedgerState getLedgerState() {
    return ledgerState;
  }


  @JsonProperty(JSON_PROPERTY_LEDGER_STATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLedgerState(LedgerState ledgerState) {
    this.ledgerState = ledgerState;
  }


  public AccountStakesResponseSuccess stakes(List<AccountStakeEntry> stakes) {
    this.stakes = stakes;
    return this;
  }

  public AccountStakesResponseSuccess addStakesItem(AccountStakeEntry stakesItem) {
    this.stakes.add(stakesItem);
    return this;
  }

   /**
   * Get stakes
   * @return stakes
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_STAKES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<AccountStakeEntry> getStakes() {
    return stakes;
  }


  @JsonProperty(JSON_PROPERTY_STAKES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStakes(List<AccountStakeEntry> stakes) {
    this.stakes = stakes;
  }


  /**
   * Return true if this AccountStakesResponseSuccess object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccountStakesResponseSuccess accountStakesResponseSuccess = (AccountStakesResponseSuccess) o;
    return Objects.equals(this.ledgerState, accountStakesResponseSuccess.ledgerState) &&
        Objects.equals(this.stakes, accountStakesResponseSuccess.stakes) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ledgerState, stakes, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccountStakesResponseSuccess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    ledgerState: ").append(toIndentedString(ledgerState)).append("\n");
    sb.append("    stakes: ").append(toIndentedString(stakes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

static {
  // Initialize and register the discriminator mappings.
  Map<String, Class<?>> mappings = new HashMap<String, Class<?>>();
  mappings.put("AccountStakesResponseError", AccountStakesResponseError.class);
  mappings.put("AccountStakesResponseSuccess", AccountStakesResponseSuccess.class);
  mappings.put("AccountStakesResponseSuccess", AccountStakesResponseSuccess.class);
  JSON.registerDiscriminator(AccountStakesResponseSuccess.class, "type", mappings);
}
}
