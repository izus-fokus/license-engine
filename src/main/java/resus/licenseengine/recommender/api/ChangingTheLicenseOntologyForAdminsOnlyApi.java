package resus.licenseengine.recommender.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.swagger.v3.oas.annotations.Operation;
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
public interface ChangingTheLicenseOntologyForAdminsOnlyApi  {

    /**
     * Add License
     *
     * Adds a license to the ontology. Only for admins.  **work in progress, not working yet**
     *
     */
    @POST
    @Path("/licenses/add/")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Add License", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object addLicenseLicensesAddPost(License body);

    /**
     * Delete License
     *
     * Deletes a certain license. Only for admins.  **work in progress, not working yet**
     *
     */
    @DELETE
    @Path("/licenses/{license_id}")
    @Produces({ "application/json" })
    @Operation(summary = "Delete License", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object deleteLicenseLicensesLicenseIdDelete(@PathParam("license_id") String licenseId);

    /**
     * Update License
     *
     * Changes the properties of a certain license. Only for admins.  **work in progress, not working yet**
     *
     */
    @PUT
    @Path("/licenses/{license_id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Update License", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object updateLicenseLicensesLicenseIdPut(License body, @PathParam("license_id") String licenseId);
}
