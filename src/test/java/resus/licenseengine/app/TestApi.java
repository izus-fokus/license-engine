package resus.licenseengine.app;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import resus.licenseengine.app.model.Software;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestApi {

    private static final Logger logger = Logger.getLogger(TestApi.class.getCanonicalName());

    @Autowired
    private MockMvc mockMvc;


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
        try (InputStream is = classLoader.getResourceAsStream("replay.json")) {
            if (is == null) {
                throw new IllegalArgumentException("Ressource nicht gefunden!");
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonElement obj = JsonParser.parseString(content);
            JsonObject jsonObject = obj.getAsJsonObject();
            Software software = new Software(jsonObject.get("id").getAsString(),
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("url").getAsString());
//            timer.scheduleAtFixedRate(repeatedTask, 0, 10_000);
            LicenseEngine.startProcessing(software);
            LicenseEngine.addSoftware("replay",software);
        }
    }

    @Test
    @Order(3)
    public void getSoftwareSet() {
        Software softResponse= LicenseEngine.getSoftware("replay");
        assertEquals("https://github.com/RePlay-DH/replay-dh-client.git",softResponse.getUrl());
        Set<String> licenseSet = new HashSet<>(Arrays.asList("GPL-3.0-or-later", "LGPL-2.1-only", "NOASSERTION", "MIT", "CC-BY-SA-3.0"));
        assertEquals(licenseSet,softResponse.getEffectiveLicenses());
        assertEquals("replay", softResponse.getName());
    }

    @Test
    @Order(4)
    public void getSoftwareAll() {
        Software softResponse= LicenseEngine.getSoftware("replay");
        assertEquals("https://github.com/RePlay-DH/replay-dh-client.git",softResponse.getUrl());
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
        Software softResponse= LicenseEngine.getSoftware("replay");
        assertEquals("https://github.com/RePlay-DH/replay-dh-client.git",softResponse.getUrl());
        assertEquals("replay", softResponse.getName());
        assertEquals(550,softResponse.getFiles().intValue());
    }

    @Test
    @Order(6)
    public void addSoftwareDataverse() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("dataverse.json")) {
            if (is == null) {
                throw new IllegalArgumentException("Ressource nicht gefunden!");
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonElement obj = JsonParser.parseString(content);
            JsonObject jsonObject = obj.getAsJsonObject();
            Software software = new Software(jsonObject.get("id").getAsString(),
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("url").getAsString());
            LicenseEngine.startProcessing(software);
            LicenseEngine.addSoftware("dataverse",software);
        }
    }

    @Test
    @Order(7)
    public void getSoftwareDataverseSet() {
        Software softResponse= LicenseEngine.getSoftware("dataverse");
        assertEquals("https://github.com/IQSS/dataverse.git",softResponse.getUrl());
        Set<String> licenseSet = new HashSet<>(Arrays.asList("CC-BY-NC-3.0",
                "CC-BY-NC-4.0",
                "CC-BY-NC-SA-4.0",
                "ODbL-1.0",
                "CC-BY-4.0",
                "CC-BY-3.0",
                "CC-BY-NC-ND-4.0",
                "NOASSERTION",
                "JSON",
                "GPL-2.0-only",
                "OGL-UK-3.0",
                "PDDL-1.0",
                "CC-BY-SA-4.0",
                "Apache-2.0",
                "CC0-1.0",
                "MIT",
                "CC-BY-ND-4.0",
                "LGPL-3.0-or-later"));
        assertEquals(licenseSet,softResponse.getEffectiveLicenses());
        assertEquals("dataverse", softResponse.getName());
    }

    @Test
    @Order(8)
    public void getSoftwareDataverseAll() {
        Software softResponse= LicenseEngine.getSoftware("dataverse");
        assertEquals("https://github.com/IQSS/dataverse.git",softResponse.getUrl());
        Set<String> licenseSetAll = new HashSet<>(Arrays.asList("CC-BY-NC-3.0",
                "CC-BY-NC-4.0",
                "CC-BY-NC-SA-4.0",
                "ODbL-1.0",
                "CC-BY-4.0",
                "CC-BY-3.0",
                "CC-BY-NC-ND-4.0",
                "NOASSERTION",
                "JSON",
                "GPL-2.0-only",
                "OGL-UK-3.0",
                "PDDL-1.0",
                "CC-BY-SA-4.0",
                "Apache-2.0",
                "CC0-1.0",
                "MIT",
                "CC-BY-ND-4.0",
                "UNKNOWN LICENSE",
                "LGPL-3.0-or-later"));
        assertEquals(licenseSetAll,softResponse.getAllLicenses());
        assertEquals("dataverse", softResponse.getName());
    }
}

