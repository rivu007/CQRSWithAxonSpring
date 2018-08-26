package com.abhilash.cqrs.task;

import com.abhilash.cqrs.domain.events.TaskEvent;
import lombok.Value;

/**
 * @author aghosh
 */
@Value
public class TaskEventNotification {

	private String type;

	private TaskEvent data;
}
