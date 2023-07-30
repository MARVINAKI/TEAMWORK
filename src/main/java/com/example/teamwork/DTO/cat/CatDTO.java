package com.example.teamwork.DTO.cat;

import com.example.teamwork.enums.Disability;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CatDTO {

	private Long id;
	private String name;
	private Integer age;
	private Disability disability;
	private String comments;
}
