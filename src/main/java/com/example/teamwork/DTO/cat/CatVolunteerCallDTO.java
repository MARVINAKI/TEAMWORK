package com.example.teamwork.DTO.cat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CatVolunteerCallDTO {

	private Long id;
	private Long chatId;
	private LocalDateTime requestTime;
	private Long dogVolunteerId;
}
