package com.example.teamwork.service;

import com.example.teamwork.DTO.CatDTO;
import com.example.teamwork.model.Cat;
import com.example.teamwork.repository.CatShelterRepository;
import org.springframework.stereotype.Service;

@Service
public class CatShelterService {
	private final CatShelterRepository catShelterRepository;

	public CatShelterService(CatShelterRepository catShelterRepository) {
		this.catShelterRepository = catShelterRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о питомце в БД (в процессе происходит преобразование объектов).
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 * @param catDTO класс DTO, который преобразуется в основной класс <i><b>Cat</b></i>.
	 */
	public void save(CatDTO catDTO) {
		catShelterRepository.save(CatDTO.convertToCat(catDTO));
	}

	/**
	 * Метод поиска объекта по идентификационному номеру в нашей БД (в процессе происходит преобразование объектов).
	 * {@link org.springframework.data.jpa.repository.JpaRepository#getReferenceById(Object)}
	 * @param id идентификационный номер объекта для поиска.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 * @return искомый объект.
	 */
	public CatDTO getById(Long id) {
		return Cat.convertToCatDTO(catShelterRepository.getReferenceById(id));
	}

	/**
	 * Метод удаления объекта по идентификационному номеру из нашей БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 * @param id идентификационный номер объекта для удаления.
	 */
	public void deleteById(Long id) {
		catShelterRepository.deleteById(id);
	}
}
