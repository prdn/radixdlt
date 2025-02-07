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

import java.util.Objects;


/**
 * MempoolMetrics
 */
@JsonPropertyOrder({
  MempoolMetrics.JSON_PROPERTY_CURRENT_SIZE,
  MempoolMetrics.JSON_PROPERTY_ADD_SUCCESS,
  MempoolMetrics.JSON_PROPERTY_ADD_FAILURE,
  MempoolMetrics.JSON_PROPERTY_RELAYS_SENT
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-26T18:39:12.472872-06:00[America/Chicago]")
public class MempoolMetrics {
  public static final String JSON_PROPERTY_CURRENT_SIZE = "current_size";
  private Long currentSize;

  public static final String JSON_PROPERTY_ADD_SUCCESS = "add_success";
  private Long addSuccess;

  public static final String JSON_PROPERTY_ADD_FAILURE = "add_failure";
  private Long addFailure;

  public static final String JSON_PROPERTY_RELAYS_SENT = "relays_sent";
  private Long relaysSent;


  public MempoolMetrics currentSize(Long currentSize) {
    this.currentSize = currentSize;
    return this;
  }

   /**
   * Get currentSize
   * @return currentSize
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_CURRENT_SIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getCurrentSize() {
    return currentSize;
  }


  @JsonProperty(JSON_PROPERTY_CURRENT_SIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCurrentSize(Long currentSize) {
    this.currentSize = currentSize;
  }


  public MempoolMetrics addSuccess(Long addSuccess) {
    this.addSuccess = addSuccess;
    return this;
  }

   /**
   * Get addSuccess
   * @return addSuccess
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_ADD_SUCCESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getAddSuccess() {
    return addSuccess;
  }


  @JsonProperty(JSON_PROPERTY_ADD_SUCCESS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAddSuccess(Long addSuccess) {
    this.addSuccess = addSuccess;
  }


  public MempoolMetrics addFailure(Long addFailure) {
    this.addFailure = addFailure;
    return this;
  }

   /**
   * Get addFailure
   * @return addFailure
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_ADD_FAILURE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getAddFailure() {
    return addFailure;
  }


  @JsonProperty(JSON_PROPERTY_ADD_FAILURE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAddFailure(Long addFailure) {
    this.addFailure = addFailure;
  }


  public MempoolMetrics relaysSent(Long relaysSent) {
    this.relaysSent = relaysSent;
    return this;
  }

   /**
   * Get relaysSent
   * @return relaysSent
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_RELAYS_SENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getRelaysSent() {
    return relaysSent;
  }


  @JsonProperty(JSON_PROPERTY_RELAYS_SENT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRelaysSent(Long relaysSent) {
    this.relaysSent = relaysSent;
  }


  /**
   * Return true if this MempoolMetrics object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MempoolMetrics mempoolMetrics = (MempoolMetrics) o;
    return Objects.equals(this.currentSize, mempoolMetrics.currentSize) &&
        Objects.equals(this.addSuccess, mempoolMetrics.addSuccess) &&
        Objects.equals(this.addFailure, mempoolMetrics.addFailure) &&
        Objects.equals(this.relaysSent, mempoolMetrics.relaysSent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentSize, addSuccess, addFailure, relaysSent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MempoolMetrics {\n");
    sb.append("    currentSize: ").append(toIndentedString(currentSize)).append("\n");
    sb.append("    addSuccess: ").append(toIndentedString(addSuccess)).append("\n");
    sb.append("    addFailure: ").append(toIndentedString(addFailure)).append("\n");
    sb.append("    relaysSent: ").append(toIndentedString(relaysSent)).append("\n");
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

