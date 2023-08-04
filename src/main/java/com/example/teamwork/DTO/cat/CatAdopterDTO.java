package com.example.teamwork.DTO.cat;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CatAdopterDTO {

	private Long id;
	private Long chatId;
	private String fullName;
	private String phoneNumber;
}
