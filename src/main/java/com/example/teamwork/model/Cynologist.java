package com.example.teamwork.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cynologists")
@NoArgsConstructor
@AllArgsConstructor
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
}
