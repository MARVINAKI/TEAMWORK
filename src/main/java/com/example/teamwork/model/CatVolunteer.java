package com.example.teamwork.model;

import com.example.teamwork.DTO.cat.CatVolunteerDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

	@OneToMany(mappedBy = "catVolunteer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CatFeedback> catFeedbacks;

	@OneToMany(mappedBy = "catVolunteer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<CatVolunteerCall> catVolunteerCalls;

	public static CatVolunteerDTO convert(CatVolunteer catVolunteer) {
		return new CatVolunteerDTO(
				catVolunteer.getId(),
				catVolunteer.getFullName()
		);
	}
}
