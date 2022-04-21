package resus.licenseengine.app.cli;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import resus.licenseengine.app.LicenseEngine;
import resus.licenseengine.app.model.Software;

public class LicenseEngineCLI {

	private static final Logger logger = LoggerFactory.getLogger(LicenseEngineCLI.class);

	public static void checkRepo(String repoURL, String branch) {

		Software software = new Software(RandomStringUtils.randomNumeric(8), "CLI_Check", repoURL);

		logger.info("Starting the licenses check for repository: {}", repoURL);
		if (branch != null) {
			logger.info("Branch: {}", branch);
			software.setBranch(branch);
		}

		if (!LicenseEngine.isFossologyAvailable()) {
			return;
		}

		LicenseEngine.startProcessing(software);

		logger.info("Found files and licenses: {}", software.getEffectiveLicensesFilesMapping());
		logger.info("Checking for a compatible license based on the found licenses: {}",
				software.getEffectiveLicenses());

		if (!LicenseEngine.isLicenseRecommenderAvailable()) {
			return;
		}

		logger.info("Recommended licenses: {}", LicenseEngine.getRecommendedLicenses(software));

	}

}