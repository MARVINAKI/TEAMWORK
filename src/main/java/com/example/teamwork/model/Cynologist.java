package com.example.teamwork.model;

import javax.persistence.*;

import com.example.teamwork.DTO.CynologistDTO;
import lombok.*;

@Entity(name = "cynologists")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Cynologist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "experience")
	private Integer experience;
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "email")
	private String eMail;
	@Column(name = "comments")
	private String comments;

	public Cynologist(String fullName, Integer experience, String phoneNumber, String eMail, String comments) {
		this.fullName = fullName;
		this.experience = experience;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.comments = comments;
	}

	public static CynologistDTO convertToCynologistDTO(Cynologist cynologist) {
		CynologistDTO cynologistDTO = new CynologistDTO(cynologist.getFullName(), cynologist.getExperience(), cynologist.getPhoneNumber(), cynologist.getEMail(), cynologist.getComments());
		cynologistDTO.setId(cynologist.getId());
		return cynologistDTO;
	}
}
