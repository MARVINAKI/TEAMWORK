package com.example.teamwork.service.cat;

import com.example.teamwork.DTO.cat.CatVolunteerCallDTO;
import com.example.teamwork.handlers.buttonHandlers.catHandler.CatShelterVolunteerHandler;
import com.example.teamwork.model.CatVolunteer;
import com.example.teamwork.model.CatVolunteerCall;
import com.example.teamwork.repository.cat.CatVolunteerCallRepository;
import com.example.teamwork.repository.cat.CatVolunteerRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatVolunteerCallService {

	private final CatVolunteerCallRepository catVolunteerCallRepository;
	private final CatVolunteerRepository catVolunteerRepository;

	public CatVolunteerCallService(CatVolunteerCallRepository catVolunteerCallRepository, CatVolunteerRepository catVolunteerRepository) {
		this.catVolunteerCallRepository = catVolunteerCallRepository;
		this.catVolunteerRepository = catVolunteerRepository;
	}

	/**
	 * Метод реализуется в обработчике {@link CatShelterVolunteerHandler} для
	 * занесения запроса на обратную связь с клиентом в нашу БД. В процессе выполнения, назначается ответственный волонтёр приюта и время
	 * поступления запроса, который будет обрабатывать постувший запрос. Волонтёр назначается случайным образом из списка доступных волонтёров (выборка волонтёров из БД).
	 * {@link JpaRepository#findAll()}
	 * {@link JpaRepository#getReferenceById(Object)}
	 * {@link JpaRepository#save(Object)}
	 *
	 * @param chatId идентификационный номер чата для связи с клиентом
	 */
	public void registrationVolunteerCall(Long chatId) {
		List<Long> list = catVolunteerRepository.getAllId();
		CatVolunteerCall catVolunteerCall = new CatVolunteerCall(chatId);
		Optional<CatVolunteer> catVolunteer = catVolunteerRepository.findById(list.get(RandomUtils.nextInt(0, list.size())));
		catVolunteerCall.setCatVolunteer(catVolunteer.isEmpty() ? null : catVolunteer.get());
		catVolunteerCallRepository.save(catVolunteerCall);
	}

	/**
	 * Метод поиска конкретного чата пользователя, который оставлял запрос на консультацию
	 * {@link JpaRepository#getReferenceById(Object)}
	 *
	 * @param id идентификационный номер записи в БД
	 * @return идентификационный номер чата клиента для консультации
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public Optional<CatVolunteerCall> findById(Long id) {
		return catVolunteerCallRepository.findById(id);
	}

	/**
	 * Метод поиска необработанных вызовов волонтёров для консультации или помощи
	 *
	 * @return список актуальных чатов для ответа клиенту
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public List<CatVolunteerCallDTO> findAllVolunteerCalls() {
		List<CatVolunteerCallDTO> listDTO = new ArrayList<>();
		for (CatVolunteerCall catVolunteerCall : catVolunteerCallRepository.findAll()) {
			listDTO.add(CatVolunteerCall.convert(catVolunteerCall));
		}
		return listDTO;
	}

	/**
	 * Метод удаления или закрытия запроса на консультацию в личном чате telegram
	 * {@link JpaRepository#deleteById(Object)}
	 *
	 * @param id идентификационный номер объекта для удаления.
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 */
	public void closeVolunteerCall(Long id) {
		catVolunteerCallRepository.deleteById(id);
	}
}
