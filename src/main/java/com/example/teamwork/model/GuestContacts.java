package com.example.teamwork.model;

import javax.persistence.*;
import lombok.*;

@Entity(name = "guest_contacts")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class GuestContacts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	@Column(name = "full_name", nullable = false)
	private String fullName;
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	@Column(name = "email")
	private String email;
	@Column(name = "shelter")
	private String shelter;
	@Column(name = "comments")
	private String comments;

	public GuestContacts(String fullName, String phoneNumber, String email, String shelter, String comments) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.shelter = shelter;
		this.comments = comments;
	}
}
