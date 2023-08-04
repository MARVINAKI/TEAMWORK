package com.example.teamwork.DTO.dog;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DogFeedbackDTO {

	private Long id;
	private String fullName;
	private String phoneNumber;
	private String eMail;
	private String comments;
	private Long dogVolunteerId;
}
