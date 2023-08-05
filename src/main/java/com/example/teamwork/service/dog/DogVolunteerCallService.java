package com.example.teamwork.service.dog;

import com.example.teamwork.DTO.dog.DogVolunteerCallDTO;
import com.example.teamwork.handlers.buttonHandlers.VolunteerHandler;
import com.example.teamwork.model.DogVolunteer;
import com.example.teamwork.model.DogVolunteerCall;
import com.example.teamwork.repository.dog.DogVolunteerCallRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogVolunteerCallService {

	private final DogVolunteerCallRepository dogVolunteerCallRepository;
	private final DogVolunteerService dogVolunteerService;

	public DogVolunteerCallService(DogVolunteerCallRepository dogVolunteerCallRepository, DogVolunteerService dogVolunteerService) {
		this.dogVolunteerCallRepository = dogVolunteerCallRepository;
		this.dogVolunteerService = dogVolunteerService;
	}

	/**
	 * Метод реализуется в обработчике {@link VolunteerHandler} для
	 * занесения запроса на обратную связь с клиентом в нашу БД. В процессе выполнения, назначается ответственный волонтёр приюта и время
	 * поступления запроса, который будет обрабатывать постувший запрос. Волонтёр назначается случайным образом из списка доступных волонтёров (выборка волонтёров из БД).
	 * {@link JpaRepository#findAll()}
	 * {@link JpaRepository#getReferenceById(Object)}
	 * {@link JpaRepository#save(Object)}
	 *
	 * @param chatId идентификационный номер чата для связи с клиентом
	 */
	public void registrationVolunteerCall(Long chatId) {
		List<Long> list = dogVolunteerService.findAllIds();
		DogVolunteerCall dogVolunteerCall = new DogVolunteerCall(chatId);
		Optional<DogVolunteer> dogVolunteer = dogVolunteerService.findById(list.get(RandomUtils.nextInt(0, list.size())));
		dogVolunteerCall.setDogVolunteer(dogVolunteer.isEmpty() ? null : dogVolunteer.get());
		dogVolunteerCallRepository.save(dogVolunteerCall);
	}

	/**
	 * Метод поиска конкретного чата пользователя, который оставлял запрос на консультацию
	 * {@link JpaRepository#getReferenceById(Object)}
	 *
	 * @param id идентификационный номер записи в БД
	 * @return идентификационный номер чата клиента для консультации
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public Optional<DogVolunteerCall> findById(Long id) {
		return dogVolunteerCallRepository.findById(id);
	}

	/**
	 * Метод поиска необработанных вызовов волонтёров для консультации или помощи
	 *
	 * @return список актуальных чатов для ответа клиенту
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public List<DogVolunteerCallDTO> findAllVolunteerCalls() {
		List<DogVolunteerCallDTO> listDTO = new ArrayList<>();
		for (DogVolunteerCall dogVolunteerCall : dogVolunteerCallRepository.findAll()) {
			listDTO.add(DogVolunteerCall.convert(dogVolunteerCall));
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
		dogVolunteerCallRepository.deleteById(id);
	}
}
