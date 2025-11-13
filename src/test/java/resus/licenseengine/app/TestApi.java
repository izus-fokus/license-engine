package resus.licenseengine.app;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import resus.licenseengine.fossology.client.FossologyClient;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestApi {

    private static final Logger logger = Logger.getLogger(TestApi.class.getCanonicalName());

    private static String fossologyEndpoint;
    private static String fossologyUsername;
    private static String fossologyPassword;

    @BeforeAll
    public static void setUpClass() {
        setUpFossology("http://localhost:7100","fossy", "fossy");
    }

    @AfterAll
    public static void tearDownClass() {
    }

    public static void setUpFossology(String fossologyEndpoint,
                                      String fossologyUsername,
                                      String fossologyPassword) {
        TestApi.fossologyEndpoint = fossologyEndpoint + "/repo/api/v1";
        TestApi.fossologyUsername = fossologyUsername;
        TestApi.fossologyPassword = fossologyPassword;
    }

    @Test
    public void fossologyIsAvailable() throws Exception {
        FossologyClient fossologyClient = new FossologyClient(fossologyEndpoint, fossologyUsername, fossologyPassword);
        boolean isAvailable = fossologyClient.isAvailable();
        assertTrue(isAvailable);
    }
}

