package com.example.teamwork.DTO.dog;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DogRegisterDTO {

	private Long id;
	private Long adoptersChatId;
	private Long dogId;
	private Long adopterId;
	private Integer trialPeriod;
	private LocalDateTime registrationDate;
	private LocalDateTime lastDateOfReports;
}
