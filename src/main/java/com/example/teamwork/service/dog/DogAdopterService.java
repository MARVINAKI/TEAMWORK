package com.example.teamwork.service.dog;

import com.example.teamwork.DTO.dog.DogAdopterDTO;
import com.example.teamwork.model.DogAdopter;
import com.example.teamwork.repository.dog.DogAdopterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogAdopterService {

	private final DogAdopterRepository dogAdopterRepository;

	public DogAdopterService(DogAdopterRepository dogAdopterRepository) {
		this.dogAdopterRepository = dogAdopterRepository;
	}

	public void addAdopter(DogAdopter dogAdopter) {
		dogAdopterRepository.save(dogAdopter);
	}

	public Optional<DogAdopter> findByName(String name) {
		return dogAdopterRepository.findDogAdopterByFullName(name);
	}

	public Optional<DogAdopter> findById(Long id) {
		return dogAdopterRepository.findById(id);
	}

	public List<DogAdopterDTO> findAllAdopters() {
		List<DogAdopterDTO> listDTO = new ArrayList<>();
		for (DogAdopter dogAdopter : dogAdopterRepository.findAll()) {
			listDTO.add(DogAdopter.convert(dogAdopter));
		}
		return listDTO;
	}

	public void deleteAdopter(Long id) {
		dogAdopterRepository.deleteById(id);
	}
}
