package com.abhilash.cqrs.query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TaskEntryRepository extends CrudRepository<TaskEntry, String> {
	Page<TaskEntry> findByUsernameAndCompleted(String username, boolean completed, Pageable pageable);
}
