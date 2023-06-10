package com.example.teamwork.controller;

import com.example.teamwork.model.Dog;
import com.example.teamwork.service.DogShelterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/DogShelter")
public class DogShelterController {
	private final DogShelterService dogShelterService;

	public DogShelterController(DogShelterService dogShelterService) {
		this.dogShelterService = dogShelterService;
	}

	@PostMapping
	public ResponseEntity<Dog> addPetToDogShelter(Dog dog) {
		dogShelterService.save(dog);
		return ResponseEntity.ok().body(dog);
	}
}
