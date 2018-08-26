package com.abhilash.cqrs.domain.commands;

import lombok.Value;

import javax.validation.constraints.NotNull;

/**
 * @author aghosh
 */
@Value
public class CreateTaskCommand {

	@NotNull
	private final String id;

	@NotNull
	private final String username;

	@NotNull
	private final String title;
}
