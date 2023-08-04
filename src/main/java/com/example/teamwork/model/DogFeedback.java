package com.example.teamwork.model;

import com.example.teamwork.DTO.dog.DogFeedbackDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_dog")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
public class DogFeedback {
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
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "request_time")
	private LocalDateTime requestTime;

	public DogFeedback(String fullName, String phoneNumber, String email, String comments) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.comments = comments;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volunteer_id")
	private DogVolunteer dogVolunteer;

	public static DogFeedbackDTO convert(DogFeedback dogFeedback) {
		return new DogFeedbackDTO(
				dogFeedback.getId(),
				dogFeedback.getFullName(),
				dogFeedback.getPhoneNumber(),
				dogFeedback.getEmail(),
				dogFeedback.getComments(),
				dogFeedback.getDogVolunteer().getId()
		);
	}

}
