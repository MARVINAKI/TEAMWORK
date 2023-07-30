package com.example.teamwork.service.dog;

import com.example.teamwork.model.Cynologist;
import com.example.teamwork.repository.dog.CynologistsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CynologistService {
	private final CynologistsRepository cynologistsRepository;

	public CynologistService(CynologistsRepository cynologistsRepository) {
		this.cynologistsRepository = cynologistsRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о кинологе в БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 *
	 * @param fullName - имя кинолога;
	 * @param experience - опыт работы;
	 * @param phoneNumber - телефонный номер;
	 * @param eMail - адрес электронной почты;
	 * @param comments - комментарии;
	 */
	public void addCynologist(String fullName, Integer experience, String phoneNumber, String eMail, String comments) {
		Cynologist cynologist = new Cynologist();
		cynologist.setFullName(fullName);
		cynologist.setExperience(experience);
		cynologist.setPhoneNumber(phoneNumber);
		cynologist.setEMail(eMail);
		cynologist.setComments(comments);
		cynologistsRepository.save(cynologist);
	}

	/**
	 * Метод поиска всех кинологов в нашей БД.
	 * {@link JpaRepository#findAll()}
	 *
	 * @return искомый список объектов.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 */
	public List<Cynologist> findAll() {
		return cynologistsRepository.findAll();
	}

	/**
	 * Метод поиска объекта по идентификационному номеру в нашей БД.
	 * {@link JpaRepository#getReferenceById(Object)}
	 *
	 * @param id идентификационный номер объекта для поиска.
	 * @return искомый объект.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public Optional<Cynologist> findById(Long id) {
		return cynologistsRepository.findById(id);
	}

	/**
	 * Метод удаления объекта по идентификационному номеру из нашей БД.
	 * {@link JpaRepository#deleteById(Object)}
	 *
	 * @param id идентификационный номер объекта для удаления.
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 */
	public void deleteById(Long id) {
		cynologistsRepository.deleteById(id);
	}
}
