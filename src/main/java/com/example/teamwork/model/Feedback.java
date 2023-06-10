package com.example.teamwork.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name = "id")
	private Long id;
	@Column(name = "full_name", nullable = false)
	private String fullName;
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "comments")
	private String comments;
	@Column(name = "response_status")
	private boolean responseStatus;
	@Column(name = "employee_name")
	private String employeeName;
	@Column(name = "request_time")
	private LocalDateTime requestTime;
	@Column(name = "response_time")
	private LocalDateTime responseTime;

	public Feedback(String fullName, String phoneNumber, String email, String comments) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.comments = comments;
	}
}
