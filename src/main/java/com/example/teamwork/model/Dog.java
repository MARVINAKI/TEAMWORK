package com.example.teamwork.model;

import javax.persistence.*;

import lombok.*;



@Entity
@Table(name = "dogs")
@Getter
@Setter
@NoArgsConstructor
public class Dog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "age")
	private Integer age;
	@Column(name = "disability")
	private String disability;
	@Column(name = "comments")
	private String comments;

	public Dog(String name, Integer age, String disability, String comments) {
		this.name = name;
		this.age = age;
		this.disability = disability;
		this.comments = comments;
	}
}
