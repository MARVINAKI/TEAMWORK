package com.example.teamwork.model;

import com.example.teamwork.DTO.cat.CatVolunteerCallDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "volunteer_call_cat")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CatVolunteerCall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "request_time")
	private LocalDateTime requestTime;

	public CatVolunteerCall(Long chatId) {
		this.chatId = chatId;
		this.requestTime = LocalDateTime.now();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volunteer_id")
	private CatVolunteer catVolunteer;

	public static CatVolunteerCallDTO convert(CatVolunteerCall catVolunteerCall) {
		return new CatVolunteerCallDTO(
				catVolunteerCall.getId(),
				catVolunteerCall.getChatId(),
				catVolunteerCall.getRequestTime(),
				catVolunteerCall.getCatVolunteer().getId()
		);
	}
}
