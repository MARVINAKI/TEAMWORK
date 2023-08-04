package com.example.teamwork.model;

import com.example.teamwork.DTO.ReportDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "description")
	private String description;
	@Column(name = "dispatch_time")
	private LocalDateTime dispatchTime;
	@Column(name = "status")
	private boolean reportsStatus;

	public Report(String fileName, String description) {
		this.fileName = fileName;
		this.description = description;
	}

	public static ReportDTO convert(Report report) {
		return new ReportDTO(
				report.getId(),
				report.getChatId(),
				report.getFileName(),
				report.getDescription(),
				report.getDispatchTime(),
				report.isReportsStatus()
		);
	}
}
