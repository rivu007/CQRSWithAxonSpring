package com.abhilash.cqrs.domain.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

/**
 * @author aghosh
 */
@Value
public class UnstarTaskCommand {

	@TargetAggregateIdentifier
	private final String id;
}
