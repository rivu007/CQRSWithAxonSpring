package com.abhilash.cqrs.domain.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;


/**
 * @author aghosh
 */
@Value
public class ModifyTaskTitleCommand {

	@TargetAggregateIdentifier
	private final String id;

	@NotNull
	private final String title;
}

