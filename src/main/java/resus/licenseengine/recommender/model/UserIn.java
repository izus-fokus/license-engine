package resus.licenseengine.recommender.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserIn   {
  
  @Schema(required = true, description = "")
  private String username = null;
  
  @Schema(required = true, description = "")
  private String passwordHash = null;
  
  @Schema(required = true, description = "")
  private String role = null;
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

  public UserIn username(String username) {
    this.username = username;
    return this;
  }

 /**
   * Get passwordHash
   * @return passwordHash
  **/
  @JsonProperty("password_hash")
  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public UserIn passwordHash(String passwordHash) {
    this.passwordHash = passwordHash;
    return this;
  }

 /**
   * Get role
   * @return role
  **/
  @JsonProperty("role")
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public UserIn role(String role) {
    this.role = role;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserIn {\n");
    
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    passwordHash: ").append(toIndentedString(passwordHash)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
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
