package com.example.teamwork.model;

import javax.persistence.*;

import com.example.teamwork.DTO.CatDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cats")
@NoArgsConstructor
@Getter
@Setter
public class Cat {

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

	public Cat(String name, Integer age, String disability, String comments) {
		this.name = name;
		this.age = age;
		this.disability = disability;
		this.comments = comments;
	}

	public static CatDTO convertToCatDTO(Cat cat) {
		CatDTO catDTO = new CatDTO(cat.getName(), cat.getAge(), cat.getDisability(), cat.getComments());
		catDTO.setId(cat.getId());
		return catDTO;
	}
}
