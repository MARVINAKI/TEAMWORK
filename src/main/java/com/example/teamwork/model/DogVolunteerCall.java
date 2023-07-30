package com.example.teamwork.model;

import com.example.teamwork.DTO.dog.DogVolunteerCallDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "volunteer_call_dog")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DogVolunteerCall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "request_time")
	private LocalDateTime requestTime;

	public DogVolunteerCall(Long chatId) {
		this.chatId = chatId;
		this.requestTime = LocalDateTime.now();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volunteer_id")
	private DogVolunteer dogVolunteer;

	public static DogVolunteerCallDTO convert(DogVolunteerCall dogVolunteerCall) {
		return new DogVolunteerCallDTO(
				dogVolunteerCall.getId(),
				dogVolunteerCall.getChatId(),
				dogVolunteerCall.getRequestTime(),
				dogVolunteerCall.getDogVolunteer().getId()
		);
	}
}
