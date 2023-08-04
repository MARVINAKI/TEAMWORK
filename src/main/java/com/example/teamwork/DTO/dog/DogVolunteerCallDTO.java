package com.example.teamwork.DTO.dog;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DogVolunteerCallDTO {

	private Long id;
	private Long chatId;
	private LocalDateTime requestTime;
	private Long dogVolunteerId;
}
