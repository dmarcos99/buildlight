package com.suncorp.sintegration.buildlight.domain;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.suncorp.sintegration.buildlight.domain.Job.BuildStatus;

public class BuildParserJenkinsRegex implements BuildParser {

	@Override
	public List<Job> checkStatus(String url, String jobName) throws IOException {
		return getJobs(url, jobName);
	}

	private String getJobStatuses(String url, String jobName) throws IOException {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod("http://" + url + "/api/xml");
		NameValuePair xpath = new NameValuePair("xpath", "/hudson/job[contains(name,'cash')]&wrapper=jobs");
		NameValuePair wrapper = new NameValuePair("wrapper", "jobs");
		method.setQueryString(new NameValuePair[] { xpath, wrapper });
		client.executeMethod(method);
		return method.getResponseBodyAsString();
	}

	private List<Job> getJobs(String url, String jobName) throws IOException {
		String xml = getJobStatuses(url, jobName);
		System.out.println("xml = " + xml);
		Document doc = loadXMLFromString(xml);

		NodeList nameNodes = doc.getElementsByTagName("name");
		NodeList colourNodes = doc.getElementsByTagName("color");
		if (nameNodes.getLength() != colourNodes.getLength()) {
			throw new RuntimeException("Error: invalid xml returned from jenkins: " + doc.toString());
		}
		List<Job> jobs = new ArrayList<Job>();
		for (int i = 0; i < nameNodes.getLength(); i++) {
			String name = nameNodes.item(i).getFirstChild().getNodeValue();
			String colour = colourNodes.item(i).getFirstChild().getNodeValue();
			jobs.add(new Job(name, getBuildStatus(colour)));
		}

		return jobs;
	}

	private BuildStatus getBuildStatus(String colour) {
		return colour.equals("blue_anime") ? BuildStatus.PROGRESS : //
		colour.equals("red_anime") ? BuildStatus.PROGRESS : //
		colour.equals("red") ? BuildStatus.FAILURE : //
		colour.equals("yellow") ? BuildStatus.FAILURE : //
		colour.equals("blue") ? BuildStatus.SUCCESS : //
		BuildStatus.UNKNOWN;
	}

	private Document loadXMLFromString(String xml) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			return builder.parse(is);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

}
