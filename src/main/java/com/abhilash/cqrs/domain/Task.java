package com.abhilash.cqrs.domain;

import com.abhilash.cqrs.domain.commands.CompleteTaskCommand;
import com.abhilash.cqrs.domain.commands.CreateTaskCommand;
import com.abhilash.cqrs.domain.commands.ModifyTaskTitleCommand;
import com.abhilash.cqrs.domain.commands.StarTaskCommand;
import com.abhilash.cqrs.domain.commands.UnstarTaskCommand;
import com.abhilash.cqrs.domain.events.TaskCompletedEvent;
import com.abhilash.cqrs.domain.events.TaskCreatedEvent;
import com.abhilash.cqrs.domain.events.TaskStarredEvent;
import com.abhilash.cqrs.domain.events.TaskTitleModifiedEvent;
import com.abhilash.cqrs.domain.events.TaskUnstarredEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


/**
 * @author aghosh
 */
@Aggregate
@NoArgsConstructor
public class Task {

	/**
	 * The constant serialVersionUID
	 */
	private static final long serialVersionUID = -5977984483620451665L;

	@AggregateIdentifier
	private String id;

	@NotNull
	private boolean completed;

	/**
	 * Creates a new Task.
	 *
	 * @param command create Task
	 */
	@CommandHandler
	public Task(CreateTaskCommand command) {
		apply(new TaskCreatedEvent(command.getId(), command.getUsername(), command.getTitle()));
	}
	/**
	 * Completes a Task.
	 *
	 * @param command complete Task
	 */
	@CommandHandler
	void on(CompleteTaskCommand command) {
		apply(new TaskCompletedEvent(command.getId()));
	}

	/**
	 * Stars a Task.
	 *
	 * @param command star Task
	 */
	@CommandHandler
	void on(StarTaskCommand command) {
		apply(new TaskStarredEvent(command.getId()));
	}

	/**
	 * Unstars a Task.
	 *
	 * @param command unstar Task
	 */
	@CommandHandler
	void on(UnstarTaskCommand command) {
		apply(new TaskUnstarredEvent(command.getId()));
	}

	/**
	 * Modifies a Task title.
	 *
	 * @param command modify Task title
	 */
	@CommandHandler
	void on(ModifyTaskTitleCommand command) {
		assertNotCompleted();
		apply(new TaskTitleModifiedEvent(command.getId(), command.getTitle()));
	}

	@EventSourcingHandler
	void on(TaskCreatedEvent event) {
		this.id = event.getId();
	}

	@EventSourcingHandler
	void on(TaskCompletedEvent event) {
		this.completed = true;
	}

	private void assertNotCompleted() {
		if (completed) {
			throw new TaskAlreadyCompletedException("Task [ identifier = " + id + " ] is completed.");
		}
	}
}
