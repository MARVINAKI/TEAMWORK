package com.example.teamwork.model;

import com.example.teamwork.DTO.CatVolunteerDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cat_volunteer")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CatVolunteer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "full_name")
	private String fullName;

	public CatVolunteer(String fullName) {
		this.fullName = fullName;
	}

	public static CatVolunteerDTO convertToCatVolunteerDTO(CatVolunteer catVolunteer) {
		CatVolunteerDTO catVolunteerDTO = new CatVolunteerDTO(catVolunteer.getFullName());
		catVolunteerDTO.setId(catVolunteer.getId());
		return catVolunteerDTO;
	}
}
