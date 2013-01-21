package com.suncorp.sintegration.buildlight.domain

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.NameValuePair
import org.apache.commons.httpclient.methods.GetMethod

import com.suncorp.sintegration.buildlight.domain.Job.BuildStatus

class BuildParserJenkinsContains implements BuildParser {

	@Override
	public List<Job> checkStatus(String url, String jobName) throws IOException {
		return getJobs(url, jobName);
	}


	private String getJobStatuses(String url, String jobName) throws IOException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod("http://" + url + "/api/xml");
		NameValuePair xpath = new NameValuePair("xpath", "/hudson/job[contains(name,'" + jobName + "')]");
		NameValuePair wrapper = new NameValuePair("wrapper", "jobs");
		method.setQueryString([xpath, wrapper] as NameValuePair[]);
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}
	
	private List<Job> getJobs(String url, String jobName) throws IOException {
		String xml = getJobStatuses(url, jobName);
		List<Job> jobs = new ArrayList<Job>();
		new XmlSlurper().parseText(xml).job.each {
			jobs << new Job(it.name?.text(), getBuildStatus(it.color?.text()));
		}
		jobs
	}

	private BuildStatus getBuildStatus(String colour) {
		return colour.equals("blue_anime") ? BuildStatus.PROGRESS : //
		colour.equals("red_anime") ? BuildStatus.PROGRESS : //
		colour.equals("red") ? BuildStatus.FAILURE : //
		colour.equals("yellow") ? BuildStatus.FAILURE : //
		colour.equals("blue") ? BuildStatus.SUCCESS : //
		BuildStatus.UNKNOWN;
	}
}
