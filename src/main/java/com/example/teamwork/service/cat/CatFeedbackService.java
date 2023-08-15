package com.example.teamwork.service.cat;

import com.example.teamwork.DTO.cat.CatFeedbackDTO;
import com.example.teamwork.handlers.buttonHandlers.FeedbackHandler;
import com.example.teamwork.model.CatFeedback;
import com.example.teamwork.model.CatVolunteer;
import com.example.teamwork.repository.cat.CatFeedbackRepository;
import com.example.teamwork.repository.cat.CatVolunteerRepository;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatFeedbackService {

	private final CatFeedbackRepository catFeedbackRepository;
	private final CatVolunteerRepository catVolunteerRepository;

	public CatFeedbackService(CatFeedbackRepository catFeedbackRepository, CatVolunteerRepository catVolunteerRepository) {
		this.catFeedbackRepository = catFeedbackRepository;
		this.catVolunteerRepository = catVolunteerRepository;
	}

	/**
	 * Метод реализуется в обработчике {@link FeedbackHandler} для
	 * занесения запроса на обратную связь с клиентом в нашу БД. В процессе выполнения, назначается ответственный волонтёр приюта и время поступления запроса,
	 * который будет обрабатывать постувший запрос. Волонтёр назначается случайным образом из списка доступных волонтёров (выборка волонтёров из БД).
	 * {@link JpaRepository#findAll()}
	 * {@link JpaRepository#getReferenceById(Object)}
	 * {@link JpaRepository#save(Object)}
	 *
	 * @param catFeedback класс форма от клиента с дополнительными полями (вносимыми программой) для дальнейшей обработки.
	 */
	public void addClientRequest(CatFeedback catFeedback) {
		List<Long> list = catVolunteerRepository.getAllId();
		if (list.size() != 0) {
			Optional<CatVolunteer> catVolunteer = catVolunteerRepository.findById(list.get(RandomUtils.nextInt(0, list.size())));
			catFeedback.setCatVolunteer(catVolunteer.isEmpty() ? null : catVolunteer.get());
			catFeedbackRepository.save(catFeedback);
		}
	}

	/**
	 * Метод поиска открытых, необработанных запросов на обратную связь с клиентом для дальнейшей отработки по ним.
	 * {@link JpaRepository#findAll()}
	 *
	 * @return список необработанных запросов на обратную связь с клиентом.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 */
	public List<CatFeedbackDTO> findOpenRequests() {
		List<CatFeedbackDTO> listDTO = new ArrayList<>();
		for (CatFeedback catFeedback : catFeedbackRepository.findAll()) {
			listDTO.add(CatFeedback.convert(catFeedback));
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
		catFeedbackRepository.deleteById(id);
	}
}