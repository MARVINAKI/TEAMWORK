package com.example.teamwork.model;

import com.example.teamwork.DTO.dog.DogRegisterDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "dog_register")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
@NoArgsConstructor
public class DogRegister {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "adopters_chat_id")
	private Long adoptersChatId;
	@Column(name = "trial_period")
	private int trialPeriod;
	@Column(name = "registration_date")
	private LocalDateTime registrationDate;
	@Column(name = "last_date_of_reports")
	private LocalDateTime lastDateOfReports;

	public DogRegister(Long adoptersChatId, Dog dog, DogAdopter dogAdopter, int trialPeriod) {
		this.adoptersChatId = adoptersChatId;
		this.dog = dog;
		this.dogAdopter = dogAdopter;
		this.trialPeriod = trialPeriod;
		this.registrationDate = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "dog_id")
	private Dog dog;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "adopter_id")
	private DogAdopter dogAdopter;

	public static DogRegisterDTO convert(DogRegister dogRegister) {
		return new DogRegisterDTO(
				dogRegister.getId(),
				dogRegister.getAdoptersChatId(),
				dogRegister.getDog().getId(),
				dogRegister.getDogAdopter().getId(),
				dogRegister.getTrialPeriod(),
				dogRegister.getRegistrationDate(),
				dogRegister.getLastDateOfReports()
		);
	}
}
