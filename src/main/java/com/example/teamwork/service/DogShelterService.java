package com.example.teamwork.service;

import com.example.teamwork.DTO.DogDTO;
import com.example.teamwork.model.Dog;
import com.example.teamwork.repository.DogShelterRepository;
import org.springframework.stereotype.Service;

@Service
public class DogShelterService {
	private final DogShelterRepository dogShelterRepository;

	public DogShelterService(DogShelterRepository dogShelterRepository) {
		this.dogShelterRepository = dogShelterRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о питомце в БД, в процессе происходит преобразование объектов.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 *
	 * @param dogDTO класс DTO, который преобразуется в основной класс <i><b>Dog</b></i>
	 */
	public void save(DogDTO dogDTO) {
		dogShelterRepository.save(DogDTO.convertToDog(dogDTO));
	}

	/**
	 * Метод поиска объекта по идентификационному номеру в нашей БД (в процессе происходит преобразование объектов).
	 * {@link org.springframework.data.jpa.repository.JpaRepository#getReferenceById(Object)}
	 *
	 * @param id идентификационный номер объекта для поиска.
	 * @return искомый объект.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public DogDTO getById(Long id) {
		return Dog.convertToDogDTO(dogShelterRepository.getReferenceById(id));
	}

	/**
	 * Метод удаления объекта по идентификационному номеру из нашей БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
	 *
	 * @param id идентификационный номер объекта для удаления.
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 */
	public void deleteById(Long id) {
		dogShelterRepository.deleteById(id);
	}

}
