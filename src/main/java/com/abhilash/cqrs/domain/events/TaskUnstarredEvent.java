package com.abhilash.cqrs.domain.events;

import lombok.Value;

/**
 * @author aghosh
 */
@Value
public class TaskUnstarredEvent implements TaskEvent {

	private final String id;
}
