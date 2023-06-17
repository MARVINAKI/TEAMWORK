package com.example.teamwork.DTO;

import com.example.teamwork.model.VolunteerCall;
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
public class VolunteerCallDTO {
	private Long id;
	private Long chatId;

	public VolunteerCallDTO(Long chatId) {
		this.chatId = chatId;
	}

	/**
	 * Данный метод используется для преобразования из одного класса в другой. Сокращает код.
	 * @param volunteerCallDTO класс DTO, который будет преобразовываться
	 * @return получаем преобразованный класс
	 */
	public static VolunteerCall convertToVolunteerCall(VolunteerCallDTO volunteerCallDTO) {
		return new VolunteerCall(volunteerCallDTO.getChatId());
	}
}
