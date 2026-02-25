package resus.licenseengine.app.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MultipartConfig {

    private static final Logger logger = LoggerFactory.getLogger(MultipartConfig.class);

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        // Maximal zulässige Größe einer einzelnen Datei
        factory.setMaxFileSize(DataSize.parse("50MB"));

        // Maximal zulässige Größe einer gesamten Request
        factory.setMaxRequestSize(DataSize.parse("50MB"));

        // Optional: Verzeichnis für temporäre Dateien
        factory.setLocation(System.getProperty("java.io.tmpdir"));

        logger.info("Max file size is {}", DataSize.parse("50MB"));

        logger.info("Tmp dir is {}", System.getProperty("java.io.tmpdir"));

        return factory.createMultipartConfig();
    }
}