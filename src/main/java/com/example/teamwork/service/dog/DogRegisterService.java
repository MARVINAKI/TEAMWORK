package com.example.teamwork.service.dog;

import com.example.teamwork.DTO.dog.DogRegisterDTO;
import com.example.teamwork.model.DogRegister;
import com.example.teamwork.repository.dog.DogRegisterRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

	/**
	 * Внесение изменений по испытательному сроку волонтёром
	 *
	 * @param id - идентификационный номер журнала усыновителя
	 * @param trialPeriod - количество дней испытательного периода
	 */
	@Caching(
			evict = {@CacheEvict("dog_register")},
			put = {@CachePut("dog_register")},
			cacheable = {@Cacheable("dog_register")}
	)
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

	/**
	 * Поиск журнала усыновителя по идентификационному номеру телеграмм чата усыновителя
	 *
	 * @param adopterChatId - идентификационный номер телеграмм чата усыновителя
	 * @return - журнал усыновителя (если имеется)
	 */
	@Cacheable("dog_register")
	public Optional<DogRegister> findByAdoptersChatId(Long adopterChatId) {
		return dogRegisterRepository.findDogRegisterByAdoptersChatId(adopterChatId);
	}

	/**
	 * Поиск журнала усыновителя по идентификационному номеру усыновленного питомца
	 *
	 * @param dogId - идентификационный номер питомца
	 * @return - журнал усыновителя (если имеется)
	 */
	@Cacheable("dog_register")
	public Optional<DogRegister> findByDogId(Long dogId) {
		return dogRegisterRepository.findByDog_Id(dogId);
	}

	/**
	 * Поиск журнала усыновителя по идентификационному номеру усыновителя
	 *
	 * @param dogAdopterId - идентификационный номер усыновителя
	 * @return - журнал усыновителя (если имеется)
	 */
	@Cacheable("dog_register")
	public Optional<DogRegister> findByDogAdopterId(Long dogAdopterId) {
		return dogRegisterRepository.findByDogAdopter_Id(dogAdopterId);
	}

	@CacheEvict("dog_register")
	public void deleteDogRegister(Long id) {
		dogRegisterRepository.deleteById(id);
	}
}
