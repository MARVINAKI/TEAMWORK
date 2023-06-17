package com.example.teamwork.service;

import com.example.teamwork.DTO.DogVolunteerDTO;
import com.example.teamwork.model.DogVolunteer;
import com.example.teamwork.repository.DogVolunteerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DogVolunteerService {

	private final DogVolunteerRepository dogVolunteerRepository;

	public DogVolunteerService(DogVolunteerRepository dogVolunteerRepository) {
		this.dogVolunteerRepository = dogVolunteerRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о волонтёре собачего приюта в БД (в процессе происходит преобразование объектов)
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 * @param dogVolunteerDTO класс DTO, который преобразуется в основной класс <i><b>DogVolunteer</b></i>
	 */
	public void addDogVolunteer(DogVolunteerDTO dogVolunteerDTO) {
		dogVolunteerRepository.save(DogVolunteerDTO.convertToDogVolunteer(dogVolunteerDTO));
	}

	/**
	 * Метод поиска волонтёра собачего приюта по идентификационному номеру в нашей БД (в процессе происходит преобразование объектов).
	 * {@link JpaRepository#getReferenceById(Object)}
	 * @param id идентификационный номер объекта для поиска.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 * @return искомый объект.
	 */
	public DogVolunteerDTO findDogVolunteer(Long id) {
		return DogVolunteer.convertToDogVolunteerDTO(dogVolunteerRepository.getReferenceById(id));
	}

	/**
	 * Метод поиска всех волонтёров собачего приюта в нашей БД (в процессе происходит преобразование объектов).
	 * {@link JpaRepository#findAll()}
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 * @return искомый список объектов.
	 */
	public List<DogVolunteerDTO> getAllDogsVolunteer() {
		List<DogVolunteerDTO> dogsVolunteer = new ArrayList<>();
		for (DogVolunteer dogVolunteer : dogVolunteerRepository.findAll()) {
			dogsVolunteer.add(DogVolunteer.convertToDogVolunteerDTO(dogVolunteer));
		}
		return dogsVolunteer;
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
