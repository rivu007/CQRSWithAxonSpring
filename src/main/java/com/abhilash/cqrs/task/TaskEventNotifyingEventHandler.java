package com.abhilash.cqrs.task;

import com.abhilash.cqrs.domain.events.TaskCompletedEvent;
import com.abhilash.cqrs.domain.events.TaskCreatedEvent;
import com.abhilash.cqrs.domain.events.TaskEvent;
import com.abhilash.cqrs.domain.events.TaskStarredEvent;
import com.abhilash.cqrs.domain.events.TaskTitleModifiedEvent;
import com.abhilash.cqrs.domain.events.TaskUnstarredEvent;
import com.abhilash.cqrs.query.TaskEntry;
import com.abhilash.cqrs.query.TaskEntryRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;


/**
 * @author aghosh
 */
@Component
@ProcessingGroup("pushNotification")
public class TaskEventNotifyingEventHandler {

	private final SimpMessageSendingOperations messagingTemplate;

	private final TaskEntryRepository taskEntryRepository;

	@Autowired
	public TaskEventNotifyingEventHandler(SimpMessageSendingOperations messagingTemplate, TaskEntryRepository taskEntryRepository) {
		this.messagingTemplate = messagingTemplate;
		this.taskEntryRepository = taskEntryRepository;
	}

	@EventHandler
	void on(TaskCreatedEvent event) {
		//publishing the notification to the user.
		publish(event.getUsername(), event);
	}

	@EventHandler
	void on(TaskCompletedEvent event) {
		TaskEntry task = findTaskById(event.getId());
		publish(task.getUsername(), event);
	}

	@EventHandler
	void on(TaskTitleModifiedEvent event) {
		TaskEntry task = findTaskById(event.getId());
		publish(task.getUsername(), event);
	}

	@EventHandler
	void on (TaskStarredEvent event) {
		TaskEntry task = findTaskById(event.getId());
		publish(task.getUsername(), event);
	}

	@EventHandler
	void on (TaskUnstarredEvent event) {
		TaskEntry task = findTaskById(event.getId());
		publish(task.getUsername(), event);
	}

	private void publish(String username, TaskEvent event) {
		String type = event.getClass().getSimpleName();
		this.messagingTemplate.convertAndSendToUser(username, "/queue/task-updates", new TaskEventNotification(type, event));
	}

	private TaskEntry findTaskById(String id) {
		return taskEntryRepository
						.findById(id)
						.orElseThrow(() -> new NoSuchElementException("Event not found: " + id));
	}
}
