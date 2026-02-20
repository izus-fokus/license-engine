package resus.licenseengine.app;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import resus.licenseengine.app.model.Software;

import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestApiFileUpload {

    private static final Logger logger = Logger.getLogger(TestApiFileUpload.class.getCanonicalName());
    @Autowired
    private MockMvc mockMvc;

    private String sha256sum;


    Timer timer = new Timer();

    TimerTask repeatedTask = new TimerTask() {
        @Override
        public void run() {
            try {
                MvcResult result = mockMvc.perform(get("http://localhost:7000/api/v1/software/status/replay")).andReturn();
                logger.info("Response is " + result.getResponse().getContentAsString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @Test
    @Order(1)
    public void fossologyIsAvailable() throws Exception {
        boolean isAvailable = LicenseEngine.isFossologyAvailable();
        assertTrue(isAvailable);
    }

    @Test
    @Order(2)
    public void addSoftware() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("replay-dh-client-1.3.0.zip")) {
            if (is == null) {
                throw new IllegalArgumentException("Ressource nicht gefunden!");
            }
            try {
                sha256sum = DigestUtils.sha256Hex(is);
            }
            catch (Exception e) {
                logger.info("MD5 Hash could not be calculated from Input Stream: replay-dh-client-1.3.0.zip");
                sha256sum = UUID.randomUUID().toString();
            }
            MockMultipartFile zipFile = new MockMultipartFile("file","replay-dh-client-1.3.0.zip","multipart/form-data",is);
            timer.scheduleAtFixedRate(repeatedTask, 0, 10_000);
//            mockMvc.perform(multipart("http://localhost:7000/api/v1/software/upload").file(zipFile)).andExpect(status().isOk());
            Software softwareUpload = new Software(sha256sum, "replay-dh-client-1.3.0.zip", zipFile);
            timer.scheduleAtFixedRate(repeatedTask, 0, 10_000);
            LicenseEngine.startProcessing(softwareUpload);
            LicenseEngine.addSoftware(sha256sum,softwareUpload);
        }
    }

    @Test
    @Order(3)
    public void getSoftwareSet() {
        Software softResponse= LicenseEngine.getSoftware(sha256sum);
        Set<String> licenseSet = new HashSet<>(Arrays.asList("GPL-3.0-or-later", "LGPL-2.1-only", "NOASSERTION", "MIT", "CC-BY-SA-3.0"));
        assertEquals(licenseSet,softResponse.getEffectiveLicenses());
        assertEquals("replay", softResponse.getName());
    }

    @Test
    @Order(4)
    public void getSoftwareAll() {
        Software softResponse= LicenseEngine.getSoftware(sha256sum);
        Set<String> licenseSetAll = new HashSet<>(Arrays.asList("EPL-1.0",
                "CDDL-1.1",
                "GPL-3.0-or-later",
                "LGPL-2.1-only",
                "BSD-3-Clause",
                "Apache-2.0",
                "NOASSERTION",
                "MIT",
                "BSD-3-Clause-HP",
                "CC-BY-SA-3.0",
                "GPL-2.0-only",
                "UNKNOWN LICENSE"));
        assertEquals(licenseSetAll,softResponse.getAllLicenses());
        assertEquals("replay", softResponse.getName());
    }

    @Test
    @Order(5)
    public void getSoftwareFiles() {
        Software softResponse= LicenseEngine.getSoftware(sha256sum);
        assertEquals("replay", softResponse.getName());
        assertEquals(550,softResponse.getFiles().intValue());
    }

}

