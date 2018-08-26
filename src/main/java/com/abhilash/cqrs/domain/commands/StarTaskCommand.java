package com.abhilash.cqrs.domain.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * @author aghosh
 */
@Value
public class StarTaskCommand {

	@TargetAggregateIdentifier
	private final String id;
}
