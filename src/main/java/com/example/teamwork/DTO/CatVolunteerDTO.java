package com.example.teamwork.DTO;

import com.example.teamwork.model.CatVolunteer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class CatVolunteerDTO {

	private Long id;
	private String fullName;

	public CatVolunteerDTO(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Данный метод используется для преобразования из одного класса в другой. Сокращает код.
	 * @param catVolunteerDTO класс DTO, который будет преобразовываться
	 * @return получаем преобразованный класс
	 */
	public static CatVolunteer convertToCatVolunteer(CatVolunteerDTO catVolunteerDTO) {
		return new CatVolunteer(catVolunteerDTO.getFullName());
	}
}
