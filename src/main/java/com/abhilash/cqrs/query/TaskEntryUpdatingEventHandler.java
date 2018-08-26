package com.abhilash.cqrs.query;

import com.abhilash.cqrs.domain.events.TaskCompletedEvent;
import com.abhilash.cqrs.domain.events.TaskCreatedEvent;
import com.abhilash.cqrs.domain.events.TaskStarredEvent;
import com.abhilash.cqrs.domain.events.TaskTitleModifiedEvent;
import com.abhilash.cqrs.domain.events.TaskUnstarredEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author aghosh
 */
@Component
public class TaskEntryUpdatingEventHandler {

	private final TaskEntryRepository taskEntryRepository;

	@Autowired
	public TaskEntryUpdatingEventHandler(TaskEntryRepository taskEntryRepository) {
		this.taskEntryRepository = taskEntryRepository;
	}

	@EventHandler
	void on(TaskCreatedEvent event) {
		//saving the data to h2 database
		//Saga - Failure trans
		// retry
		TaskEntry task = new TaskEntry(event.getId(), event.getUsername(), event.getTitle(), false, false);
		taskEntryRepository.save(task);
	}

	@EventHandler
	void on(TaskCompletedEvent event) {
		TaskEntry task = taskEntryRepository.findOne(event.getId());
		task.setCompleted(true);

		taskEntryRepository.save(task);
	}

	@EventHandler
	void on(TaskTitleModifiedEvent event) {
		TaskEntry task = taskEntryRepository.findOne(event.getId());
		task.setTitle(event.getTitle());

		taskEntryRepository.save(task);
	}

	@EventHandler
	void on (TaskStarredEvent event) {
		TaskEntry task = taskEntryRepository.findOne(event.getId());
		task.setStarred(true);

		taskEntryRepository.save(task);
	}

	@EventHandler
	void on (TaskUnstarredEvent event) {
		TaskEntry task = taskEntryRepository.findOne(event.getId());
		task.setStarred(false);

		taskEntryRepository.save(task);
	}
}
