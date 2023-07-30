package com.example.teamwork.model;

import com.example.teamwork.DTO.cat.CatAdopterDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cat_adopter")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
@NoArgsConstructor
public class CatAdopter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "phone_number")
	private String phoneNumber;

	public CatAdopter(Long chatId, String fullName, String phoneNumber) {
		this.chatId = chatId;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
	}

	@OneToOne(mappedBy = "catAdopter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private CatRegister catRegister;

	public static CatAdopterDTO convert(CatAdopter catAdopter) {
		return new CatAdopterDTO(
				catAdopter.getId(),
				catAdopter.getChatId(),
				catAdopter.getFullName(),
				catAdopter.getPhoneNumber()
		);
	}
}
