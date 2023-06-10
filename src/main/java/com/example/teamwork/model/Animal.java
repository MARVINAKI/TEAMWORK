package com.example.teamwork.model;

import com.example.teamwork.model.ENUM.Disability;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter
public abstract class Animal {

	private String name;
	private Integer age;
	private Disability disability;
	private String comments;

	public Animal(String name, Integer age, Disability disability, String comments) {
		this.name = name;
		this.age = age;
		this.disability = disability;
		this.comments = comments;
	}
}
