package resus.licenseengine.app.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import resus.licenseengine.app.LicenseEngine;
import resus.licenseengine.app.model.Software;

public class LicenseEngineCLI {

	private static final Logger logger = LoggerFactory.getLogger(LicenseEngineCLI.class);

	public static Integer checkRepo(String repoURL, String branch, String license) {

		Software software = new Software(RandomStringUtils.randomNumeric(8), null, repoURL);

		logger.info("Starting the licenses check for repository: {} ...", repoURL);
		if (branch != null) {
			logger.info("Branch: {}", branch);
			software.setBranch(branch);
		}
		return checkSoftware(software, license);
	}

	// public static Integer checkFile(Attachment att, String license) {

	// Software software = new Software(RandomStringUtils.randomNumeric(8), att);

	// logger.info("Starting the licenses check for file: {} ...", att);

	// return checkSoftware(software, license);
	// }

	public static Integer checkSoftware(Software software, String license) {

		if (!LicenseEngine.isFossologyAvailable()) {
			return 1;
		}

		LicenseEngine.startProcessing(software);

		printSeparator();

		logger.info("Found licenses and files:");
		printFilesAndLicenes(software);

		setExcludedFiles(software);

		logger.info("Resulting licenses and files used for checking for compatible licenses:");
		printFilesAndLicenes(software);

		Set<String> licenses = software.getEffectiveLicenses();

		if (licenses.size() < 1) {
			printSeparator();
			logger.info("No licenses found.");
			return 0;
		}

		logger.info("Checking for compatible licenses based on the resulting list of licenses: {} ...", licenses);

		if (!LicenseEngine.isLicenseRecommenderAvailable()) {
			return 1;
		}

		List<String> compatibleLicense = LicenseEngine.getRecommendedLicenses(software);

		printSeparator();

		if (compatibleLicense != null && compatibleLicense.size() > 0) {

			logger.info("Compatible licenses found: {}", compatibleLicense.toString());

			if (license != null) {
				if (compatibleLicense.stream().anyMatch(license::equalsIgnoreCase)) {
					logger.info("Found licenses are compatible with the defined license of the repository: {}",
							license);
					return 0;
				} else {
					logger.error(
							"However, found licenses are not compatible with the defined license of the repository: {}",
							license);
					return 1;
				}
			}
			return 0;
		}

		logger.error("Found licenses are not compatible!");
		return 1;
	}

	private static void printFilesAndLicenes(Software software) {
		for (Map.Entry<String, List<String>> entry : software.getEffectiveLicensesFilesMapping().entrySet()) {
			logger.info("   - " + entry.getKey() + ":");
			for (String file : entry.getValue()) {
				logger.info("       - " + file);
			}
		}
	}

	private static void setExcludedFiles(Software software) {
		Path path = Paths.get(".license_ignorefiles");
		try {
			List<String> lines = Files.readAllLines(path);
			logger.info(".license_ignorefiles file found. Following files are specified to be ignored:");
			for (String line : lines) {
				logger.info("   - " + line);
				software.getExcludedFiles().add(line);
			}
		} catch (IOException e) {
			logger.info("No .license_ignorefiles file found!");
		}
	}

	private static void printSeparator() {
		logger.info(
				"*********************************************************************************************************************************************************");
		logger.info(
				"*********************************************************************************************************************************************************");
	}

}