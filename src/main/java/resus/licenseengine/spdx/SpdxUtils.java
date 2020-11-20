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

package resus.licenseengine.spdx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spdx.html.InvalidLicenseTemplateException;
import org.spdx.rdfparser.InvalidSPDXAnalysisException;
import org.spdx.rdfparser.SPDXDocumentFactory;
import org.spdx.rdfparser.license.AnyLicenseInfo;
import org.spdx.rdfparser.license.LicenseInfoFactory;
import org.spdx.rdfparser.model.SpdxDocument;
import org.spdx.rdfparser.model.SpdxFile;

public class SpdxUtils {

	private static final Logger logger = LoggerFactory.getLogger(SpdxUtils.class);

	/**
	 * 
	 * @param file to be parsed
	 * @return a {@link org.spdx.rdfparser.model.SpdxDocument} object based on the
	 *         given file
	 * @throws IOException
	 * @throws InvalidSPDXAnalysisException
	 */
	public static SpdxDocument getSpdxDocumentFromFile(File file) throws IOException, InvalidSPDXAnalysisException {
		logger.debug("Creating a SPDX-Document from file: {}", file.getAbsolutePath());
		SpdxDocument doc = SPDXDocumentFactory.createSpdxDocument(file.getAbsolutePath());
		return doc;
	}

	/**
	 * 
	 * @param doc the {@link org.spdx.rdfparser.model.SpdxDocument} to be checked
	 * @return the amount of files contained in the
	 *         {@link org.spdx.rdfparser.model.SpdxDocument} object.
	 * @throws InvalidSPDXAnalysisException
	 */
	public static Integer getFilesCount(SpdxDocument doc) throws InvalidSPDXAnalysisException {
		logger.debug("Determining the amount of checked files...");
		return doc.getDocumentContainer().getFileReferences().length;
	}

	/**
	 * 
	 * @param doc
	 * @return a map, containing all files contained in the
	 *         {@link org.spdx.rdfparser.model.SpdxDocument} object as the key, and
	 *         a list with the corresponding licenses as the value.
	 * @throws InvalidSPDXAnalysisException
	 */
	public static Map<String, List<String>> getFileLicensesMap(SpdxDocument doc) throws InvalidSPDXAnalysisException {

		logger.debug("Creating the files to licenses mapping...");

		Map<String, List<String>> filesLicensesMapping = new HashMap<String, List<String>>();

		for (SpdxFile file : doc.getDocumentContainer().getFileReferences()) {

			String fileName = file.getName();

			AnyLicenseInfo[] licenses = file.getLicenseInfoFromFiles();
			List<String> licenseList = new ArrayList<String>();

			for (AnyLicenseInfo license : licenses) {
				licenseList.add(license.toString());
			}

			filesLicensesMapping.put(fileName, licenseList);

		}

		return filesLicensesMapping;

	}

	/**
	 * 
	 * @param doc
	 * @return a map, containing all licenses and their mappings to all
	 *         corresponding files contained in the
	 *         {@link org.spdx.rdfparser.model.SpdxDocument} object.
	 * @throws InvalidSPDXAnalysisException
	 */
	public static Map<String, List<String>> getLicensesFilesMap(SpdxDocument doc) throws InvalidSPDXAnalysisException {

		logger.debug("Creating the licenses to files mapping...");

		Map<String, List<String>> licensesFilesMapping = new HashMap<String, List<String>>();
		String licenseString;

		for (SpdxFile file : doc.getDocumentContainer().getFileReferences()) {

			AnyLicenseInfo[] licenses = file.getLicenseInfoFromFiles();

			for (AnyLicenseInfo license : licenses) {

				licenseString = license.toString();

				if (licensesFilesMapping.containsKey(licenseString)) {

					licensesFilesMapping.get(licenseString).add(file.getName());

				} else {
					ArrayList<String> fileList = new ArrayList<String>();
					fileList.add(file.getName());
					licensesFilesMapping.put(licenseString, fileList);
				}
			}
		}

		return licensesFilesMapping;

	}

	/**
	 * @param licenseID
	 * @return the corresponding license text to the given licenseID
	 * @throws InvalidSPDXAnalysisException
	 */
	public static String getLicenseText(String licenseID) throws InvalidSPDXAnalysisException {

		logger.debug("Returning the plain text for the license: {}", licenseID);
		return LicenseInfoFactory.getListedLicenseById(licenseID).getLicenseText();

	}

	/**
	 * @param licenseID
	 * @return the corresponding license text as html to the given licenseID
	 * @throws InvalidLicenseTemplateException
	 * @throws InvalidSPDXAnalysisException
	 */
	public static String getLicenseTextHtml(String licenseID)
			throws InvalidLicenseTemplateException, InvalidSPDXAnalysisException {
		logger.debug("Returning the text as html for the license: {}", licenseID);
		return LicenseInfoFactory.getListedLicenseById(licenseID).getLicenseTextHtml();

	}

	/**
	 * @return all available licenseIDs
	 */
	public static String[] getAllLicenses() {
		logger.debug("Returning all available licenses IDs...");
		return LicenseInfoFactory.getSpdxListedLicenseIds();
	}

}
