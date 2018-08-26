package com.abhilash.cqrs.query;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = { "id" })
public class TaskEntry {

	@Id
	private String id;

	private String username;

	@Setter
	private String title;

	@Setter
	private boolean completed;

	@Setter
	private boolean starred;
}
