package com.abhilash.cqrs.domain.events;

import lombok.Value;


/**
 * @author aghosh
 */
@Value
public class TaskCreatedEvent implements TaskEvent {

	private final String id;

	private final String username;

	private final String title;
}
