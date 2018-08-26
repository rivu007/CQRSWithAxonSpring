package com.abhilash.cqrs.domain.events;

import lombok.Value;

/**
 * @author aghosh
 */
@Value
public class TaskTitleModifiedEvent implements TaskEvent {

	private final String id;

	private final String title;
}
