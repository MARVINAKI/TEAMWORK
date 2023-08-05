package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatDTO;
import com.example.teamwork.constant.Disability;
import com.example.teamwork.model.Cat;
import com.example.teamwork.service.cat.CatShelterService;
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

import static com.example.teamwork.constant.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatShelterControllerTest {

	@InjectMocks
	private CatShelterController controller;

	@Mock
	private CatShelterService service;

	@Test
	void addPetToCatShelter() {
		controller.addPetToCatShelter(NAME_1, AGE_1, String.valueOf(DISABILITY_1), COMMENTS_1);
		verify(service, only()).addPetToShelter(NAME_1, AGE_1, String.valueOf(DISABILITY_1), COMMENTS_1);
	}

	@Test
	void findCatById() {
		Cat cat = new Cat(NAME_1, AGE_1, DISABILITY_1, COMMENTS_1);
		cat.setId(ID_1);

		when(service.findById(ID_1)).thenReturn(Optional.of(cat));

		ResponseEntity<CatDTO> responseEntity = controller.findCatById(ID_1);

		verify(service, only()).findById(ID_1);

		assertTrue(service.findById(ID_1).isPresent());
		assertTrue(controller.findCatById(ID_1).getStatusCode().is2xxSuccessful());

		assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), cat.getId());
		assertEquals(responseEntity.getBody().getDisability(), Disability.MOVEMENT);
	}

	@Test
	void notFoundCatByIdTest() {
		when(service.findById(ID_3)).thenReturn(Optional.empty());

		ResponseEntity<CatDTO> responseEntity = controller.findCatById(ID_3);

		verify(service, only()).findById(ID_3);

		assertTrue(service.findById(ID_3).isEmpty());
		assertFalse(responseEntity.getStatusCode().is2xxSuccessful());
		assertTrue(responseEntity.getStatusCode().is4xxClientError());
	}

	@Test
	void findAllCatsInTheShelter() {
		CatDTO cat1 = new CatDTO(ID_1, NAME_1, AGE_1, DISABILITY_1, COMMENTS_1);
		CatDTO cat2 = new CatDTO(ID_2, NAME_2, AGE_2, DISABILITY_2, COMMENTS_2);
		CatDTO cat3 = new CatDTO(ID_3, NAME_3, AGE_3, DISABILITY_3, COMMENTS_3);

		List<CatDTO> listDTO = Arrays.asList(cat1, cat2, cat3);

		when(service.findAll()).thenReturn(listDTO);

		ResponseEntity<List<CatDTO>> listResponseEntity = controller.findAllCatsInTheShelter();

		verify(service, only()).findAll();

		assertFalse(service.findAll().isEmpty());
		assertTrue(listResponseEntity.getStatusCode().is2xxSuccessful());

		assertSame(listDTO.get(1), Objects.requireNonNull(listResponseEntity.getBody()).get(1));

		assertEquals(3, listResponseEntity.getBody().size());
	}

	@Test
	void deleteCatById() {
		controller.deleteCatById(ID_2);
		verify(service, only()).deleteById(ID_2);
		verify(service, never()).deleteById(ID_1);
		verify(service, never()).deleteById(ID_3);
	}
}