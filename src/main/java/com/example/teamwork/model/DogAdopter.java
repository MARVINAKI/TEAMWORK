package com.example.teamwork.model;

import com.example.teamwork.DTO.dog.DogAdopterDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "dog_adopter")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
@NoArgsConstructor
public class DogAdopter {
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

	public DogAdopter(Long chatId, String fullName, String phoneNumber) {
		this.chatId = chatId;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
	}

	@OneToOne(mappedBy = "dogAdopter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private DogRegister dogRegister;

	public static DogAdopterDTO convert(DogAdopter dogAdopter) {
		return new DogAdopterDTO(
				dogAdopter.getId(),
				dogAdopter.getChatId(),
				dogAdopter.getFullName(),
				dogAdopter.getPhoneNumber()
		);
	}
}
