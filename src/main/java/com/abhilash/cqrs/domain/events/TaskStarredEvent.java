package com.abhilash.cqrs.domain.events;

import lombok.Value;

/**
 * @author aghosh
 */
@Value
public class TaskStarredEvent implements TaskEvent {

	private final String id;
}
