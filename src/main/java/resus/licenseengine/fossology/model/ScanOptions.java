package resus.licenseengine.fossology.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScanOptions implements OneOfbody{

    @Schema(description = "")
    private Analysis analysis = null;

    @Schema(description = "")
    private LicenseDecider decider = null;

    @Schema(description = "")
    private Reuser reuse = null;

    /**
     * Get analysis
     *
     * @return analysis
     **/
    @JsonProperty("analysis")
    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public ScanOptions analysis(Analysis analysis) {
        this.analysis = analysis;
        return this;
    }

    /**
     * Get decider
     *
     * @return decider
     **/
    @JsonProperty("decider")
    public LicenseDecider getDecider() {
        return decider;
    }

    public void setDecider(LicenseDecider decider) {
        this.decider = decider;
    }

    public ScanOptions decider(LicenseDecider decider) {
        this.decider = decider;
        return this;
    }

    /**
     * Get reuse
     *
     * @return reuse
     **/
    @JsonProperty("reuse")
    public Reuser getReuse() {
        return reuse;
    }

    public void setReuse(Reuser reuse) {
        this.reuse = reuse;
    }

    public ScanOptions reuse(Reuser reuse) {
        this.reuse = reuse;
        return this;
    }


    @Override
    public String toString() {

        return "class ScanOptions {\n" +
                "    analysis: " + toIndentedString(analysis) + "\n" +
                "    decider: " + toIndentedString(decider) + "\n" +
                "    reuse: " + toIndentedString(reuse) + "\n" +
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

    @Override
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
