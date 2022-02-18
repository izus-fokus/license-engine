package resus.licenseengine.recommender.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class User   {
  
  @Schema(required = true, description = "")
  private Integer id = null;
  
  @Schema(required = true, description = "")
  private String username = null;
  
  @Schema(required = true, description = "")
  private String passwordHash = null;
  
  @Schema(required = true, description = "")
  private String role = null;
 /**
   * Get id
   * minimum: 1
   * maximum: 2147483647
   * @return id
  **/
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public User id(Integer id) {
    this.id = id;
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

  public User username(String username) {
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

  public User passwordHash(String passwordHash) {
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

  public User role(String role) {
    this.role = role;
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
