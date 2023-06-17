package com.example.teamwork.service;

import com.example.teamwork.DTO.VolunteerCallDTO;
import com.example.teamwork.model.VolunteerCall;
import com.example.teamwork.repository.DogVolunteerRepository;
import com.example.teamwork.repository.VolunteerCallRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VolunteerCallService {

	private final VolunteerCallRepository volunteerCallRepository;
	private final DogVolunteerRepository dogVolunteerRepository;

	public VolunteerCallService(VolunteerCallRepository volunteerCallRepository, DogVolunteerRepository dogVolunteerRepository) {
		this.volunteerCallRepository = volunteerCallRepository;
		this.dogVolunteerRepository = dogVolunteerRepository;
	}

	/**
	 * Метод реализуется в обработчике {@link com.example.teamwork.handlers.buttonHandlers.dogHandler.DogShelterVolunteerHandler} для
	 * занесения запроса на обратную связь с клиентом в нашу БД. В процессе выполнения, назначается ответственный волонтёр приюта и время
	 * поступления запроса, который будет обрабатывать постувший запрос. Волонтёр назначается случайным образом из списка доступных волонтёров (выборка волонтёров из БД).
	 * {@link JpaRepository#findAll()}
	 * {@link JpaRepository#getReferenceById(Object)}
	 * {@link JpaRepository#save(Object)}
	 *
	 * @param chatId идентификационный номер чата для связи с клиентом
	 */
	public void registrationVolunteerCall(Long chatId) {
		List<Long> list = dogVolunteerRepository.getAllId();
		VolunteerCall volunteerCall = new VolunteerCall(chatId);
		volunteerCall.setDogVolunteer(dogVolunteerRepository.getReferenceById(list.get(RandomUtils.nextInt(0, list.size()))));
		volunteerCallRepository.save(volunteerCall);
	}

	/**
	 * Метод поиска конкретного чата пользователя, который оставлял запрос на консультацию
	 * {@link JpaRepository#getReferenceById(Object)}
	 *
	 * @param id идентификационный номер записи в БД
	 * @return идентификационный номер чата клиента для консультации
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public VolunteerCallDTO findVolunteerCall(Long id) {
		return VolunteerCall.convertToVolunteerCallDTO(volunteerCallRepository.getReferenceById(id));
	}

	/**
	 * Метод поиска необработанных вызовов волонтёров для консультации или помощи
	 *
	 * @return список актуальных чатов для ответа клиенту
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public List<VolunteerCallDTO> findAllVolunteerCalls() {
		List<VolunteerCallDTO> volunteersCall = new ArrayList<>();
		for (VolunteerCall volunteerCall : volunteerCallRepository.findAll()) {
			volunteersCall.add(VolunteerCall.convertToVolunteerCallDTO(volunteerCall));
		}
		return volunteersCall;
	}

	/**
	 * Метод удаления или закрытия запроса на консультацию в личном чате telegram
	 * {@link JpaRepository#deleteById(Object)}
	 *
	 * @param id идентификационный номер объекта для удаления.
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 */
	public void closeVolunteerCall(Long id) {
		volunteerCallRepository.deleteById(id);
	}
}
