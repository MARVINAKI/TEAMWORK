package com.example.teamwork.service;

import com.example.teamwork.model.Dog;
import com.example.teamwork.repository.DogShelterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogShelterService {
	private final DogShelterRepository dogShelterRepository;

	public DogShelterService(DogShelterRepository dogShelterRepository) {
		this.dogShelterRepository = dogShelterRepository;
	}

	public void save(Dog dog) {
		dogShelterRepository.save(dog);
	}
//
//	public Dog findDogById(Long id) {
//		return dogShelterRepository.getById(id);
//	}
//
//	public Dog findDogByName(String name) {
//		return dogShelterRepository.findByName(name);
//	}
//
//	public List<Dog> findAllDog() {
//		return dogShelterRepository.findAll();
//	}
//
//	public void deleteById(Long id) {
//		dogShelterRepository.deleteById(id);
//	}
//
//	public void deleteByName(String name) {
//		dogShelterRepository.deleteByName(name);
//	}
}
