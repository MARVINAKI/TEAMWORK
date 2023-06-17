package com.example.teamwork.model;

import com.example.teamwork.DTO.VolunteerCallDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "volunteer_call")
@NoArgsConstructor
@Getter
@Setter
public class VolunteerCall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "chat_id")
	private Long chatId;
	@Column(name = "request_time")
	private LocalDateTime requestTime;

	public VolunteerCall(Long chatId) {
		this.chatId = chatId;
		this.requestTime = LocalDateTime.now();
	}

	public static VolunteerCallDTO convertToVolunteerCallDTO(VolunteerCall volunteerCall) {
		VolunteerCallDTO volunteerCallDTO = new VolunteerCallDTO(volunteerCall.getChatId());
		volunteerCallDTO.setId(volunteerCall.getId());
		return volunteerCallDTO;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "volunteer_id")
	private DogVolunteer dogVolunteer;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VolunteerCall that = (VolunteerCall) o;
		return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(requestTime, that.requestTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, chatId, requestTime);
	}

	@Override
	public String toString() {
		return "VolunteerCall{" +
				"chatId=" + chatId +
				", requestTime=" + requestTime +
				'}';
	}
}
