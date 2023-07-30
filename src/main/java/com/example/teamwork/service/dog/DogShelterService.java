package com.example.teamwork.service.dog;

import com.example.teamwork.DTO.dog.DogDTO;
import com.example.teamwork.enums.Disability;
import com.example.teamwork.model.Dog;
import com.example.teamwork.repository.dog.DogShelterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogShelterService {
	private final DogShelterRepository dogShelterRepository;

	public DogShelterService(DogShelterRepository dogShelterRepository) {
		this.dogShelterRepository = dogShelterRepository;
	}

	/**
	 * Метод используется сотрудниками приюта для занесения данных о питомце в БД;
	 * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
	 *
	 * @param name       - имя питомца;
	 * @param age        - возраст питомца;
	 * @param disability - отклонения/инвалидность;
	 * @param comments   - комментарии по питомцу;
	 */
	public void addPetToShelter(String name, Integer age, String disability, String comments) {
		Dog dog = new Dog();
		dog.setName(name);
		dog.setAge(age);
		for (Disability dis : Disability.values()) {
			if (dis.getDisability().equalsIgnoreCase(disability)) {
				dog.setDisability(dis);
				break;
			}
			dog.setDisability(Disability.UNDEFINED);
		}
		dog.setComments(comments);
		dogShelterRepository.save(dog);
	}

	/**
	 * Метод поиска объекта по идентификационному номеру в нашей БД.
	 * {@link org.springframework.data.jpa.repository.JpaRepository#getReferenceById(Object)}
	 *
	 * @param id идентификационный номер объекта для поиска.
	 * @return искомый объект.
	 * @throws javax.persistence.EntityNotFoundException при отсутствии объекта в БД.
	 */
	public Optional<Dog> findById(Long id) {
		return dogShelterRepository.findById(id);
	}

	public List<DogDTO> findAll() {
		List<DogDTO> listDTO = new ArrayList<>();
		for (Dog dog : dogShelterRepository.findAll()) {
			listDTO.add(Dog.convert(dog));
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
	public void deleteById(Long id) {
		dogShelterRepository.deleteById(id);
	}
}
