package com.example.teamwork.DTO.dog;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DogAdopterDTO {

	private Long id;
	private Long chatId;
	private String fullName;
	private String phoneNumber;
}
