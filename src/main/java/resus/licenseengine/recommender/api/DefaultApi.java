package resus.licenseengine.recommender.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import resus.licenseengine.recommender.model.HTTPValidationError;
import resus.licenseengine.recommender.model.User;
import resus.licenseengine.recommender.model.UserIn;

/**
 * License Checker
 *
 * <p>The License Checker provides information about FLOSS software licenses and helps you to identify compatible licenses.
 *
 */
@Path("/")
public interface DefaultApi  {

    /**
     * Create User
     *
     */
    @POST
    @Path("/users")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Create User", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public User createUserUsersPost(UserIn body);

    /**
     * Generate Token
     *
     */
    @POST
    @Path("/token")
    @Consumes({ "application/x-www-form-urlencoded" })
    @Produces({ "application/json" })
    @Operation(summary = "Generate Token", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "422", description = "Validation Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HTTPValidationError.class))) })
    public Object generateTokenTokenPost(@Multipart(value = "grant_type")  String grantType, @Multipart(value = "username")  String username, @Multipart(value = "password")  String password, @Multipart(value = "scope")  String scope, @Multipart(value = "client_id")  String clientId, @Multipart(value = "client_secret")  String clientSecret);

    /**
     * Get User
     *
     */
    @GET
    @Path("/users/me")
    @Produces({ "application/json" })
    @Operation(summary = "Get User", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Successful Response", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))) })
    public User getUserUsersMeGet();
}
