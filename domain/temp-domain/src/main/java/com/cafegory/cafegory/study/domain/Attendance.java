package com.cafegory.cafegory.study.domain;

public enum Attendance {

	YES(true),
	NO(false);

	private final boolean isPresent;

	Attendance(boolean isPresent) {
		this.isPresent = isPresent;
	}
}
