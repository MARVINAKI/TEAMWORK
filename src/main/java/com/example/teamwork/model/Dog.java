package com.example.teamwork.model;

import com.example.teamwork.DTO.dog.DogDTO;
import com.example.teamwork.constant.Disability;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "dogs")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "id")
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
	@Enumerated(EnumType.STRING)
	private Disability disability;
	@Column(name = "comments")
	private String comments;

	public Dog(String name, Integer age, Disability disability, String comments) {
		this.name = name;
		this.age = age;
		this.disability = disability;
		this.comments = comments;
	}

	@OneToOne(mappedBy = "dog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private DogRegister dogRegister;

	public static DogDTO convert(Dog dog) {
		return new DogDTO(
				dog.getId(),
				dog.getName(),
				dog.getAge(),
				dog.getDisability(),
				dog.getComments()
		);
	}
}
