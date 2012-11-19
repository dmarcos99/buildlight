package com.suncorp.sintegration.buildlight.domain;

import java.io.IOException;
import java.util.List;

public class TestClass {

	// public static void main(String[] args) {
	//
	// String xml = "<a><b>val1</b><b>val2</b></a>";
	//
	// Document doc = loadXMLFromString(xml);
	//
	// //System.out.println(doc.getElementsByTagName("b").item(0).getFirstChild().getNodeValue());
	//
	// NodeList childNodes = doc.getElementsByTagName("b");
	// for (int i = 0; i < childNodes.getLength(); i++){
	// System.out.println(childNodes.item(i).getFirstChild().getNodeValue());
	// }
	//
	// //System.out.println(doc);
	//
	// }

	public static void main(String[] args) throws IOException {
		BuildParser parser1 = new BuildParserJenkinsImpl();
		
		//List<Job> jobs1 = parser1.checkStatus("ci.jenkins-ci.org", "fix-git-configuration-on-remote-slave-8");
		//System.out.println("" + jobs.);
		
		BuildParser parser = new BuildParserJenkinsRegex();
		

		List<Job> jobs = parser.checkStatus("ci.jenkins-ci.org", "git");

		for (Job job : jobs) {
			System.out.println(job.getName());
		}
	}

}
