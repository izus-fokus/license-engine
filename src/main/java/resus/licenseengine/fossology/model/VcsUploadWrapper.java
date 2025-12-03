package resus.licenseengine.fossology.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;

/**
 * To create an upload from a version control system
 **/
@Schema(description = "To create an upload from a version control system")
public class VcsUploadWrapper implements OneOfbody {

    public VcsUploadWrapper(VcsUpload vcsUpload) {
        this.location = new HashMap<>();
        this.location.put("location",vcsUpload);
    }

    public HashMap<String, VcsUpload> getLocation() {
        return location;
    }

    public void setLocation(HashMap<String, VcsUpload> location) {
        this.location = location;
    }

    @Schema(description = "VCS location")
    /**
     * VCS location
     **/
    private HashMap<String,VcsUpload> location = null;

    public String toJsonObject() {

        // https://stackoverflow.com/questions/15786129/converting-java-objects-to-json-with-jackson

        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert location object to json string and return it
            return mapper.writeValueAsString(location);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
