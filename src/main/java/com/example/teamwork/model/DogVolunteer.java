package com.example.teamwork.model;

import com.example.teamwork.DTO.DogVolunteerDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dog_volunteer")
@NoArgsConstructor
@Getter
@Setter
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

	public static DogVolunteerDTO convertToDogVolunteerDTO(DogVolunteer dogVolunteer) {
		DogVolunteerDTO dogVolunteerDTO = new DogVolunteerDTO(dogVolunteer.getFullName());
		dogVolunteerDTO.setId(dogVolunteer.getId());
		return dogVolunteerDTO;
	}

	@OneToMany(mappedBy = "dogVolunteer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Feedback> feedbacks;

	@OneToMany(mappedBy = "dogVolunteer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<VolunteerCall> volunteerCalls;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DogVolunteer that = (DogVolunteer) o;
		return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, fullName);
	}

	@Override
	public String toString() {
		return "DogVolunteer{" +
				"fullName='" + fullName + '\'' +
				'}';
	}
}
