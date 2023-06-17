package com.example.teamwork.service;

import com.example.teamwork.DTO.FeedbackDTO;
import com.example.teamwork.model.Feedback;
import com.example.teamwork.repository.DogVolunteerRepository;
import com.example.teamwork.repository.FeedbackRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;
	private final DogVolunteerRepository dogVolunteerRepository;

	public FeedbackService(FeedbackRepository feedbackRepository, DogVolunteerRepository dogVolunteerRepository) {
		this.feedbackRepository = feedbackRepository;
		this.dogVolunteerRepository = dogVolunteerRepository;
	}

	/**
	 * Метод реализуется в обработчике {@link com.example.teamwork.handlers.messageHandlers.FeedbackHandler} для
	 * занесения запроса на обратную связь с клиентом в нашу БД. В процессе выполнения, назначается ответственный волонтёр приюта и время поступления запроса,
	 * который будет обрабатывать постувший запрос. Волонтёр назначается случайным образом из списка доступных волонтёров (выборка волонтёров из БД).
	 * {@link JpaRepository#findAll()}
	 * {@link JpaRepository#getReferenceById(Object)}
	 * {@link JpaRepository#save(Object)}
	 *
	 * @param feedback класс форма от клиента с дополнительными полями (вносимыми программой) для дальнейшей обработки.
	 */
	public void addClientRequest(Feedback feedback) {
		List<Long> list = dogVolunteerRepository.getAllId();
		feedback.setDogVolunteer(dogVolunteerRepository.getReferenceById(list.get(RandomUtils.nextInt(0, list.size()))));
		feedbackRepository.save(feedback);
	}

	/**
	 * Метод поиска открытых, необработанных запросов на обратную связь с клиентом для дальнейшей отработки по ним.
	 * {@link JpaRepository#findAll()}
	 *
	 * @return список необработанных запросов на обратную связь с клиентом.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 */
	public List<FeedbackDTO> findOpenRequests() {
		List<FeedbackDTO> listDTO = new ArrayList<>();
		for (Feedback feedback : feedbackRepository.findAll()) {
			listDTO.add(Feedback.convertToFeedbackDTO(feedback));
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
		feedbackRepository.deleteById(id);
	}
}
