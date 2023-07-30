package com.example.teamwork.service.dog;

import com.example.teamwork.DTO.dog.DogVolunteerDTO;
import com.example.teamwork.model.DogVolunteer;
import com.example.teamwork.repository.dog.DogVolunteerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogVolunteerService {

	private final DogVolunteerRepository dogVolunteerRepository;

	public DogVolunteerService(DogVolunteerRepository dogVolunteerRepository) {
		this.dogVolunteerRepository = dogVolunteerRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о волонтёре собачего приюта в БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 * @param fullName - имя волонтёра;
	 */
	public void addDogVolunteer(String fullName) {
		dogVolunteerRepository.save(new DogVolunteer(fullName));
	}

	/**
	 * Метод поиска волонтёра собачего приюта по идентификационному номеру в нашей БД.
	 * {@link JpaRepository#getReferenceById(Object)}
	 * @param id идентификационный номер объекта для поиска.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 * @return искомый объект.
	 */
	public Optional<DogVolunteer> findById(Long id) {
		return dogVolunteerRepository.findById(id);
	}

	/**
	 * Метод поиска всех волонтёров собачего приюта в нашей БД.
	 * {@link JpaRepository#findAll()}
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 * @return искомый список объектов.
	 */
	public List<DogVolunteerDTO> findAll() {
		List<DogVolunteerDTO> listDTO = new ArrayList<>();
		for (DogVolunteer dogVolunteer : dogVolunteerRepository.findAll()) {
			listDTO.add(DogVolunteer.convert(dogVolunteer));
		}
		return listDTO;
	}

	public List<Long> findAllIds() {
		return dogVolunteerRepository.getAllId();
	}

	/**
	 * Метод удаления объекта по идентификационному номеру из нашей БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 * @param id идентификационный номер объекта для удаления.
	 */
	public void deleteDogVolunteer(Long id) {
		dogVolunteerRepository.deleteById(id);
	}
}
