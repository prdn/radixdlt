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
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


/**
 * UnexpectedErrorDetails
 */
@JsonPropertyOrder({
  UnexpectedErrorDetails.JSON_PROPERTY_CAUSE,
  UnexpectedErrorDetails.JSON_PROPERTY_EXCEPTION
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-30T13:55:26.722890-06:00[America/Chicago]")
public class UnexpectedErrorDetails {
  public static final String JSON_PROPERTY_CAUSE = "cause";
  private String cause;

  public static final String JSON_PROPERTY_EXCEPTION = "exception";
  private String exception;


  public UnexpectedErrorDetails cause(String cause) {
    this.cause = cause;
    return this;
  }

   /**
   * Get cause
   * @return cause
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_CAUSE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getCause() {
    return cause;
  }


  @JsonProperty(JSON_PROPERTY_CAUSE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCause(String cause) {
    this.cause = cause;
  }


  public UnexpectedErrorDetails exception(String exception) {
    this.exception = exception;
    return this;
  }

   /**
   * Get exception
   * @return exception
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_EXCEPTION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getException() {
    return exception;
  }


  @JsonProperty(JSON_PROPERTY_EXCEPTION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setException(String exception) {
    this.exception = exception;
  }


  /**
   * Return true if this UnexpectedError_details object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UnexpectedErrorDetails unexpectedErrorDetails = (UnexpectedErrorDetails) o;
    return Objects.equals(this.cause, unexpectedErrorDetails.cause) &&
        Objects.equals(this.exception, unexpectedErrorDetails.exception);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cause, exception);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnexpectedErrorDetails {\n");
    sb.append("    cause: ").append(toIndentedString(cause)).append("\n");
    sb.append("    exception: ").append(toIndentedString(exception)).append("\n");
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
