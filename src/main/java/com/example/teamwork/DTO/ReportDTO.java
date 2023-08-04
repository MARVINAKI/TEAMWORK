package com.example.teamwork.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

	private Long id;
	private Long chatId;
	private String fileName;
	private String description;
	private LocalDateTime dispatchTime;
	private Boolean reportsStatus;
}
