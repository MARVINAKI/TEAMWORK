package com.example.teamwork.DTO;

import com.example.teamwork.model.Feedback;
import lombok.*;

/**
 * Класс DTO предназначен для передачи данных между подсистемами.
 * Данный класс DTO также используется для избавления проблем с сериализацией
 * и для упрощения работ в веб-интерфейсе swagger.
 * @author Kostya
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FeedbackDTO {
	private Long id;
	private String fullName;
	private String phoneNumber;
	private String email;
	private String comments;
	private Long chatId;

	public FeedbackDTO(String fullName, String phoneNumber, String email, String comments, Long chatId) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.comments = comments;
		this.chatId = chatId;
	}

	/**
	 * Данный метод используется для преобразования из одного класса в другой. Сокращает код.
	 * @param feedbackDTO класс DTO, который будет преобразовываться
	 * @return получаем преобразованный класс
	 */
	public static Feedback convertToFeedback(FeedbackDTO feedbackDTO) {
		return new Feedback(feedbackDTO.getFullName(), feedbackDTO.getPhoneNumber(), feedbackDTO.getEmail(), feedbackDTO.getComments());
	}
}
