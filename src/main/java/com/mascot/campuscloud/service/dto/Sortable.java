package com.mascot.campuscloud.service.dto;

import java.time.LocalDateTime;

public abstract class Sortable {

	protected LocalDateTime ldtModified;
	protected String localName;

	public LocalDateTime getLdtModified() {
		return ldtModified;
	}

	public String getLocalName() {
		return localName;
	}
}
