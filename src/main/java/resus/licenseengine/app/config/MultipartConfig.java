package resus.licenseengine.app.config;

import jakarta.servlet.MultipartConfigElement;
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
        long maxSize = DataSize.parse("50MB").toBytes();
        String tmpDir = System.getProperty("java.io.tmpdir");

        logger.info("Max file size is {}", DataSize.parse("50MB"));
        logger.info("Tmp dir is {}", tmpDir);

        return new MultipartConfigElement(tmpDir, maxSize, maxSize, 0);
    }
}