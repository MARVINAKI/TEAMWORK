package com.example.teamwork.service.cat;

import com.example.teamwork.DTO.cat.CatVolunteerDTO;
import com.example.teamwork.model.CatVolunteer;
import com.example.teamwork.repository.cat.CatVolunteerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatVolunteerService {

	private final CatVolunteerRepository catVolunteerRepository;

	public CatVolunteerService(CatVolunteerRepository catVolunteerRepository) {
		this.catVolunteerRepository = catVolunteerRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о волонтёре кошачьего приюта в БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 * @param fullName - имя волонтёра;
	 */
	public void addCatVolunteer(String fullName) {
		catVolunteerRepository.save(new CatVolunteer(fullName));
	}

	/**
	 * Метод поиска волонтёра кошачьего приюта по идентификационному номеру в нашей БД.
	 * {@link JpaRepository#getReferenceById(Object)}
	 *
	 * @param id идентификационный номер объекта для поиска.
	 * @return искомый объект.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public Optional<CatVolunteer> findById(Long id) {
		return catVolunteerRepository.findById(id);
	}

	/**
	 * Метод поиска всех волонтёров кошачьего приюта в нашей БД (в процессе происходит преобразование объектов).
	 * {@link JpaRepository#findAll()}
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объектов в БД.
	 * @return искомый список объектов.
	 */
	public List<CatVolunteerDTO> findAll() {
		List<CatVolunteerDTO> listDTO = new ArrayList<>();
		for (CatVolunteer catVolunteer : catVolunteerRepository.findAll()) {
			listDTO.add(CatVolunteer.convert(catVolunteer));
		}
		return listDTO;
	}

	/**
	 * Метод удаления объекта по идентификационному номеру из нашей БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
	 *
	 * @param id идентификационный номер объекта для удаления.
	 * @throws org.springframework.dao.EmptyResultDataAccessException при отсутсвии объекта удаления в БД.
	 */
	public void deleteCatVolunteer(Long id) {
		catVolunteerRepository.deleteById(id);
	}
}
