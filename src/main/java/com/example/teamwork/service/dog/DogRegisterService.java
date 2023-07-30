package com.example.teamwork.service.dog;

import com.example.teamwork.DTO.dog.DogRegisterDTO;
import com.example.teamwork.model.DogRegister;
import com.example.teamwork.repository.dog.DogRegisterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogRegisterService {

	private final DogRegisterRepository dogRegisterRepository;

	public DogRegisterService(DogRegisterRepository dogRegisterRepository) {
		this.dogRegisterRepository = dogRegisterRepository;
	}

	public void addDogRegister(DogRegister dogRegister) {
		dogRegisterRepository.save(dogRegister);
	}

	public void updateReportsDate(Long chatId, LocalDateTime dateTime) {
		Optional<DogRegister> dogRegister = dogRegisterRepository.findDogRegisterByAdoptersChatId(chatId);
		dogRegister.ifPresent(value -> {
			value.setLastDateOfReports(dateTime.truncatedTo(ChronoUnit.DAYS));
			dogRegisterRepository.saveAndFlush(value);
		});
	}

	public void updateTrialPeriod(Long id, Integer trialPeriod) {
		dogRegisterRepository.findById(id).ifPresent(value -> {
			value.setTrialPeriod(trialPeriod);
			dogRegisterRepository.saveAndFlush(value);
		});

	}

	public List<DogRegisterDTO> findAll() {
		List<DogRegisterDTO> listDTO = new ArrayList<>();
		for (DogRegister dogRegister : dogRegisterRepository.findAll()) {
			listDTO.add(DogRegister.convert(dogRegister));
		}
		return listDTO;
	}

	public Optional<DogRegister> findByAdoptersChatId(Long adopterChatId) {
		return dogRegisterRepository.findDogRegisterByAdoptersChatId(adopterChatId);
	}

	public Optional<DogRegister> findByDogId(Long dogId) {
		return dogRegisterRepository.findByDog_Id(dogId);
	}

	public Optional<DogRegister> findByDogAdopterId(Long dogAdopterId) {
		return dogRegisterRepository.findByDogAdopter_Id(dogAdopterId);
	}

	public void deleteDogRegister(Long id) {
		dogRegisterRepository.deleteById(id);
	}
}
