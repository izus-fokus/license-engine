package resus.licenseengine.app;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import resus.licenseengine.app.model.Software;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestApi {

    private static final Logger logger = Logger.getLogger(TestApi.class.getCanonicalName());

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
            LicenseEngine.startProcessing(software);
            LicenseEngine.addSoftware("replay",software);
        }
    }

    @Test
    @Order(3)
    public void getSoftware() {
        Software softResponse= LicenseEngine.getSoftware("replay");
        assertEquals("https://github.com/RePlay-DH/replay-dh-client.git",softResponse.getUrl());
        Set<String> licenseSet = new HashSet<>(Arrays.asList("LGPL-2.1", "MIT", "CC-BY-SA-3.0"));
        assertEquals(licenseSet,softResponse.getEffectiveLicenses());
        assertEquals("replay", softResponse.getName());
        assertEquals(550,softResponse.getFiles().intValue());
    }
}

