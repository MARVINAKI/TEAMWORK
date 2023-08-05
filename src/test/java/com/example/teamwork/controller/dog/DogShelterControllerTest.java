package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogDTO;
import com.example.teamwork.constant.Disability;
import com.example.teamwork.model.Dog;
import com.example.teamwork.service.dog.DogShelterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.example.teamwork.constant.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogShelterControllerTest {

	@InjectMocks
	private DogShelterController controller;

	@Mock
	private DogShelterService service;

	@Test
	void addPetToDogShelterTest() {
		controller.addPetToDogShelter(NAME_1, AGE_1, String.valueOf(DISABILITY_1), COMMENTS_1);
		verify(service, only()).addPetToShelter(NAME_1, AGE_1, String.valueOf(DISABILITY_1), COMMENTS_1);
	}

	@Test
	void findDogByIdTest() {
		Dog dog = new Dog();
		dog.setId(ID_1);
		dog.setName(NAME_1);
		dog.setAge(AGE_1);
		dog.setDisability(DISABILITY_1);
		dog.setComments(COMMENTS_1);

		when(service.findById(ID_1)).thenReturn(Optional.of(dog));

		ResponseEntity<DogDTO> responseEntity = controller.findDogById(ID_1);

		verify(service, only()).findById(ID_1);

		assertTrue(service.findById(ID_1).isPresent());
		assertTrue(controller.findDogById(ID_1).getStatusCode().is2xxSuccessful());

		assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), dog.getId());
		assertEquals(responseEntity.getBody().getDisability(), Disability.MOVEMENT);
	}

	@Test
	void notFoundDogByIdTest() {
		when(service.findById(ID_3)).thenReturn(Optional.empty());

		ResponseEntity<DogDTO> responseEntity = controller.findDogById(ID_3);

		verify(service, only()).findById(ID_3);

		assertTrue(service.findById(ID_3).isEmpty());
		assertFalse(responseEntity.getStatusCode().is2xxSuccessful());
		assertTrue(responseEntity.getStatusCode().is4xxClientError());
	}

	@Test
	void findAllDogsInTheShelterTest() {
		DogDTO dog1 = new DogDTO(ID_1, NAME_1, AGE_1, DISABILITY_1, COMMENTS_1);
		DogDTO dog2 = new DogDTO(ID_2, NAME_2, AGE_2, DISABILITY_2, COMMENTS_2);
		DogDTO dog3 = new DogDTO(ID_3, NAME_3, AGE_3, DISABILITY_3, COMMENTS_3);

		List<DogDTO> listDTO = Arrays.asList(dog1, dog2, dog3);

		when(service.findAll()).thenReturn(listDTO);

		ResponseEntity<List<DogDTO>> listResponseEntity = controller.findAllDogsInTheShelter();

		verify(service, only()).findAll();

		assertFalse(service.findAll().isEmpty());
		assertTrue(listResponseEntity.getStatusCode().is2xxSuccessful());

		assertSame(listDTO.get(1), Objects.requireNonNull(listResponseEntity.getBody()).get(1));

		assertEquals(3, listResponseEntity.getBody().size());
	}

	@Test
	void findEmptyListOfDogTest() {
		List<DogDTO> listDTO = new ArrayList<>();

		when(service.findAll()).thenReturn(listDTO);

		ResponseEntity<List<DogDTO>> listResponseEntity = controller.findAllDogsInTheShelter();

		verify(service, only()).findAll();

		assertTrue(controller.findAllDogsInTheShelter().getStatusCode().is2xxSuccessful());
		assertTrue(Objects.requireNonNull(controller.findAllDogsInTheShelter().getBody()).isEmpty());
		assertTrue(service.findAll().isEmpty());
	}

	@Test
	void deleteDogByIdTest() {
		controller.deleteDogById(ID_2);
		verify(service, only()).deleteById(ID_2);
		verify(service, never()).deleteById(ID_1);
		verify(service, never()).deleteById(ID_3);
	}
}