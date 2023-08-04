package com.example.teamwork.model;

import com.example.teamwork.DTO.cat.CatRegisterDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "cat_register")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
@NoArgsConstructor
public class CatRegister {
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

	public CatRegister(Long adoptersChatId, Cat cat, CatAdopter catAdopter, int trialPeriod) {
		this.adoptersChatId = adoptersChatId;
		this.cat = cat;
		this.catAdopter = catAdopter;
		this.trialPeriod = trialPeriod;
		this.registrationDate = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cat_id")
	private Cat cat;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "adopter_id")
	private CatAdopter catAdopter;

	public static CatRegisterDTO convert(CatRegister catRegister) {
		return new CatRegisterDTO(
				catRegister.getId(),
				catRegister.getAdoptersChatId(),
				catRegister.getCat().getId(),
				catRegister.getCatAdopter().getId(),
				catRegister.getTrialPeriod(),
				catRegister.getRegistrationDate(),
				catRegister.getLastDateOfReports()
		);
	}
}
