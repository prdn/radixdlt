package com.radixdlt.test;

import com.google.common.collect.Lists;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import utils.CmdHelper;
import utils.Generic;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.radixdlt.test.AssertionChecks.latentTestBuilder;
import static com.radixdlt.test.AssertionChecks.outOfSynchronyTestBuilder;

@RunWith(JUnitParamsRunner.class)
@Category(Docker.class)
public class DockerTests {

    private static final Logger logger = LogManager.getLogger();

    @Rule
    public TestName testNameRule = new TestName();

    private String testMethodName;

    @Before
    public void setup() {
        testMethodName = Generic.extractTestName(testNameRule.getMethodName());
        logger.info("Test name is {}", testMethodName);
    }

    @Test
    public void smoke_test() {
        try (DockerNetwork network = DockerNetwork.builder().numNodes(4).testName(testMethodName).startConsensusOnBoot().build()) {
            network.startBlocking();
            Conditions.waitUntilNetworkHasLiveness(network);

            // make all nodes slow/latent
            network.getNodeIds().forEach(nodeId -> CmdHelper.setupQueueQuality(nodeId, "delay 100ms loss 20%"));

            // first check
            RemoteBFTTest test = latentTestBuilder()
                    .network(RemoteBFTNetworkBridge.of(network))
                    .waitUntilResponsive()
                    .startConsensusOnRun()
                    .build();
            test.runBlocking(CmdHelper.getTestDurationInSeconds(), TimeUnit.SECONDS);

            String nodeToStopAndStart = network.getNodeIds().iterator().next();
            CmdHelper.stopContainer(nodeToStopAndStart);

            // second check, after the down is down
            RemoteBFTTest testOutOfSynchronyBounds = outOfSynchronyTestBuilder(Lists.newArrayList(nodeToStopAndStart))
                    .network(RemoteBFTNetworkBridge.of(network))
                    .build();
            testOutOfSynchronyBounds.runBlocking(CmdHelper.getTestDurationInSeconds(), TimeUnit.SECONDS);

            CmdHelper.startContainer(nodeToStopAndStart);

            // third check. The node that was brought back up should have liveness by itself (i.e. it should report an increasing view/epoch)
            List<String> restOfTheNodes = Lists.newArrayList(network.getNodeIds());
            restOfTheNodes.remove(nodeToStopAndStart);
            Conditions.waitUntilNetworkHasLiveness(network, restOfTheNodes);
        }
    }

    @Parameters({"4", "5"})
    @TestCaseName("given_{0}_correct_bfts_in_latent_docker_network__when_all_nodes_are_out_synchrony__then_a_liveness_check_should_fail")
    @Test
    public void given_X_correct_bfts_in_latent_docker_network__when_all_nodes_are_out_synchrony__then_a_liveness_check_should_fail(int numberOfNodes) {
        try (DockerNetwork network = DockerNetwork.builder().numNodes(numberOfNodes).testName(testMethodName).startConsensusOnBoot().build()) {
            network.startBlocking();

            Conditions.waitUntilNetworkHasLiveness(network);

            network.getNodeIds().forEach(nodeId -> CmdHelper.blockPort(nodeId, 30000)); // unfortunately a magic number, should be read from a config instead

            RemoteBFTTest testOutOfSynchronyBounds = RemoteBFTTest.builder().assertLiveness(20)
                    .network(RemoteBFTNetworkBridge.of(network)).build();

            AssertionError error = Assert.assertThrows(AssertionError.class, () ->
                    testOutOfSynchronyBounds.runBlocking(CmdHelper.getTestDurationInSeconds(), TimeUnit.SECONDS)
            );

            Generic.assertAssertionErrorIsLivenessError(error);
        }
    }

}
