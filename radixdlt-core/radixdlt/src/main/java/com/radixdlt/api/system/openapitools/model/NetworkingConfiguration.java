/*
 * Radix System API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.radixdlt.api.system.openapitools.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * NetworkingConfiguration
 */
@JsonPropertyOrder({
  NetworkingConfiguration.JSON_PROPERTY_DEFAULT_PORT,
  NetworkingConfiguration.JSON_PROPERTY_DISCOVERY_INTERVAL,
  NetworkingConfiguration.JSON_PROPERTY_LISTEN_ADDRESS,
  NetworkingConfiguration.JSON_PROPERTY_LISTEN_PORT,
  NetworkingConfiguration.JSON_PROPERTY_BROADCAST_PORT,
  NetworkingConfiguration.JSON_PROPERTY_PEER_CONNECTION_TIMEOUT,
  NetworkingConfiguration.JSON_PROPERTY_MAX_INBOUND_CHANNELS,
  NetworkingConfiguration.JSON_PROPERTY_MAX_OUTBOUND_CHANNELS,
  NetworkingConfiguration.JSON_PROPERTY_CHANNEL_BUFFER_SIZE,
  NetworkingConfiguration.JSON_PROPERTY_PEER_LIVENESS_CHECK_INTERVAL,
  NetworkingConfiguration.JSON_PROPERTY_PING_TIMEOUT,
  NetworkingConfiguration.JSON_PROPERTY_SEED_NODES,
  NetworkingConfiguration.JSON_PROPERTY_NODE_ADDRESS
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-26T15:21:53.191235-06:00[America/Chicago]")
public class NetworkingConfiguration {
  public static final String JSON_PROPERTY_DEFAULT_PORT = "default_port";
  private Integer defaultPort;

  public static final String JSON_PROPERTY_DISCOVERY_INTERVAL = "discovery_interval";
  private Long discoveryInterval;

  public static final String JSON_PROPERTY_LISTEN_ADDRESS = "listen_address";
  private String listenAddress;

  public static final String JSON_PROPERTY_LISTEN_PORT = "listen_port";
  private Integer listenPort;

  public static final String JSON_PROPERTY_BROADCAST_PORT = "broadcast_port";
  private Integer broadcastPort;

  public static final String JSON_PROPERTY_PEER_CONNECTION_TIMEOUT = "peer_connection_timeout";
  private Integer peerConnectionTimeout;

  public static final String JSON_PROPERTY_MAX_INBOUND_CHANNELS = "max_inbound_channels";
  private Integer maxInboundChannels;

  public static final String JSON_PROPERTY_MAX_OUTBOUND_CHANNELS = "max_outbound_channels";
  private Integer maxOutboundChannels;

  public static final String JSON_PROPERTY_CHANNEL_BUFFER_SIZE = "channel_buffer_size";
  private Integer channelBufferSize;

  public static final String JSON_PROPERTY_PEER_LIVENESS_CHECK_INTERVAL = "peer_liveness_check_interval";
  private Long peerLivenessCheckInterval;

  public static final String JSON_PROPERTY_PING_TIMEOUT = "ping_timeout";
  private Long pingTimeout;

  public static final String JSON_PROPERTY_SEED_NODES = "seed_nodes";
  private List<String> seedNodes = new ArrayList<>();

  public static final String JSON_PROPERTY_NODE_ADDRESS = "node_address";
  private String nodeAddress;


  public NetworkingConfiguration defaultPort(Integer defaultPort) {
    this.defaultPort = defaultPort;
    return this;
  }

   /**
   * Get defaultPort
   * @return defaultPort
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_DEFAULT_PORT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getDefaultPort() {
    return defaultPort;
  }


  @JsonProperty(JSON_PROPERTY_DEFAULT_PORT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDefaultPort(Integer defaultPort) {
    this.defaultPort = defaultPort;
  }


  public NetworkingConfiguration discoveryInterval(Long discoveryInterval) {
    this.discoveryInterval = discoveryInterval;
    return this;
  }

   /**
   * Get discoveryInterval
   * @return discoveryInterval
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_DISCOVERY_INTERVAL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getDiscoveryInterval() {
    return discoveryInterval;
  }


  @JsonProperty(JSON_PROPERTY_DISCOVERY_INTERVAL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDiscoveryInterval(Long discoveryInterval) {
    this.discoveryInterval = discoveryInterval;
  }


  public NetworkingConfiguration listenAddress(String listenAddress) {
    this.listenAddress = listenAddress;
    return this;
  }

   /**
   * Get listenAddress
   * @return listenAddress
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_LISTEN_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getListenAddress() {
    return listenAddress;
  }


  @JsonProperty(JSON_PROPERTY_LISTEN_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setListenAddress(String listenAddress) {
    this.listenAddress = listenAddress;
  }


  public NetworkingConfiguration listenPort(Integer listenPort) {
    this.listenPort = listenPort;
    return this;
  }

   /**
   * Get listenPort
   * @return listenPort
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_LISTEN_PORT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getListenPort() {
    return listenPort;
  }


  @JsonProperty(JSON_PROPERTY_LISTEN_PORT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setListenPort(Integer listenPort) {
    this.listenPort = listenPort;
  }


  public NetworkingConfiguration broadcastPort(Integer broadcastPort) {
    this.broadcastPort = broadcastPort;
    return this;
  }

   /**
   * Get broadcastPort
   * @return broadcastPort
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_BROADCAST_PORT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getBroadcastPort() {
    return broadcastPort;
  }


  @JsonProperty(JSON_PROPERTY_BROADCAST_PORT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setBroadcastPort(Integer broadcastPort) {
    this.broadcastPort = broadcastPort;
  }


  public NetworkingConfiguration peerConnectionTimeout(Integer peerConnectionTimeout) {
    this.peerConnectionTimeout = peerConnectionTimeout;
    return this;
  }

   /**
   * Get peerConnectionTimeout
   * @return peerConnectionTimeout
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_PEER_CONNECTION_TIMEOUT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getPeerConnectionTimeout() {
    return peerConnectionTimeout;
  }


  @JsonProperty(JSON_PROPERTY_PEER_CONNECTION_TIMEOUT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPeerConnectionTimeout(Integer peerConnectionTimeout) {
    this.peerConnectionTimeout = peerConnectionTimeout;
  }


  public NetworkingConfiguration maxInboundChannels(Integer maxInboundChannels) {
    this.maxInboundChannels = maxInboundChannels;
    return this;
  }

   /**
   * Get maxInboundChannels
   * @return maxInboundChannels
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_MAX_INBOUND_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getMaxInboundChannels() {
    return maxInboundChannels;
  }


  @JsonProperty(JSON_PROPERTY_MAX_INBOUND_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMaxInboundChannels(Integer maxInboundChannels) {
    this.maxInboundChannels = maxInboundChannels;
  }


  public NetworkingConfiguration maxOutboundChannels(Integer maxOutboundChannels) {
    this.maxOutboundChannels = maxOutboundChannels;
    return this;
  }

   /**
   * Get maxOutboundChannels
   * @return maxOutboundChannels
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_MAX_OUTBOUND_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getMaxOutboundChannels() {
    return maxOutboundChannels;
  }


  @JsonProperty(JSON_PROPERTY_MAX_OUTBOUND_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMaxOutboundChannels(Integer maxOutboundChannels) {
    this.maxOutboundChannels = maxOutboundChannels;
  }


  public NetworkingConfiguration channelBufferSize(Integer channelBufferSize) {
    this.channelBufferSize = channelBufferSize;
    return this;
  }

   /**
   * Get channelBufferSize
   * @return channelBufferSize
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_CHANNEL_BUFFER_SIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getChannelBufferSize() {
    return channelBufferSize;
  }


  @JsonProperty(JSON_PROPERTY_CHANNEL_BUFFER_SIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setChannelBufferSize(Integer channelBufferSize) {
    this.channelBufferSize = channelBufferSize;
  }


  public NetworkingConfiguration peerLivenessCheckInterval(Long peerLivenessCheckInterval) {
    this.peerLivenessCheckInterval = peerLivenessCheckInterval;
    return this;
  }

   /**
   * Get peerLivenessCheckInterval
   * @return peerLivenessCheckInterval
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_PEER_LIVENESS_CHECK_INTERVAL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getPeerLivenessCheckInterval() {
    return peerLivenessCheckInterval;
  }


  @JsonProperty(JSON_PROPERTY_PEER_LIVENESS_CHECK_INTERVAL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPeerLivenessCheckInterval(Long peerLivenessCheckInterval) {
    this.peerLivenessCheckInterval = peerLivenessCheckInterval;
  }


  public NetworkingConfiguration pingTimeout(Long pingTimeout) {
    this.pingTimeout = pingTimeout;
    return this;
  }

   /**
   * Get pingTimeout
   * @return pingTimeout
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_PING_TIMEOUT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getPingTimeout() {
    return pingTimeout;
  }


  @JsonProperty(JSON_PROPERTY_PING_TIMEOUT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPingTimeout(Long pingTimeout) {
    this.pingTimeout = pingTimeout;
  }


  public NetworkingConfiguration seedNodes(List<String> seedNodes) {
    this.seedNodes = seedNodes;
    return this;
  }

  public NetworkingConfiguration addSeedNodesItem(String seedNodesItem) {
    this.seedNodes.add(seedNodesItem);
    return this;
  }

   /**
   * Get seedNodes
   * @return seedNodes
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_SEED_NODES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<String> getSeedNodes() {
    return seedNodes;
  }


  @JsonProperty(JSON_PROPERTY_SEED_NODES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSeedNodes(List<String> seedNodes) {
    this.seedNodes = seedNodes;
  }


  public NetworkingConfiguration nodeAddress(String nodeAddress) {
    this.nodeAddress = nodeAddress;
    return this;
  }

   /**
   * Get nodeAddress
   * @return nodeAddress
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_NODE_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getNodeAddress() {
    return nodeAddress;
  }


  @JsonProperty(JSON_PROPERTY_NODE_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNodeAddress(String nodeAddress) {
    this.nodeAddress = nodeAddress;
  }


  /**
   * Return true if this NetworkingConfiguration object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NetworkingConfiguration networkingConfiguration = (NetworkingConfiguration) o;
    return Objects.equals(this.defaultPort, networkingConfiguration.defaultPort) &&
        Objects.equals(this.discoveryInterval, networkingConfiguration.discoveryInterval) &&
        Objects.equals(this.listenAddress, networkingConfiguration.listenAddress) &&
        Objects.equals(this.listenPort, networkingConfiguration.listenPort) &&
        Objects.equals(this.broadcastPort, networkingConfiguration.broadcastPort) &&
        Objects.equals(this.peerConnectionTimeout, networkingConfiguration.peerConnectionTimeout) &&
        Objects.equals(this.maxInboundChannels, networkingConfiguration.maxInboundChannels) &&
        Objects.equals(this.maxOutboundChannels, networkingConfiguration.maxOutboundChannels) &&
        Objects.equals(this.channelBufferSize, networkingConfiguration.channelBufferSize) &&
        Objects.equals(this.peerLivenessCheckInterval, networkingConfiguration.peerLivenessCheckInterval) &&
        Objects.equals(this.pingTimeout, networkingConfiguration.pingTimeout) &&
        Objects.equals(this.seedNodes, networkingConfiguration.seedNodes) &&
        Objects.equals(this.nodeAddress, networkingConfiguration.nodeAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(defaultPort, discoveryInterval, listenAddress, listenPort, broadcastPort, peerConnectionTimeout, maxInboundChannels, maxOutboundChannels, channelBufferSize, peerLivenessCheckInterval, pingTimeout, seedNodes, nodeAddress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NetworkingConfiguration {\n");
    sb.append("    defaultPort: ").append(toIndentedString(defaultPort)).append("\n");
    sb.append("    discoveryInterval: ").append(toIndentedString(discoveryInterval)).append("\n");
    sb.append("    listenAddress: ").append(toIndentedString(listenAddress)).append("\n");
    sb.append("    listenPort: ").append(toIndentedString(listenPort)).append("\n");
    sb.append("    broadcastPort: ").append(toIndentedString(broadcastPort)).append("\n");
    sb.append("    peerConnectionTimeout: ").append(toIndentedString(peerConnectionTimeout)).append("\n");
    sb.append("    maxInboundChannels: ").append(toIndentedString(maxInboundChannels)).append("\n");
    sb.append("    maxOutboundChannels: ").append(toIndentedString(maxOutboundChannels)).append("\n");
    sb.append("    channelBufferSize: ").append(toIndentedString(channelBufferSize)).append("\n");
    sb.append("    peerLivenessCheckInterval: ").append(toIndentedString(peerLivenessCheckInterval)).append("\n");
    sb.append("    pingTimeout: ").append(toIndentedString(pingTimeout)).append("\n");
    sb.append("    seedNodes: ").append(toIndentedString(seedNodes)).append("\n");
    sb.append("    nodeAddress: ").append(toIndentedString(nodeAddress)).append("\n");
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

}
