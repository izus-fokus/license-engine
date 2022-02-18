package resus.licenseengine.recommender.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import resus.licenseengine.recommender.model.HTTPValidationError;
import resus.licenseengine.recommender.model.License;

/**
 * License Checker
 *
 * <p>The License Checker provides information about FLOSS software licenses and helps you to identify compatible licenses.
 *
 */
@Path("/")
public interface AccessingInformationFromTheLicenseOntologyApi  {

    /**
     * Check License
     *
     * Returns which licenses are compatible with a certain license.  **work in progress, not working yet**
     *
     */
    @POST
    @Path("/licenses/check/")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Check License", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public List<String> checkLicenseLicensesCheckPost(List<String> body);

    /**
     * Check Osi Popularity
     *
     * Returns TRUE if the license is among the popular licenses according to the Open Software Initiative.
     *
     */
    @GET
    @Path("/licenses/{license_id}/osi_popular")
    @Produces({ "application/json" })
    @Operation(summary = "Check Osi Popularity", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object checkOsiPopularityLicensesLicenseIdOsiPopularGet(@PathParam("license_id") String licenseId);

    /**
     * Get All Licenses
     *
     * Returns all available software licenses as License-Objects
     *
     */
    @GET
    @Path("/licenses/")
    @Produces({ "application/json" })
    @Operation(summary = "Get All Licenses", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = License.class)))) })
    public List<License> getAllLicensesLicensesGet();

    /**
     * Get License Conditions
     *
     * Returns the conditions of a certain license.
     *
     */
    @GET
    @Path("/licenses/{license_id}/conditions")
    @Produces({ "application/json" })
    @Operation(summary = "Get License Conditions", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object getLicenseConditionsLicensesLicenseIdConditionsGet(@PathParam("license_id") String licenseId);

    /**
     * Get License
     *
     * Returns a certain license as a License-Object
     *
     */
    @GET
    @Path("/licenses/{license_id}")
    @Produces({ "application/json" })
    @Operation(summary = "Get License", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object getLicenseLicensesLicenseIdGet(@PathParam("license_id") String licenseId);

    /**
     * Get License Limitations
     *
     * Returns the limitations of a certain license.
     *
     */
    @GET
    @Path("/licenses/{license_id}/limitations")
    @Produces({ "application/json" })
    @Operation(summary = "Get License Limitations", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object getLicenseLimitationsLicensesLicenseIdLimitationsGet(@PathParam("license_id") String licenseId);

    /**
     * Get License Permissions
     *
     * Returns the permissions of a certain license.
     *
     */
    @GET
    @Path("/licenses/{license_id}/permissions")
    @Produces({ "application/json" })
    @Operation(summary = "Get License Permissions", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object getLicensePermissionsLicensesLicenseIdPermissionsGet(@PathParam("license_id") String licenseId);

    /**
     * Get License Type
     *
     * Returns the type of a certain license.
     *
     */
    @GET
    @Path("/licenses/{license_id}/license_type")
    @Produces({ "application/json" })
    @Operation(summary = "Get License Type", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object getLicenseTypeLicensesLicenseIdLicenseTypeGet(@PathParam("license_id") String licenseId);

    /**
     * Return Licenses For Type
     *
     * Returns a list of licenses of a certain type.
     *
     */
    @GET
    @Path("/types/{license_type}")
    @Produces({ "application/json" })
    @Operation(summary = "Return Licenses For Type", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object returnLicensesForTypeTypesLicenseTypeGet(@PathParam("license_type") String licenseType);

    /**
     * Return Osi Popular Licenses
     *
     * Returns all popular licenses according to the Open Software Initiative.
     *
     */
    @GET
    @Path("/popular")
    @Produces({ "application/json" })
    @Operation(summary = "Return Osi Popular Licenses", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))) })
    public Object returnOsiPopularLicensesPopularGet();
}
