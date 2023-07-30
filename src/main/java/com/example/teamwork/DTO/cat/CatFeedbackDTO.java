package com.example.teamwork.DTO.cat;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CatFeedbackDTO {

	private Long id;
	private String fullName;
	private String phoneNumber;
	private String eMail;
	private String comments;
	private Long dogVolunteerId;
}
