package com.abhilash.cqrs.rest;

import com.abhilash.cqrs.domain.commands.CompleteTaskCommand;
import com.abhilash.cqrs.domain.commands.CreateTaskCommand;
import com.abhilash.cqrs.domain.commands.ModifyTaskTitleCommand;
import com.abhilash.cqrs.domain.commands.StarTaskCommand;
import com.abhilash.cqrs.query.TaskEntry;
import com.abhilash.cqrs.query.TaskEntryRepository;
import com.abhilash.cqrs.rest.requests.CreateTaskRequest;
import com.abhilash.cqrs.rest.requests.ModifyTitleRequest;
import lombok.RequiredArgsConstructor;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author aghosh
 */
@RestController
@RequiredArgsConstructor
public class TaskController {

	private final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();

	private final TaskEntryRepository taskEntryRepository;

	private final SimpMessageSendingOperations messagingTemplate;

	private final CommandGateway commandGateway;

	@RequestMapping(value = "/api/tasks", method = RequestMethod.GET)
	public @ResponseBody
    Page<TaskEntry> findAll(Principal principal, @RequestParam(required = false, defaultValue = "false") boolean completed, Pageable pageable) {
		return taskEntryRepository.findByUsernameAndCompleted(principal.getName(), completed, pageable);
	}

	@RequestMapping(value = "/api/tasks", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createTask(Principal principal, @RequestBody @Valid CreateTaskRequest request) {
		//Axon
		/// saving
		String ack = commandGateway.sendAndWait(new CreateTaskCommand(identifierFactory.generateIdentifier(), principal.getName(), request.getTitle()));
	}

	@RequestMapping(value = "/api/tasks/{identifier}/title", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createTask(@PathVariable String identifier, @RequestBody @Valid ModifyTitleRequest request) {
		commandGateway.sendAndWait(new ModifyTaskTitleCommand(identifier, request.getTitle()));
	}

	@RequestMapping(value = "/api/tasks/{identifier}/complete", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void createTask(@PathVariable String identifier) {
		commandGateway.sendAndWait(new CompleteTaskCommand(identifier));
	}

	@RequestMapping(value = "/api/tasks/{identifier}/star", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void starTask(@PathVariable String identifier) {
		commandGateway.sendAndWait(new StarTaskCommand(identifier));
	}

	@RequestMapping(value = "/api/tasks/{identifier}/unstar", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void unstarTask(@PathVariable String identifier) {
		throw new RuntimeException("Could not unstar task...");
		//commandGateway.sendAndWait(new UnstarTaskCommand(identifier));
	}

	@ExceptionHandler
	public void handleException(Principal principal, Throwable exception) {
		messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/errors", exception.getMessage());
	}

}
