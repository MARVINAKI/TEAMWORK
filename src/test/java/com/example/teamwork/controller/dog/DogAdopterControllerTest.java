package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogAdopterDTO;
import com.example.teamwork.model.DogAdopter;
import com.example.teamwork.service.dog.DogAdopterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogAdopterControllerTest {

	private static final Long ID_1 = 1L;
	private static final Long ID_2 = 2L;
	private static final Long ID_3 = 3L;
	private static final Long CHAT_ID_1 = 11L;
	private static final Long CHAT_ID_2 = 22L;
	private static final Long CHAT_ID_3 = 33L;
	private static final String FULL_NAME_1 = "name1";
	private static final String FULL_NAME_2 = "name2";
	private static final String FULL_NAME_3 = "name3";
	private static final String PHONE_NUMBER_1 = "89171111111";
	private static final String PHONE_NUMBER_2 = "89172222222";
	private static final String PHONE_NUMBER_3 = "89173333333";

	@InjectMocks
	private DogAdopterController controller;

	@Mock
	private DogAdopterService service;

	@Test
	void addDogAdopter() {
		DogAdopter dogAdopter = new DogAdopter(CHAT_ID_1, FULL_NAME_1, PHONE_NUMBER_1);
		dogAdopter.setId(ID_1);

		controller.addDogAdopter(CHAT_ID_1, FULL_NAME_1, PHONE_NUMBER_1);

		verify(service, only()).addAdopter(dogAdopter);

		assertTrue(controller.addDogAdopter(CHAT_ID_1, FULL_NAME_1, PHONE_NUMBER_1).getStatusCode().is2xxSuccessful());
	}

	@Test
	void findAllDogAdopters() {
		DogAdopterDTO dogAdopterDTO1 = new DogAdopterDTO(ID_1, CHAT_ID_1, FULL_NAME_1, PHONE_NUMBER_1);
		DogAdopterDTO dogAdopterDTO2 = new DogAdopterDTO(ID_2, CHAT_ID_2, FULL_NAME_2, PHONE_NUMBER_2);
		DogAdopterDTO dogAdopterDTO3 = new DogAdopterDTO(ID_3, CHAT_ID_3, FULL_NAME_3, PHONE_NUMBER_3);
		List<DogAdopterDTO> listDTO = Arrays.asList(dogAdopterDTO1, dogAdopterDTO2, dogAdopterDTO3);

		when(service.findAllAdopters()).thenReturn(listDTO);

		ResponseEntity<List<DogAdopterDTO>> listResponseEntity = controller.findAllDogAdopters();

		verify(service, only()).findAllAdopters();

		assertFalse(controller.findAllDogAdopters().getStatusCode().is4xxClientError());
		assertTrue(controller.findAllDogAdopters().getStatusCode().is2xxSuccessful());
		assertFalse(service.findAllAdopters().isEmpty());

		assertSame(Objects.requireNonNull(listResponseEntity.getBody()).get(1), listDTO.get(1));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> Objects.requireNonNull(controller.findAllDogAdopters().getBody()).get(listResponseEntity.getBody().size()));
	}

	@Test
	void findDogAdopterByName() {

		DogAdopter dogAdopter = new DogAdopter(CHAT_ID_2, FULL_NAME_2, PHONE_NUMBER_2);
		dogAdopter.setId(ID_2);

		when(service.findByName(FULL_NAME_2)).thenReturn(Optional.of(dogAdopter));

		ResponseEntity<DogAdopterDTO> responseEntity = controller.findDogAdopterByName(FULL_NAME_2);

		verify(service, only()).findByName(FULL_NAME_2);

		assertTrue(controller.findDogAdopterByName(FULL_NAME_2).getStatusCode().is2xxSuccessful());
		assertTrue(service.findByName(FULL_NAME_2).isPresent());

		assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), dogAdopter.getId());
	}

	@Test
	void findById() {
		DogAdopter dogAdopter = new DogAdopter(CHAT_ID_2, FULL_NAME_2, PHONE_NUMBER_2);
		dogAdopter.setId(ID_2);

		when(service.findById(ID_2)).thenReturn(Optional.of(dogAdopter));

		ResponseEntity<DogAdopterDTO> responseEntity = controller.findById(ID_2);

		verify(service, only()).findById(ID_2);

		assertTrue(service.findById(ID_2).isPresent());
		assertTrue(controller.findById(ID_2).getStatusCode().is2xxSuccessful());

		assertEquals(DogAdopter.convert(dogAdopter), responseEntity.getBody());
	}

	@Test
	void noFoundDogAdopterByID() {
		when(service.findById(ID_1)).thenReturn(Optional.empty());

		ResponseEntity<DogAdopterDTO> responseEntity = controller.findById(ID_1);

		verify(service, only()).findById(ID_1);

		assertTrue(service.findById(ID_1).isEmpty());
		assertTrue(responseEntity.getStatusCode().is4xxClientError());
	}

	@Test
	void deleteDogAdopter() {
		controller.deleteDogAdopter(ID_2);
		verify(service, only()).deleteAdopter(ID_2);
		verify(service, never()).deleteAdopter(ID_3);
		verify(service, never()).deleteAdopter(ID_1);
	}
}