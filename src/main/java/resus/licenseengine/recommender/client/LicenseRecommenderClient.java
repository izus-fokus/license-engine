package resus.licenseengine.recommender.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import resus.licenseengine.recommender.api.AccessingInformationFromTheLicenseOntologyApi;
import resus.licenseengine.recommender.model.License;

public class LicenseRecommenderClient {

	private static final Logger logger = LoggerFactory.getLogger(LicenseRecommenderClient.class);

	private AccessingInformationFromTheLicenseOntologyApi recommenderApi;

	/**
	 * 
	 * Creates a new client for accessing the license recommender service.
	 * 
	 * @param endpoint of the license recommender service
	 */
	public LicenseRecommenderClient(String endpoint) {

		logger.debug("Creating new license recommender client for endpoint: {} ....", endpoint);

		JacksonJsonProvider provider = new JacksonJsonProvider();
		List<JacksonJsonProvider> providers = new ArrayList<JacksonJsonProvider>();
		providers.add(provider);
		recommenderApi = JAXRSClientFactory.create(endpoint, AccessingInformationFromTheLicenseOntologyApi.class, providers);

	}

	/**
	 * 
	 * Checks if the license recommender service is available.
	 * 
	 */
	public boolean isAvailable() {
		try {
			return getAllLicenses().size() > 0;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	/**
	 * 
	 * Requests all supported licenses of the license recommender service.
	 * 
	 */
	public List<License> getAllLicenses() throws Exception {
		return recommenderApi.getAllLicensesLicensesGet();
	}

	/**
	 * 
	 * Sends a list of licenses to the license recommender service in order to get a
	 * list of compatible licenses in return.
	 * 
	 * @param list to check
	 * 
	 * @return a list of compatible licenses.
	 */
	public List<String> getCompatibleLicenses(List<String> set) throws Exception {
		return recommenderApi.checkLicenseLicensesCheckPost(set);
	}

}
