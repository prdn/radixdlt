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
 * BurnTokens
 */
@JsonPropertyOrder({
  BurnTokens.JSON_PROPERTY_FROM_ACCOUNT,
  BurnTokens.JSON_PROPERTY_AMOUNT
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-30T23:32:02.552056-06:00[America/Chicago]")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = BurnTokens.class, name = "BurnTokens"),
  @JsonSubTypes.Type(value = MintTokens.class, name = "MintTokens"),
  @JsonSubTypes.Type(value = StakeTokens.class, name = "StakeTokens"),
  @JsonSubTypes.Type(value = TransferTokens.class, name = "TransferTokens"),
  @JsonSubTypes.Type(value = UnstakeTokens.class, name = "UnstakeTokens"),
})

public class BurnTokens extends Action {
  public static final String JSON_PROPERTY_FROM_ACCOUNT = "from_account";
  private AccountIdentifier fromAccount;

  public static final String JSON_PROPERTY_AMOUNT = "amount";
  private TokenAmount amount;


  public BurnTokens fromAccount(AccountIdentifier fromAccount) {
    this.fromAccount = fromAccount;
    return this;
  }

   /**
   * Get fromAccount
   * @return fromAccount
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_FROM_ACCOUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public AccountIdentifier getFromAccount() {
    return fromAccount;
  }


  @JsonProperty(JSON_PROPERTY_FROM_ACCOUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setFromAccount(AccountIdentifier fromAccount) {
    this.fromAccount = fromAccount;
  }


  public BurnTokens amount(TokenAmount amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(JSON_PROPERTY_AMOUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public TokenAmount getAmount() {
    return amount;
  }


  @JsonProperty(JSON_PROPERTY_AMOUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setAmount(TokenAmount amount) {
    this.amount = amount;
  }


  /**
   * Return true if this BurnTokens object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BurnTokens burnTokens = (BurnTokens) o;
    return Objects.equals(this.fromAccount, burnTokens.fromAccount) &&
        Objects.equals(this.amount, burnTokens.amount) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fromAccount, amount, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BurnTokens {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    fromAccount: ").append(toIndentedString(fromAccount)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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
  mappings.put("BurnTokens", BurnTokens.class);
  mappings.put("MintTokens", MintTokens.class);
  mappings.put("StakeTokens", StakeTokens.class);
  mappings.put("TransferTokens", TransferTokens.class);
  mappings.put("UnstakeTokens", UnstakeTokens.class);
  mappings.put("BurnTokens", BurnTokens.class);
  JSON.registerDiscriminator(BurnTokens.class, "type", mappings);
}
}

