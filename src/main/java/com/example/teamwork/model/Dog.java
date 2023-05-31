package com.example.teamwork.model;

import com.example.teamwork.model.ENUM.Disability;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;


@Entity(name = "dogs")
@NoArgsConstructor
public class Dog extends Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Dog(String name, Integer age, Disability disability, String comments) {
		super(name, age, disability, comments);
	}

	public Long getId() {
		return id;
	}
}
