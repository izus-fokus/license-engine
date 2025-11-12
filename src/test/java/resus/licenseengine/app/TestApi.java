package resus.licenseengine.app;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
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
    }

    @AfterAll
    public static void tearDownClass() {
    }

    public void setUp(@Value("${fossology.endpoint}") String fossologyEndpoint,
                      @Value("${fossology.username}") String fossologyUsername,
                      @Value("${fossology.password}") String fossologyPassword) {
        TestApi.fossologyEndpoint = fossologyEndpoint;
        TestApi.fossologyUsername = fossologyUsername;
        TestApi.fossologyPassword = fossologyPassword;
    }

    @Test
    public void fossologyIsAvailable() throws Exception {
        setUp("http://localhost:7100/repo/api/v1","fossy", "fossy");
        FossologyClient fossologyClient = new FossologyClient(fossologyEndpoint, fossologyUsername, fossologyPassword);
        boolean isAvailable = fossologyClient.isAvailable();
        assertTrue(isAvailable);
    }
}

