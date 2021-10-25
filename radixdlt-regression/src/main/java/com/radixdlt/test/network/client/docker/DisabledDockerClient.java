package com.radixdlt.test.network.client.docker;

import com.github.dockerjava.api.exception.DockerClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class DisabledDockerClient implements DockerClient {

    private static final Logger logger = LoggerFactory.getLogger(DisabledDockerClient.class);

    public void connect() {
        logger.debug("Docker client is disabled, cannot connect.");
    }

    public String runCommand(String nodeLocator, String... commands) {
        throw new DockerClientException("Disabled client cannot run command " + Arrays.toString(commands));
    }

    @Override
    public void restartNode(String nodeLocator) {
        throw new DockerClientException("Disabled client cannot restart node " + nodeLocator);
    }

    @Override
    public void stopNode(String nodeLocator) {
        throw new DockerClientException("Disabled client cannot stop node " + nodeLocator);
    }

    @Override
    public void cleanup(String... parameters) {
        logger.debug("Disabled docker client cannot cleanup");
    }

}
