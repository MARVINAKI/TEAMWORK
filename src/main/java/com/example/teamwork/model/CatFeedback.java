package com.example.teamwork.model;

import com.example.teamwork.DTO.cat.CatFeedbackDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_cat")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
public class CatFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "email")
	private String email;
	@Column(name = "comments")
	private String comments;
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "request_time")
	private LocalDateTime requestTime;

	public CatFeedback(String fullName, String phoneNumber, String email, String comments) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.comments = comments;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volunteer_id")
	private CatVolunteer catVolunteer;

	public static CatFeedbackDTO convert(CatFeedback catFeedback) {
		return new CatFeedbackDTO(
				catFeedback.getId(),
				catFeedback.getFullName(),
				catFeedback.getPhoneNumber(),
				catFeedback.getEmail(),
				catFeedback.getComments(),
				catFeedback.getCatVolunteer().getId()
		);
	}
}
