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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ValidatorInfoResponseSuccess
 */
@JsonPropertyOrder({
  ValidatorInfoResponseSuccess.JSON_PROPERTY_LEDGER_STATE,
  ValidatorInfoResponseSuccess.JSON_PROPERTY_VALIDATOR
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-12-01T23:17:20.933920-06:00[America/Chicago]")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = ValidatorInfoResponseError.class, name = "ValidatorInfoResponseError"),
  @JsonSubTypes.Type(value = ValidatorInfoResponseSuccess.class, name = "ValidatorInfoResponseSuccess"),
})

public class ValidatorInfoResponseSuccess extends ValidatorInfoResponse {
  public static final String JSON_PROPERTY_LEDGER_STATE = "ledger_state";
  private LedgerState ledgerState;

  public static final String JSON_PROPERTY_VALIDATOR = "validator";
  private Validator validator;


  public ValidatorInfoResponseSuccess ledgerState(LedgerState ledgerState) {
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


  public ValidatorInfoResponseSuccess validator(Validator validator) {
    this.validator = validator;
    return this;
  }

   /**
   * Get validator
   * @return validator
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_VALIDATOR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Validator getValidator() {
    return validator;
  }


  @JsonProperty(JSON_PROPERTY_VALIDATOR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setValidator(Validator validator) {
    this.validator = validator;
  }


  /**
   * Return true if this ValidatorInfoResponseSuccess object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValidatorInfoResponseSuccess validatorInfoResponseSuccess = (ValidatorInfoResponseSuccess) o;
    return Objects.equals(this.ledgerState, validatorInfoResponseSuccess.ledgerState) &&
        Objects.equals(this.validator, validatorInfoResponseSuccess.validator) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ledgerState, validator, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValidatorInfoResponseSuccess {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    ledgerState: ").append(toIndentedString(ledgerState)).append("\n");
    sb.append("    validator: ").append(toIndentedString(validator)).append("\n");
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
  mappings.put("ValidatorInfoResponseError", ValidatorInfoResponseError.class);
  mappings.put("ValidatorInfoResponseSuccess", ValidatorInfoResponseSuccess.class);
  mappings.put("ValidatorInfoResponseSuccess", ValidatorInfoResponseSuccess.class);
  JSON.registerDiscriminator(ValidatorInfoResponseSuccess.class, "type", mappings);
}
}
