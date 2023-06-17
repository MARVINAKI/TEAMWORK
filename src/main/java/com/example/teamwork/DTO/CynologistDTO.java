package com.example.teamwork.DTO;

import com.example.teamwork.model.Cynologist;
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
public class CynologistDTO {
	private Long id;
	private String fullName;
	private Integer experience;
	private String phoneNumber;
	private String eMail;
	private String comments;

	public CynologistDTO(String fullName, Integer experience, String phoneNumber, String eMail, String comments) {
		this.fullName = fullName;
		this.experience = experience;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.comments = comments;
	}

	/**
	 * Данный метод используется для преобразования из одного класса в другой. Сокращает код.
	 * @param cynologistDTO класс DTO, который будет преобразовываться
	 * @return получаем преобразованный класс
	 */
	public static Cynologist convertToCynologist(CynologistDTO cynologistDTO) {
		return new Cynologist(cynologistDTO.getFullName(), cynologistDTO.getExperience(), cynologistDTO.getPhoneNumber(), cynologistDTO.getEMail(), cynologistDTO.getComments());
	}
}
