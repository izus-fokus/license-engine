/*******************************************************************************
 * Copyright (c) 2020 IAAS, University of Stuttgart.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package resus.licenseengine.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import resus.licenseengine.app.cli.LicenseEngineCLI;
import resus.licenseengine.utils.LicenseUtils;

@SpringBootApplication
public class LicenseEngineApplication {

	@Autowired
	private ApplicationContext context;

	private static final Logger logger = LoggerFactory.getLogger(LicenseEngineApplication.class);

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(LicenseEngineApplication.class, args);
		// initialize for speed up
		Class.forName(LicenseUtils.class.getName());
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption,
			@Value("${application-version}") String appVersion) {

		return new OpenAPI()
				.info(new Info().title("ReSUS License-Engine API").version(appVersion).description(appDesciption));
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").exposedHeaders("Location",
						"Access-Control-Expose-Headers", "Access-Control-Allow-Origin",
						"Access-Control-Allow-Credentials");
			}
		};
	}

	@Component
	public class LicenseEngineApplicationRunner implements ApplicationRunner {

		@Value("${server.port}")
		String port;
		@Value("${server.endpoints.software.path}")
		String softwarepath;
		@Value("${server.endpoints.licenses.path}")
		String licensepath;

		public void run(ApplicationArguments args) {

			logger.info("Starting...");

			if (args.containsOption("repo")) {
				String branch = null;
				String repo = "https://github.com/" + args.getOptionValues("repo").get(0);
				if (args.containsOption("branch")) {
					branch = args.getOptionValues("branch").get(0);
				}
				Integer result = LicenseEngineCLI.checkRepo(repo, branch);				
				int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> result);
				System.exit(exitCode);

			} else {
				logger.info("******************************************");
				logger.info("Available endpoints:");
				logger.info("http://localhost:" + port + softwarepath);
				logger.info("http://localhost:" + port + licensepath);
				logger.info("http://localhost:" + port + "/actuator/health");
				logger.info("Endpoint documentation:");
				logger.info("http://localhost:" + port + "/swagger-ui.html");
				logger.info(
						"(This service requires a running FOSSology instance, which can be started, e.g., as follows: docker run -p 8081:80 fossology/fossology:3.10.0)");
				logger.info("******************************************");

			}

		}
	}

}
