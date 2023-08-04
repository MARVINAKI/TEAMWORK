package com.example.teamwork.DTO.dog;

import com.example.teamwork.enums.Disability;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DogDTO {

	private Long id;
	private String name;
	private Integer age;
	private Disability disability;
	private String comments;
}
