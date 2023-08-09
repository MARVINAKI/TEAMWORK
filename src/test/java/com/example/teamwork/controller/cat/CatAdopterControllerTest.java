package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatAdopterDTO;
import com.example.teamwork.model.CatAdopter;
import com.example.teamwork.service.cat.CatAdopterService;
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
class CatAdopterControllerTest {

	@InjectMocks
	private CatAdopterController controller;

	@Mock
	private CatAdopterService service;

	@Test
	void addCatAdopter() {
		CatAdopter catAdopter = new CatAdopter(CHAT_ID_1, FULL_NAME_1, PHONE_NUMBER_1);
		catAdopter.setId(ID_1);

		ResponseEntity<Void> responseEntity = controller.addCatAdopter(CHAT_ID_1, FULL_NAME_1, PHONE_NUMBER_1);

		verify(service, only()).addAdopter(catAdopter);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
	}

	@Test
	void findAllCatAdopters() {
		CatAdopterDTO catAdopterDTO1 = new CatAdopterDTO(ID_1, CHAT_ID_1, FULL_NAME_1, PHONE_NUMBER_1);
		CatAdopterDTO catAdopterDTO2 = new CatAdopterDTO(ID_2, CHAT_ID_2, FULL_NAME_2, PHONE_NUMBER_2);
		CatAdopterDTO catAdopterDTO3 = new CatAdopterDTO(ID_3, CHAT_ID_3, FULL_NAME_3, PHONE_NUMBER_3);
		List<CatAdopterDTO> listDTO = Arrays.asList(catAdopterDTO1, catAdopterDTO2, catAdopterDTO3);

		when(service.findAllAdopters()).thenReturn(listDTO);

		ResponseEntity<List<CatAdopterDTO>> listResponseEntity = controller.findAllCatAdopters();

		verify(service, only()).findAllAdopters();

		assertFalse(controller.findAllCatAdopters().getStatusCode().is4xxClientError());
		assertTrue(controller.findAllCatAdopters().getStatusCode().is2xxSuccessful());
		assertFalse(service.findAllAdopters().isEmpty());

		assertSame(Objects.requireNonNull(listResponseEntity.getBody()).get(1), listDTO.get(1));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> Objects.requireNonNull(controller.findAllCatAdopters().getBody()).get(listResponseEntity.getBody().size()));
	}

	@Test
	void findCatAdopterByName() {
		CatAdopter catAdopter = new CatAdopter(CHAT_ID_2, FULL_NAME_2, PHONE_NUMBER_2);
		catAdopter.setId(ID_2);

		when(service.findByFullName(FULL_NAME_2)).thenReturn(Optional.of(catAdopter));

		ResponseEntity<CatAdopterDTO> responseEntity = controller.findCatAdopterByName(FULL_NAME_2);

		verify(service, only()).findByFullName(FULL_NAME_2);

		assertTrue(controller.findCatAdopterByName(FULL_NAME_2).getStatusCode().is2xxSuccessful());
		assertTrue(service.findByFullName(FULL_NAME_2).isPresent());

		assertEquals(Objects.requireNonNull(responseEntity.getBody()).getId(), catAdopter.getId());
	}

	@Test
	void findById() {
		CatAdopter catAdopter = new CatAdopter(CHAT_ID_2, FULL_NAME_2, PHONE_NUMBER_2);
		catAdopter.setId(ID_2);

		when(service.findById(ID_2)).thenReturn(Optional.of(catAdopter));

		ResponseEntity<CatAdopterDTO> responseEntity = controller.findById(ID_2);

		verify(service, only()).findById(ID_2);

		assertTrue(service.findById(ID_2).isPresent());
		assertTrue(controller.findById(ID_2).getStatusCode().is2xxSuccessful());

		assertEquals(CatAdopter.convert(catAdopter), responseEntity.getBody());
	}

	@Test
	void noFoundDogAdopterByID() {
		when(service.findById(ID_1)).thenReturn(Optional.empty());

		ResponseEntity<CatAdopterDTO> responseEntity = controller.findById(ID_1);

		verify(service, only()).findById(ID_1);

		assertTrue(service.findById(ID_1).isEmpty());
		assertTrue(responseEntity.getStatusCode().is4xxClientError());
	}

	@Test
	void deleteCatAdopter() {
		controller.deleteCatAdopter(ID_2);
		verify(service, only()).deleteAdopter(ID_2);
		verify(service, never()).deleteAdopter(ID_3);
		verify(service, never()).deleteAdopter(ID_1);
	}
}