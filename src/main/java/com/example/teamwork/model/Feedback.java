package com.example.teamwork.model;

import com.example.teamwork.DTO.FeedbackDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "feedback")
@NoArgsConstructor
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
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "request_time")
	private LocalDateTime requestTime;

	public Feedback(String fullName, String phoneNumber, String email, String comments) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.comments = comments;
	}

	public static FeedbackDTO convertToFeedbackDTO(Feedback feedback) {
		FeedbackDTO feedbackDTO = new FeedbackDTO( feedback.getFullName(), feedback.getPhoneNumber(), feedback.getEmail(), feedback.getComments(), feedback.getChatId());
		feedbackDTO.setId(feedback.getId());
		return feedbackDTO;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volunteer_id")
	private DogVolunteer dogVolunteer;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Feedback feedback = (Feedback) o;
		return Objects.equals(id, feedback.id) && Objects.equals(fullName, feedback.fullName) && Objects.equals(phoneNumber, feedback.phoneNumber) && Objects.equals(email, feedback.email) && Objects.equals(comments, feedback.comments) && Objects.equals(chatId, feedback.chatId) && Objects.equals(requestTime, feedback.requestTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullName, phoneNumber, email, comments, chatId, requestTime);
	}

	@Override
	public String toString() {
		return "Feedback{" +
				"fullName='" + fullName + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", email='" + email + '\'' +
				", comments='" + comments + '\'' +
				", chatId=" + chatId +
				", requestTime=" + requestTime +
				'}';
	}
}
