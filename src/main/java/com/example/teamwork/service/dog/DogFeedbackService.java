package com.example.teamwork.service.dog;

import com.example.teamwork.DTO.dog.DogFeedbackDTO;
import com.example.teamwork.handlers.messageHandlers.dog.DogFeedbackHandler;
import com.example.teamwork.model.DogFeedback;
import com.example.teamwork.model.DogVolunteer;
import com.example.teamwork.repository.dog.DogFeedbackRepository;
import com.example.teamwork.repository.dog.DogVolunteerRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogFeedbackService {

	private final DogFeedbackRepository dogFeedbackRepository;
	private final DogVolunteerRepository dogVolunteerRepository;

	public DogFeedbackService(DogFeedbackRepository dogFeedbackRepository, DogVolunteerRepository dogVolunteerRepository) {
		this.dogFeedbackRepository = dogFeedbackRepository;
		this.dogVolunteerRepository = dogVolunteerRepository;
	}

	/**
	 * Метод реализуется в обработчике {@link DogFeedbackHandler} для
	 * занесения запроса на обратную связь с клиентом в нашу БД. В процессе выполнения, назначается ответственный волонтёр приюта и время поступления запроса,
	 * который будет обрабатывать постувший запрос. Волонтёр назначается случайным образом из списка доступных волонтёров (выборка волонтёров из БД).
	 * {@link JpaRepository#findAll()}
	 * {@link JpaRepository#getReferenceById(Object)}
	 * {@link JpaRepository#save(Object)}
	 *
	 * @param dogFeedback класс форма от клиента с дополнительными полями (вносимыми программой) для дальнейшей обработки.
	 */
	public void addClientRequest(DogFeedback dogFeedback) {
		List<Long> list = dogVolunteerRepository.getAllId();
		if (list.size() != 0) {
			Optional<DogVolunteer> dogVolunteer = dogVolunteerRepository.findById(list.get(RandomUtils.nextInt(0, list.size())));
			dogFeedback.setDogVolunteer(dogVolunteer.isEmpty() ? null : dogVolunteer.get());
			dogFeedbackRepository.save(dogFeedback);
		}
	}

	/**
	 * Метод поиска открытых, необработанных запросов на обратную связь с клиентом для дальнейшей отработки по ним.
	 * {@link JpaRepository#findAll()}
	 *
	 * @return список необработанных запросов на обратную связь с клиентом.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 */
	public List<DogFeedbackDTO> findOpenRequests() {
		List<DogFeedbackDTO> listDTO = new ArrayList<>();
		for (DogFeedback dogFeedback : dogFeedbackRepository.findAll()) {
			listDTO.add(DogFeedback.convert(dogFeedback));
		}
		return listDTO;
	}

	/**
	 * Метод удаления или закрытия запроса на обратную связь с клиентом после выполнения
	 * {@link JpaRepository#deleteById(Object)}
	 *
	 * @param id идентификационный номер объекта для удаления.
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 */
	public void closeProcessedRequest(Long id) {
		dogFeedbackRepository.deleteById(id);
	}
}
