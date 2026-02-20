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

import org.springframework.web.multipart.MultipartFile;

// import com.fasterxml.jackson.annotation.JsonProperty;

public class SoftwareUpload extends Software {

	// private String id;

	// private String name;
	private MultipartFile att;
	// @JsonProperty(required = false)
	// private String url;

	// public SoftwareUpload(String id, String name, String url) {
	// this.id = id;
	// this.name = name;
	// this.url = url;
	// }

	public SoftwareUpload(String id, String name, MultipartFile attachment) {
		super(id, name, new String());
		this.setAtt(attachment);
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
}
