/*******************************************************************************
 * Copyright (c) 2020 IAAS, University of Stuttgart.
 * <p>
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package resus.licenseengine.app.model;

import java.util.*;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.springframework.web.multipart.MultipartFile;

public class Software {

	private final String unknownLicense = "UNKNOWN LICENSE";

	private MultipartFile att;

	@JsonProperty(required = true)
	private String id;
	@JsonProperty(required = true)
	private String name;
	@JsonProperty(required = true)
	private String url;
	@JsonProperty(required = false)
	private String branch;
	@JsonProperty(access = Access.READ_ONLY)
	private ProcessingStatus status;
	@JsonProperty(access = Access.READ_ONLY)
	private Integer files;
	@JsonIgnore
	private Map<String, List<String>> licensesToFilesMapping = new HashMap<String, List<String>>();
	@JsonIgnore
	private List<String> excludedFiles = new ArrayList<String>();

	@JsonIgnore
	private List<String> filesWithUnknownLicense = new ArrayList<String>();

	protected Software() {}

	public Software(String id, String name) {
		this.id = id;
		this.name = name;
	}


	public Software(String id, String name, String url) {
		this(id, name);
		this.url = url;
	}

	public Software(String id, String name, MultipartFile att) {
		this(id, name);
		this.att = att;
	}

	/**
	 * @return the attachment
	 */
	public MultipartFile getAtt() {
		return att;
	}

	/**
	 * @param att the attachment to set
	 */
	public void setAtt(MultipartFile att) {
		this.att = att;
	}

	@JsonProperty(value = "licensesAll", access = Access.READ_ONLY)
	public Set<String> getAllLicenses() {
		return licensesToFilesMapping.keySet();
	}

	@JsonProperty(value = "filesExcluded", access = Access.READ_ONLY)
	public Integer getExcludedFilesCount() {
		return excludedFiles.size();
	}

	@JsonProperty(value = "filesWithUnknownLicense", access = Access.READ_ONLY)
	public Integer getFilesWithAnUnknownLicenseCount() {
		return filesWithUnknownLicense.size();
	}

	@JsonProperty(value = "licensesEffective", access = Access.READ_ONLY)
	public Set<String> getEffectiveLicenses() {
		return getEffectiveLicensesFilesMapping().keySet();
	}

	@JsonIgnore
	public Map<String, List<String>> getEffectiveLicensesFilesMapping() {

		Map<String, List<String>> effectiveLicensesFilesMapping = deepCopyMap(licensesToFilesMapping);
		List<String> toRemoveLicenses = new ArrayList<String>();

		for (Entry<String, List<String>> licensesFilesEntry : effectiveLicensesFilesMapping.entrySet()) {
			for (String ignoredFile : excludedFiles) {
				if (ignoredFile.endsWith("*") && ignoredFile.startsWith("*")) {
					List<String> toRemoveFiles = new ArrayList<String>();
					for (String file : licensesFilesEntry.getValue()) {
						if (file.contains(ignoredFile.substring(1, ignoredFile.length() - 1))) {
							toRemoveFiles.add(file);
						}
					}
					licensesFilesEntry.getValue().removeAll(toRemoveFiles);
				} else if (ignoredFile.endsWith("*")) {
					List<String> toRemoveFiles = new ArrayList<String>();
					for (String file : licensesFilesEntry.getValue()) {
						if (file.startsWith(ignoredFile.substring(0, ignoredFile.length() - 1))) {
							toRemoveFiles.add(file);
						}
					}
					licensesFilesEntry.getValue().removeAll(toRemoveFiles);
				} else if (ignoredFile.startsWith("*")) {
					List<String> toRemoveFiles = new ArrayList<String>();
					for (String file : licensesFilesEntry.getValue()) {
						if (file.endsWith(ignoredFile.substring(1))) {
							toRemoveFiles.add(file);
						}
					}
					licensesFilesEntry.getValue().removeAll(toRemoveFiles);
				} else {
					licensesFilesEntry.getValue().remove(ignoredFile);
				}

			}
			if (licensesFilesEntry.getValue().isEmpty()) {
				toRemoveLicenses.add(licensesFilesEntry.getKey());
			}
		}
		for (String license : toRemoveLicenses) {
			effectiveLicensesFilesMapping.remove(license);
		}

		return effectiveLicensesFilesMapping;
	}

	/**
	 * @return the licenseFilesMapping
	 */
	@JsonIgnore
	public Map<String, List<String>> getLicenseFilesMapping() {
		return licensesToFilesMapping;
	}

	/**
	 * @param licenseFilesMapping the licenseFilesMapping to set
	 */
	public void setLicenseFilesMapping(Map<String, List<String>> licenseFilesMapping) {
		List<String> files;
		String license;
		for (Entry<String, List<String>> licenseFilesEntry : licenseFilesMapping.entrySet()) {
			license = licenseFilesEntry.getKey();
			files = licenseFilesEntry.getValue();
			if (license.equals("NULL LICENSE")) {
				this.addExcludedFiles(files);
				this.licensesToFilesMapping.put(license, files);
			} else if (license.startsWith("LicenseRef-")) {
				this.addExcludedFiles(files);
				this.addFilesWithUnknownLicense(files);
				if (this.licensesToFilesMapping.containsKey(unknownLicense)) {
					this.licensesToFilesMapping.get(unknownLicense).addAll(files);
				} else {
					this.licensesToFilesMapping.put(unknownLicense, files);
				}
			} else {
				this.licensesToFilesMapping.put(license, files);
			}
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the files
	 */
	public Integer getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(Integer files) {
		this.files = files;
	}

	/**
	 * @return the status
	 */
	public ProcessingStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ProcessingStatus status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the excludedFiles
	 */
	public List<String> getExcludedFiles() {
		return excludedFiles;
	}

	/**
	 * @param excludedFiles the excludedFiles to set
	 */
	public void addExcludedFiles(List<String> excludedFiles) {
		if (excludedFiles != null) {
			this.excludedFiles.addAll(excludedFiles);
		}
	}

	/**
	 * clears the excluded-files list
	 */
	public void clearExcludedFiles() {
		this.excludedFiles.clear();
	}

	/**
	 * @param filesWithUnknownLicense files with an unknown license to add to the
	 *                                list
	 */
	public void addFilesWithUnknownLicense(List<String> filesWithUnknownLicense) {
		if (filesWithUnknownLicense != null) {
			this.filesWithUnknownLicense.addAll(filesWithUnknownLicense);
		}
	}

	/**
	 * @return the filesWithUnknownLicense
	 */
	public List<String> getFilesWithUnknownLicense() {
		return filesWithUnknownLicense;
	}

	/**
	 * Sets the licenses for files with an unknown license
	 *
     */
	public void setLicensesForFiles(Map<String, List<String>> licenseFilesMap) throws Exception {
		String license;
		List<String> files;
		for (Entry<String, List<String>> licenseFilesEntry : licenseFilesMap.entrySet()) {
			license = licenseFilesEntry.getKey();
			files = licenseFilesEntry.getValue();

			if (licensesToFilesMapping.containsKey(unknownLicense)) {
				if (new HashSet<>(licensesToFilesMapping.get(unknownLicense)).containsAll(files)) {
					licensesToFilesMapping.get(unknownLicense).removeAll(files);
				} else {
					throw new Exception("Files (" + files + ") already have specified licenses!");
				}
			} else {
				throw new Exception("Files (" + files + ") don't have an unknown license!");
			}

			if (licensesToFilesMapping.containsKey(license)) {
				licensesToFilesMapping.get(license).addAll(files);
			} else {
				licensesToFilesMapping.put(license, files);
			}
			excludedFiles.removeAll(files);
			filesWithUnknownLicense.removeAll(files);
		}
		if (filesWithUnknownLicense.isEmpty()) {
			licensesToFilesMapping.remove(unknownLicense);
		}
	}

	private static HashMap<String, List<String>> deepCopyMap(Map<String, List<String>> licensesToFilesMapping) {

		HashMap<String, List<String>> copy = new HashMap<>();
		for (Map.Entry<String, List<String>> entry : licensesToFilesMapping.entrySet()) {
			copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
		}
		return copy;
	}

}
