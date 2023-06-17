package com.example.teamwork.service;

import com.example.teamwork.DTO.CynologistDTO;
import com.example.teamwork.model.Cynologist;
import com.example.teamwork.repository.CynologistsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CynologistService {
	private final CynologistsRepository cynologistsRepository;

	public CynologistService(CynologistsRepository cynologistsRepository) {
		this.cynologistsRepository = cynologistsRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о кинологе в БД (в процессе происходит преобразование объектов).
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 * @param cynologistDTO класс DTO, который преобразуется в основной класс <i><b>Cynologist</b></i>.
	 */
	public void save(CynologistDTO cynologistDTO) {
		cynologistsRepository.save(CynologistDTO.convertToCynologist(cynologistDTO));
	}

	/**
	 * Метод поиска всех кинологов в нашей БД (в процессе происходит преобразование объектов).
	 * {@link JpaRepository#findAll()}
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 * @return искомый список объектов.
	 */
	public List<CynologistDTO> getAll() {
		List<CynologistDTO> cynologists = new ArrayList<>();
		for (Cynologist cynologist : cynologistsRepository.findAll()) {
			cynologists.add(Cynologist.convertToCynologistDTO(cynologist));
		}
		return cynologists;
	}

	/**
	 * Метод поиска объекта по идентификационному номеру в нашей БД (в процессе происходит преобразование объектов).
	 * {@link JpaRepository#getReferenceById(Object)}
	 * @param id идентификационный номер объекта для поиска.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 * @return искомый объект.
	 */
	public CynologistDTO getById(Long id) {
		return Cynologist.convertToCynologistDTO(cynologistsRepository.getReferenceById(id));
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
