package resus.licenseengine.app.cli;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import resus.licenseengine.app.LicenseEngine;
import resus.licenseengine.app.model.Software;

public class LicenseEngineCLI {

	private static final Logger logger = LoggerFactory.getLogger(LicenseEngineCLI.class);

	public static Integer checkRepo(String repoURL, String branch) {

		Software software = new Software(RandomStringUtils.randomNumeric(8), "CLI_Check", repoURL);

		logger.info("Starting the licenses check for repository: {}", repoURL);
		if (branch != null) {
			logger.info("Branch: {}", branch);
			software.setBranch(branch);
		}

		if (!LicenseEngine.isFossologyAvailable()) {
			return 1;
		}

		LicenseEngine.startProcessing(software);

		logger.info("Found files and licenses:");

		for (Map.Entry<String, List<String>> entry : software.getEffectiveLicensesFilesMapping().entrySet()) {
			logger.info("   - " + entry.getKey() + ":");
			for (String file : entry.getValue()) {
				logger.info("       - " + file);
			}
		}

		logger.info("Checking for compatible licenses based on the found licenses: {}",
				software.getEffectiveLicenses());

		if (!LicenseEngine.isLicenseRecommenderAvailable()) {
			return 1;
		}

		if (LicenseEngine.getRecommendedLicenses(software).size() > 0) {
			logger.info("*******************************************************************");
			logger.info("Found compatible licenses: {}", LicenseEngine.getRecommendedLicenses(software));
			return 0;
		}
		logger.info("*******************************************************************");
		logger.error("No compatible license(s) found!");
		return 1;
	}
}