package com.example.teamwork.DTO;

import com.example.teamwork.model.Dog;
import lombok.*;

/**
 * Класс DTO предназначен для передачи данных между подсистемами.
 * Данный класс DTO также используется для избавления проблем с сериализацией
 * и для упрощения работ в веб-интерфейсе swagger.
 * @author Kostya
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DogDTO {
	private Long id;
	private String name;
	private Integer age;
	private String disability;
	private String comments;

	public DogDTO(String name, Integer age, String disability, String comments) {
		this.name = name;
		this.age = age;
		this.disability = disability;
		this.comments = comments;
	}

	/**
	 * Данный метод используется для преобразования из одного класса в другой. Сокращает код.
	 * @param dogDTO класс DTO, который будет преобразовываться
	 * @return получаем преобразованный класс
	 */
	public static Dog convertToDog(DogDTO dogDTO) {
		return new Dog(dogDTO.getName(), dogDTO.getAge(), dogDTO.getDisability(), dogDTO.getComments());
	}
}
