package com.example.teamwork.model;

import com.example.teamwork.DTO.cat.CatDTO;
import com.example.teamwork.constant.Disability;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cats")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
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
	@Enumerated(EnumType.STRING)
	private Disability disability;
	@Column(name = "comments")
	private String comments;

	public Cat(String name, Integer age, Disability disability, String comments) {
		this.name = name;
		this.age = age;
		this.disability = disability;
		this.comments = comments;
	}

	@OneToOne(mappedBy = "cat", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private CatRegister catRegister;

	public static CatDTO convert(Cat cat) {
		return new CatDTO(
				cat.getId(),
				cat.getName(),
				cat.getAge(),
				cat.getDisability(),
				cat.getComments()
		);
	}
}
