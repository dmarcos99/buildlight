package com.suncorp.sintegration.buildlight.domain;

public class Job {

	public enum BuildStatus {
		OFF, SUCCESS, FAILURE, PROGRESS, UNKNOWN;
		
		public static BuildStatus getStatus(String colour) {
			return null;
		}
	}

	private String name;
	private BuildStatus status;

	public Job() {
	}

	public Job(String name, BuildStatus status) {
		this.name = name;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BuildStatus getStatus() {
		return status;
	}

	public void setStatus(BuildStatus status) {
		this.status = status;
	}
}
