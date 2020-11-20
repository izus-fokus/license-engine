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

package resus.licenseengine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.model.SpdxDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import resus.licenseengine.fossology.client.FossologyClient;
import resus.licenseengine.rest.ProcessingStatus;
import resus.licenseengine.rest.Software;
import resus.licenseengine.spdx.SpdxUtils;

@Component
public class LicenseEngine {

	private static final Logger logger = LoggerFactory.getLogger(LicenseEngine.class);

	private static String fossologyEndpoint;
	private static String fossologyUsername;
	private static String fossologyPassword;

	private static Map<String, Software> softwareMap = new HashMap<String, Software>();

	/**
	 * 
	 * Processes the given software. Uploads the files, checks them for licenses,
	 * and creates a report.
	 * 
	 * @param software to be processed.
	 */
	public static void startProcessing(Software software) {

		String id = software.getId();

		logger.info("Starting the processing of a new software with ID: {} ...", id);

		FossologyClient client = new FossologyClient(fossologyEndpoint, fossologyUsername, fossologyPassword);

		software.setStatus(ProcessingStatus.UPLOADING);

		Integer uploadID = null;
		if (software.getBranch() != null) {
			uploadID = client.uploadVCS(software.getUrl(), software.getBranch(), software.getName());
		} else {
			uploadID = client.uploadURL(software.getUrl(), software.getName());
		}

		if (uploadID != null && client.waitForUploadJob(uploadID)) {

			software.setStatus(ProcessingStatus.ANALYZING);

			Integer jobID = client.startAnalyzeJob(uploadID);

			if (client.waitForAnalyzeJob(jobID)) {

				Integer reportID = client.createReport(uploadID);

				if (client.waitForReportJob(reportID)) {

					File reportFile = client.getReport(reportID);

					try {

						SpdxDocument spdxDocument = SpdxUtils.getSpdxDocumentFromFile(reportFile);

						Map<String, List<String>> licenseFilesMapping = SpdxUtils.getLicensesFilesMap(spdxDocument);

						software.setLicenseFilesMapping(licenseFilesMapping);
						software.setFiles(SpdxUtils.getFilesCount(spdxDocument));

					} catch (InvalidSPDXAnalysisException | IOException e) {
						software.setStatus(ProcessingStatus.FAILED);
						software.setStatus(ProcessingStatus.FAILED);
						logger.error("Creating report failed! Software: " + id);
						e.printStackTrace();
					}

					logger.info("Processing of the new software with ID: {} finished.", id);
					software.setStatus(ProcessingStatus.FINISHED);

				} else {
					software.setStatus(ProcessingStatus.FAILED);
					logger.error("Creating report failed! Software: " + id);
				}

			} else {
				software.setStatus(ProcessingStatus.FAILED);
				logger.error("Analyzing failed! Software: " + id);
			}
		} else {
			software.setStatus(ProcessingStatus.FAILED);
			logger.error("Upload failed! Software: " + id);
		}
	}

	/**
	 * @param id
	 * @return software with the given id. Or null, if no software can be found for
	 *         it.
	 */
	public static Software getSoftware(String id) {
		return softwareMap.get(id);
	}

	/**
	 * @param id       of the software to be added and processed.
	 * @param software to be processed.
	 * @return
	 */
	public static boolean addSoftware(String id, final Software software) {

		if (softwareMap.containsKey(id)) {
			logger.warn("A software with the provided ID: {} is already processed.", id);
			return false;
		}

		software.setStatus(ProcessingStatus.QUEUED);
		softwareMap.put(id, software);

		new Thread() {
			public void run() {
				LicenseEngine.startProcessing(software);
			}
		}.start();

		return true;
	}

	/**
	 * 
	 * @return if a fossology instance is running and can be accessed.
	 */
	public static boolean isAvailable() {

		if (FossologyClient.isAvailable(fossologyEndpoint)) {
			return true;
		}
		return false;
	}

	/**
	 * @param id of the software to be deleted.
	 */
	public static void deletesoftware(String id) {
		softwareMap.remove(id);

		logger.info("Software with ID: {} was deleted.", id);
	}

	@Value("${fossology.endpoint}")
	public void setFossologyEndpoint(String endpoint) {
		LicenseEngine.fossologyEndpoint = endpoint;
	}

	@Value("${fossology.username}")
	public void setFossologyUsername(String username) {
		LicenseEngine.fossologyUsername = username;
	}

	@Value("${fossology.password}")
	public void setFossologyPassword(String password) {
		LicenseEngine.fossologyPassword = password;
	}

}
