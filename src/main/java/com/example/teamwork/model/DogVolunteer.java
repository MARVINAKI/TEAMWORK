package com.example.teamwork.model;

import com.example.teamwork.DTO.dog.DogVolunteerDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dog_volunteer")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DogVolunteer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "full_name")
	private String fullName;

	public DogVolunteer(String fullName) {
		this.fullName = fullName;
	}

	@OneToMany(mappedBy = "dogVolunteer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<DogFeedback> dogFeedbacks;

	@OneToMany(mappedBy = "dogVolunteer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<DogVolunteerCall> dogVolunteerCalls;

	public static DogVolunteerDTO convert(DogVolunteer dogVolunteer) {
		return new DogVolunteerDTO(
				dogVolunteer.getId(),
				dogVolunteer.getFullName());
	}
}