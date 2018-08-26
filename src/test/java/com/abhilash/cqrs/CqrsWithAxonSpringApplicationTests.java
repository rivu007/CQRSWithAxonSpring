package com.abhilash.cqrs;

import com.abhilash.cqrs.domain.Task;
import com.abhilash.cqrs.domain.TaskAlreadyCompletedException;
import com.abhilash.cqrs.domain.commands.CompleteTaskCommand;
import com.abhilash.cqrs.domain.commands.CreateTaskCommand;
import com.abhilash.cqrs.domain.commands.ModifyTaskTitleCommand;
import com.abhilash.cqrs.domain.commands.StarTaskCommand;
import com.abhilash.cqrs.domain.events.TaskCompletedEvent;
import com.abhilash.cqrs.domain.events.TaskCreatedEvent;
import com.abhilash.cqrs.domain.events.TaskStarredEvent;
import com.abhilash.cqrs.domain.events.TaskTitleModifiedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class CqrsWithAxonSpringApplicationTests {

	private FixtureConfiguration fixture;

	@Before
	public void setUp() {
		fixture = new AggregateTestFixture(Task.class);
	}

	@Test
	public void createTask() {
		fixture.given()
				.when(new CreateTaskCommand("1", "testUser", "Sample-task"))
				.expectSuccessfulHandlerExecution()
				.expectEvents(new TaskCreatedEvent("1", "testUser", "Sample-task"));
	}

	@Test
	public void updateTaskTitle() {
		fixture.given(new TaskCreatedEvent("1", "testUser", "Sample-task"))
				.when(new ModifyTaskTitleCommand("1", "Sample-task-title-updated"))
				.expectSuccessfulHandlerExecution()
				.expectEvents(new TaskTitleModifiedEvent("1", "Sample-task-title-updated"));
	}

	@Test
	public void starTask() {
		fixture.given(new TaskCreatedEvent("1", "testUser", "Important-task"))
				.when(new StarTaskCommand("1"))
				.expectSuccessfulHandlerExecution()
				.expectEvents(new TaskStarredEvent("1"));
	}

	@Test
	public void completeTask() {
		fixture.given(new TaskCreatedEvent("1", "testUser", "Important-task"),
				      new TaskStarredEvent("1"))
				.when(new CompleteTaskCommand("1"))
				.expectSuccessfulHandlerExecution()
				.expectEvents(new TaskCompletedEvent("1"));
	}

	@Test
	public void updatedTitleOfAlreadyFinishedTask_exception() {
		fixture.given(new TaskCreatedEvent("1", "testUser", "Sample-task"),
				      new TaskCompletedEvent("1"))
				.when(new ModifyTaskTitleCommand("1", "Sample-task-updated"))
				.expectException(TaskAlreadyCompletedException.class);
	}

}
