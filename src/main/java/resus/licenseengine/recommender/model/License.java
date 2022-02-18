package resus.licenseengine.recommender.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class License   {
  
  @Schema(required = true, description = "")
  private String id = null;
  
  @Schema(required = true, description = "")
  private String name = null;
  
  @Schema(description = "")
  private String url = null;
  
  @Schema(description = "")
  private LicenseTypes type = null;
  
  @Schema(description = "")
  private List<Conditions> conditions = null;
  
  @Schema(description = "")
  private List<Permissions> permissions = null;
  
  @Schema(description = "")
  private List<Limitations> limitations = null;
  
  @Schema(description = "")
  private List<String> compatibility = null;
 /**
   * Get id
   * @return id
  **/
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public License id(String id) {
    this.id = id;
    return this;
  }

 /**
   * Get name
   * @return name
  **/
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public License name(String name) {
    this.name = name;
    return this;
  }

 /**
   * Get url
   * @return url
  **/
  @JsonProperty("url")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public License url(String url) {
    this.url = url;
    return this;
  }

 /**
   * Get type
   * @return type
  **/
  @JsonProperty("type")
  public LicenseTypes getType() {
    return type;
  }

  public void setType(LicenseTypes type) {
    this.type = type;
  }

  public License type(LicenseTypes type) {
    this.type = type;
    return this;
  }

 /**
   * Get conditions
   * @return conditions
  **/
  @JsonProperty("conditions")
  public List<Conditions> getConditions() {
    return conditions;
  }

  public void setConditions(List<Conditions> conditions) {
    this.conditions = conditions;
  }

  public License conditions(List<Conditions> conditions) {
    this.conditions = conditions;
    return this;
  }

  public License addConditionsItem(Conditions conditionsItem) {
    this.conditions.add(conditionsItem);
    return this;
  }

 /**
   * Get permissions
   * @return permissions
  **/
  @JsonProperty("permissions")
  public List<Permissions> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permissions> permissions) {
    this.permissions = permissions;
  }

  public License permissions(List<Permissions> permissions) {
    this.permissions = permissions;
    return this;
  }

  public License addPermissionsItem(Permissions permissionsItem) {
    this.permissions.add(permissionsItem);
    return this;
  }

 /**
   * Get limitations
   * @return limitations
  **/
  @JsonProperty("limitations")
  public List<Limitations> getLimitations() {
    return limitations;
  }

  public void setLimitations(List<Limitations> limitations) {
    this.limitations = limitations;
  }

  public License limitations(List<Limitations> limitations) {
    this.limitations = limitations;
    return this;
  }

  public License addLimitationsItem(Limitations limitationsItem) {
    this.limitations.add(limitationsItem);
    return this;
  }

 /**
   * Get compatibility
   * @return compatibility
  **/
  @JsonProperty("compatibility")
  public List<String> getCompatibility() {
    return compatibility;
  }

  public void setCompatibility(List<String> compatibility) {
    this.compatibility = compatibility;
  }

  public License compatibility(List<String> compatibility) {
    this.compatibility = compatibility;
    return this;
  }

  public License addCompatibilityItem(String compatibilityItem) {
    this.compatibility.add(compatibilityItem);
    return this;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class License {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    conditions: ").append(toIndentedString(conditions)).append("\n");
    sb.append("    permissions: ").append(toIndentedString(permissions)).append("\n");
    sb.append("    limitations: ").append(toIndentedString(limitations)).append("\n");
    sb.append("    compatibility: ").append(toIndentedString(compatibility)).append("\n");
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
