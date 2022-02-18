package resus.licenseengine.recommender.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class BodyGenerateTokenTokenPost   {
  
  @Schema(description = "")
  private String grantType = null;
  
  @Schema(required = true, description = "")
  private String username = null;
  
  @Schema(required = true, description = "")
  private String password = null;
  
  @Schema(description = "")
  private String scope = "";
  
  @Schema(description = "")
  private String clientId = null;
  
  @Schema(description = "")
  private String clientSecret = null;
 /**
   * Get grantType
   * @return grantType
  **/
  @JsonProperty("grant_type")
  public String getGrantType() {
    return grantType;
  }

  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }

  public BodyGenerateTokenTokenPost grantType(String grantType) {
    this.grantType = grantType;
    return this;
  }

 /**
   * Get username
   * @return username
  **/
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public BodyGenerateTokenTokenPost username(String username) {
    this.username = username;
    return this;
  }

 /**
   * Get password
   * @return password
  **/
  @JsonProperty("password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public BodyGenerateTokenTokenPost password(String password) {
    this.password = password;
    return this;
  }

 /**
   * Get scope
   * @return scope
  **/
  @JsonProperty("scope")
  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public BodyGenerateTokenTokenPost scope(String scope) {
    this.scope = scope;
    return this;
  }

 /**
   * Get clientId
   * @return clientId
  **/
  @JsonProperty("client_id")
  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public BodyGenerateTokenTokenPost clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

 /**
   * Get clientSecret
   * @return clientSecret
  **/
  @JsonProperty("client_secret")
  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public BodyGenerateTokenTokenPost clientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BodyGenerateTokenTokenPost {\n");
    
    sb.append("    grantType: ").append(toIndentedString(grantType)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    scope: ").append(toIndentedString(scope)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    clientSecret: ").append(toIndentedString(clientSecret)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
