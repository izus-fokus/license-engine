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

package resus.licenseengine.app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Software {

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

	public Software(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	@JsonProperty(value = "licenses-all", access = Access.READ_ONLY)
	public Set<String> getAllLicenses() {
		return licensesToFilesMapping.keySet();
	}

	@JsonProperty(value = "files-excluded", access = Access.READ_ONLY)
	public Integer getExcludedFilesCount() {
		return excludedFiles.size();
	}

	@JsonProperty(value = "licenses-effective", access = Access.READ_ONLY)
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
						if (file.endsWith(ignoredFile.substring(1, ignoredFile.length()))) {
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
		this.licensesToFilesMapping = licenseFilesMapping;
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
		this.excludedFiles.addAll(excludedFiles);
	}

	/**
	 * clears the excluded-files list
	 */
	public void clearExcludedFiles() {
		this.excludedFiles.clear();
	}

	private static HashMap<String, List<String>> deepCopyMap(Map<String, List<String>> licensesToFilesMapping) {

		HashMap<String, List<String>> copy = new HashMap<>();
		for (Map.Entry<String, List<String>> entry : licensesToFilesMapping.entrySet()) {
			copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
		}
		return copy;
	}
}
