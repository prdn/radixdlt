package com.radixdlt.client.core;

import com.radixdlt.client.core.address.RadixUniverseConfig;
import com.radixdlt.client.core.address.RadixUniverseConfigs;
import com.radixdlt.client.core.network.PeerDiscovery;
import com.radixdlt.client.core.network.PeersFromNodeFinder;
import com.radixdlt.client.core.network.PeersFromSeed;
import com.radixdlt.client.core.network.RadixPeer;
import java.util.function.Supplier;

public enum Bootstrap implements BootstrapConfig {
	BETANET(
		RadixUniverseConfigs::getBetanet,
			() -> new PeersFromSeed(new RadixPeer("localhost", false, 8080))
	),
	ALPHANET(
		RadixUniverseConfigs::getAlphanet,
			() -> new PeersFromNodeFinder("https://alphanet.radixdlt.com/node-finder", 443)
	),
	HIGHGARDEN(
		RadixUniverseConfigs::getHighgarden,
			() -> new PeersFromNodeFinder("https://highgarden.radixdlt.com/node-finder", 443)
	),
	SUNSTONE(
		RadixUniverseConfigs::getSunstone,
			() -> new PeersFromNodeFinder("https://sunstone.radixdlt.com/node-finder", 443)
	),
	WINTERFELL(
		RadixUniverseConfigs::getWinterfell,
			() -> new PeersFromSeed(new RadixPeer("52.190.0.18", false, 8080))
	),
	WINTERFELL_LOCAL(
		RadixUniverseConfigs::getWinterfell,
			() -> new PeersFromSeed(new RadixPeer("localhost", false, 8080))
	);

	private final Supplier<RadixUniverseConfig> config;
	private final Supplier<PeerDiscovery> discovery;

	Bootstrap(Supplier<RadixUniverseConfig> config, Supplier<PeerDiscovery> discoverySupplier) {
		this.config = config;
		this.discovery = discoverySupplier;
	}

	public RadixUniverseConfig getConfig() {
		return config.get();
	}

	public PeerDiscovery getDiscovery() {
		return discovery.get();
	}
}
