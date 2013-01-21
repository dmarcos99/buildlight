package com.suncorp.sintegration.buildlight.domain;

public class Job implements Comparable<Job> {

	public enum BuildStatus {
		OFF, SUCCESS, UNKNOWN, PROGRESS, FAILURE;
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

	public String toString() {
		return "Job " + name + " has a status of " + status;
	}

	@Override
	public int compareTo(Job job) {
		// sort by status then name
		int statusCompare = this.status.compareTo(job.getStatus());
		return statusCompare == 0 ? this.name.compareTo(job.getName()) : statusCompare;
	}
}
