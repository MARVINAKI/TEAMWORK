package com.example.teamwork.service.cat;

import com.example.teamwork.DTO.cat.CatRegisterDTO;
import com.example.teamwork.model.CatRegister;
import com.example.teamwork.repository.cat.CatRegisterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatRegisterService {

	private final CatRegisterRepository catRegisterRepository;

	public CatRegisterService(CatRegisterRepository catRegisterRepository) {
		this.catRegisterRepository = catRegisterRepository;
	}

	public void addCatRegister(CatRegister catRegister) {
		catRegisterRepository.save(catRegister);
	}

	public void updateReportsDate(Long chatId, LocalDateTime dateTime) {
		Optional<CatRegister> catRegister = catRegisterRepository.findByAdoptersChatId(chatId);
		catRegister.ifPresent(value -> {
			value.setLastDateOfReports(dateTime.truncatedTo(ChronoUnit.DAYS));
			catRegisterRepository.saveAndFlush(value);
		});
	}

	public void updateTrialPeriod(Long id, Integer trialPeriod) {
		catRegisterRepository.findById(id).ifPresent(value -> {
			value.setTrialPeriod(trialPeriod);
			catRegisterRepository.saveAndFlush(value);
		});
	}

	public List<CatRegisterDTO> findAll() {
		List<CatRegisterDTO> listDTO = new ArrayList<>();
		for (CatRegister catRegister : catRegisterRepository.findAll()) {
			listDTO.add(CatRegister.convert(catRegister));
		}
		return listDTO;
	}

	public Optional<CatRegister> findByAdoptersChatId(Long adopterChatId) {
		return catRegisterRepository.findByAdoptersChatId(adopterChatId);
	}

	public Optional<CatRegister> findByCatId(Long catId) {
		return catRegisterRepository.findByCat_Id(catId);
	}

	public Optional<CatRegister> findByCatAdopterId(Long catAdopterId) {
		return catRegisterRepository.findByCatAdopter_Id(catAdopterId);
	}

	public void deleteCatRegister(Long id) {
		catRegisterRepository.deleteById(id);
	}
}
