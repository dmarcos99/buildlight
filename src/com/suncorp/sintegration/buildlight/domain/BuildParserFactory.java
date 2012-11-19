package com.suncorp.sintegration.buildlight.domain;

import com.suncorp.sintegration.buildlight.configuration.Configuration;

public class BuildParserFactory {

	public enum PARSER {
		JENKINS, JENKINS_REGEX;
	}

	public static BuildParser getParser(Configuration config) {
		// TODO: return parser based on config
		return new BuildParserJenkinsRegex();
	}
}
