package com.abhilash.cqrs.domain.events;

import lombok.Value;

/**
 * @author aghosh
 */
@Value
public class TaskCompletedEvent implements TaskEvent {

	private final String id;
}
