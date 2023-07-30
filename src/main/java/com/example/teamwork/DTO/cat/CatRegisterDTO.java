package com.example.teamwork.DTO.cat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CatRegisterDTO {

	private Long id;
	private Long adoptersChatId;
	private Long dogId;
	private Long adopterId;
	private Integer trialPeriod;
	private LocalDateTime registrationDate;
	private LocalDateTime lastDateOfReports;
}
