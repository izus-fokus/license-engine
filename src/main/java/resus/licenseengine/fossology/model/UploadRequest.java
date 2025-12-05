package resus.licenseengine.fossology.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class UploadRequest implements OneOfbody{

    public UploadRequest() {
    }

    public UploadRequest(String uploadType, Integer folderId, String uploadDescription,
                         String _public, Boolean ignoreScm, String groupName, OneOfbody location) {
        this.uploadType = uploadType;
        this.folderId = folderId;
        this.uploadDescription = uploadDescription;
        this._public = _public;
        this.ignoreScm = ignoreScm;
        this.groupName = groupName;
        this.location = location;
    }

    /**
     * uploadType
     **/
    private String uploadType = null;

    /**
     * folderId
     **/
    private int folderId = 0;

    /**
     * uploadDescription
     **/
    private String uploadDescription = null;

    /**
     * _public scope of the token.
     **/
    private String _public = null;

    /**
     * ignoreScm
     **/
    private boolean ignoreScm = false;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * groupName
     **/
    private String groupName = null;

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getUploadDescription() {
        return uploadDescription;
    }

    public void setUploadDescription(String uploadDescription) {
        this.uploadDescription = uploadDescription;
    }

    public String get_public() {
        if (_public == null) {
            return null;
        }
        return _public;
    }

    public void set_public(String _public) {
        this._public = _public;
    }

    public boolean getIgnoreScm() {
        return ignoreScm;
    }

    public void setIgnoreScm(Boolean ignoreScm) {
        this.ignoreScm = ignoreScm;
    }


    public OneOfbody getLocation() {
        return location;
    }

    public void setLocation(OneOfbody location) {
        this.location = location;
    }

    private OneOfbody location = null;

    @Override
    public String toString() {

        return "class UploadRequestParamBody {\n" +
                "    uploadType: " + toIndentedString(uploadType) + "\n" +
                "    folderId: " + toIndentedString(folderId) + "\n" +
                "    uploadDescription: " + toIndentedString(uploadDescription) + "\n" +
                "    public: " + toIndentedString(_public) + "\n" +
                "    ignoreScm: " + toIndentedString(ignoreScm) + "\n" +
                "    groupName: " + toIndentedString(groupName) + "\n" +
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
