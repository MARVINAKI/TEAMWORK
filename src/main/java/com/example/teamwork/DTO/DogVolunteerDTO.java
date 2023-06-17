package com.example.teamwork.DTO;

import com.example.teamwork.model.DogVolunteer;
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
public class DogVolunteerDTO {

	private Long id;
	private String fullName;

	public DogVolunteerDTO(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Данный метод используется для преобразования из одного класса в другой. Сокращает код.
	 * @param dogVolunteerDTO класс DTO, который будет преобразовываться
	 * @return получаем преобразованный класс
	 */
	public static DogVolunteer convertToDogVolunteer(DogVolunteerDTO dogVolunteerDTO) {
		return new DogVolunteer(dogVolunteerDTO.getFullName());
	}
}
