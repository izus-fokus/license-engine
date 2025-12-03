package resus.licenseengine.fossology.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * To create an upload from a version control system
 **/
@Schema(description = "To create an upload from a version control system")
public class VcsUpload implements OneOfbody {
    public enum VcsTypeEnum {
        SVN("svn"),
        GIT("git");

        private final String value;

        VcsTypeEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static VcsTypeEnum fromValue(String text) {
            for (VcsTypeEnum b : VcsTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @Schema(description = "VCS type")
    private VcsTypeEnum vcsType = null;

    @Schema(description = "URL of the repository")
    private String vcsUrl = null;

    @Schema(description = "Branch to checkout for analysis (for Git only)")
    private String vcsBranch = null;

    @Schema(description = "Display name of the upload")
    private String vcsName = null;

    @Schema(description = "Username for the VCS")
    private String vcsUsername = null;

    @Schema(description = "Password for the VCS")
    private String vcsPassword = null;

    /**
     * VCS type
     *
     * @return vcsType
     **/
    @JsonProperty("vcsType")
    public String getVcsType() {
        if (vcsType == null) {
            return null;
        }
        return vcsType.getValue();
    }

    public void setVcsType(VcsTypeEnum vcsType) {
        this.vcsType = vcsType;
    }

    public VcsUpload vcsType(VcsTypeEnum vcsType) {
        this.vcsType = vcsType;
        return this;
    }

    /**
     * URL of the repository
     *
     * @return vcsUrl
     **/
    @JsonProperty("vcsUrl")
    public String getVcsUrl() {
        return vcsUrl;
    }

    public void setVcsUrl(String vcsUrl) {
        this.vcsUrl = vcsUrl;
    }

    public VcsUpload vcsUrl(String vcsUrl) {
        this.vcsUrl = vcsUrl;
        return this;
    }

    /**
     * Branch to checkout for analysis (for Git only)
     *
     * @return vcsBranch
     **/
    @JsonProperty("vcsBranch")
    public String getVcsBranch() {
        return vcsBranch;
    }

    public void setVcsBranch(String vcsBranch) {
        this.vcsBranch = vcsBranch;
    }

    public VcsUpload vcsBranch(String vcsBranch) {
        this.vcsBranch = vcsBranch;
        return this;
    }

    /**
     * Display name of the upload
     *
     * @return vcsName
     **/
    @JsonProperty("vcsName")
    public String getVcsName() {
        return vcsName;
    }

    public void setVcsName(String vcsName) {
        this.vcsName = vcsName;
    }

    public VcsUpload vcsName(String vcsName) {
        this.vcsName = vcsName;
        return this;
    }

    /**
     * Username for the VCS
     *
     * @return vcsUsername
     **/
    @JsonProperty("vcsUsername")
    public String getVcsUsername() {
        return vcsUsername;
    }

    public void setVcsUsername(String vcsUsername) {
        this.vcsUsername = vcsUsername;
    }

    public VcsUpload vcsUsername(String vcsUsername) {
        this.vcsUsername = vcsUsername;
        return this;
    }

    /**
     * Password for the VCS
     *
     * @return vcsPassword
     **/
    @JsonProperty("vcsPassword")
    public String getVcsPassword() {
        return vcsPassword;
    }

    public void setVcsPassword(String vcsPassword) {
        this.vcsPassword = vcsPassword;
    }

    public VcsUpload vcsPassword(String vcsPassword) {
        this.vcsPassword = vcsPassword;
        return this;
    }


    @Override
    public String toString() {

        return "class VcsUpload {\n" +
                "    vcsType: " + toIndentedString(vcsType) + "\n" +
                "    vcsUrl: " + toIndentedString(vcsUrl) + "\n" +
                "    vcsBranch: " + toIndentedString(vcsBranch) + "\n" +
                "    vcsName: " + toIndentedString(vcsName) + "\n" +
                "    vcsUsername: " + toIndentedString(vcsUsername) + "\n" +
                "    vcsPassword: " + toIndentedString(vcsPassword) + "\n" +
                "}";
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

    public String toJsonObject() {

        // https://stackoverflow.com/questions/15786129/converting-java-objects-to-json-with-jackson

        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert location object to json string and return it
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
