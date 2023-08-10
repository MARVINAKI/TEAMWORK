package com.example.teamwork.service.cat;

import com.example.teamwork.DTO.cat.CatRegisterDTO;
import com.example.teamwork.model.CatRegister;
import com.example.teamwork.repository.cat.CatRegisterRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

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

	/**
	 * Внесение изменений по испытательному сроку волонтёром
	 *
	 * @param id - идентификационный номер журнала усыновителя
	 * @param trialPeriod - количество дней испытательного периода
	 */
	@Caching(
			evict = {@CacheEvict("cat_register")},
			put = {@CachePut("cat_register")},
			cacheable = {@Cacheable("cat_register")}
	)
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

	/**
	 * Поиск журнала усыновителя по идентификационному номеру телеграмм чата усыновителя
	 *
	 * @param adopterChatId - идентификационный номер телеграмм чата усыновителя
	 * @return - журнал усыновителя (если имеется)
	 */
	@Cacheable("cat_register")
	public Optional<CatRegister> findByAdoptersChatId(Long adopterChatId) {
		return catRegisterRepository.findByAdoptersChatId(adopterChatId);
	}

	/**
	 * Поиск журнала усыновителя по идентификационному номеру усыновленного питомца
	 *
	 * @param catId - идентификационный номер питомца
	 * @return - журнал усыновителя (если имеется)
	 */
	@Cacheable("cat_register")
	public Optional<CatRegister> findByCatId(Long catId) {
		return catRegisterRepository.findByCat_Id(catId);
	}

	/**
	 * Поиск журнала усыновителя по идентификационному номеру усыновителя
	 *
	 * @param catAdopterId - идентификационный номер усыновителя
	 * @return - журнал усыновителя (если имеется)
	 */
	@Cacheable("cat_register")
	public Optional<CatRegister> findByCatAdopterId(Long catAdopterId) {
		return catRegisterRepository.findByCatAdopter_Id(catAdopterId);
	}

	@CacheEvict("cat_register")
	public void deleteCatRegister(Long id) {
		catRegisterRepository.deleteById(id);
	}
}
