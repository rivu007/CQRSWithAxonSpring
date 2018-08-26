package com.abhilash.cqrs.rest.requests;

import javax.validation.constraints.NotNull;

/**
 * @author aghosh
 */
public class CreateTaskRequest {

	@NotNull
	private String title;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
