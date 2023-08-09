package com.example.teamwork.service.cat;

import com.example.teamwork.DTO.cat.CatDTO;
import com.example.teamwork.constant.Disability;
import com.example.teamwork.model.Cat;
import com.example.teamwork.repository.cat.CatShelterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CatShelterService {
	private final CatShelterRepository catShelterRepository;

	public CatShelterService(CatShelterRepository catShelterRepository) {
		this.catShelterRepository = catShelterRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о питомце в БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 *
	 * @param name - имя питомца;
	 * @param age - возраст питомца;
	 * @param disability - отклонения/инвалидность;
	 * @param comments - комментарии по питомцу;
	 *
	 */
	public void addPetToShelter(String name, Integer age, String disability, String comments) {
		Cat cat = new Cat();
		cat.setName(name);
		cat.setAge(age);
		for (Disability dis : Disability.values()) {
			if (dis.getDisability().equalsIgnoreCase(disability)) {
				cat.setDisability(dis);
				break;
			}
			cat.setDisability(Disability.UNDEFINED);
		}
		cat.setComments(comments);
		catShelterRepository.save(cat);
	}

	/**
	 * Метод поиска объекта по идентификационному номеру в нашей БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#getReferenceById(Object)}
	 * @param id идентификационный номер объекта для поиска.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 * @return искомый объект.
	 */
	public Optional<Cat> findById(Long id) {
		return catShelterRepository.findById(id);
	}

	public List<CatDTO> findAll() {
		List<CatDTO> listDTO = new ArrayList<>();
		for (Cat cat : catShelterRepository.findAll()) {
			listDTO.add(Cat.convert(cat));
		}
		return listDTO;
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
