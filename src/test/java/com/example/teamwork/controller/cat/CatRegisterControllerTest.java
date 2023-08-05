package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatRegisterDTO;
import com.example.teamwork.model.Cat;
import com.example.teamwork.model.CatAdopter;
import com.example.teamwork.model.CatRegister;
import com.example.teamwork.service.cat.CatAdopterService;
import com.example.teamwork.service.cat.CatRegisterService;
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
class CatRegisterControllerTest {

	@InjectMocks
	private CatRegisterController controller;

	@Mock
	private CatRegisterService service;

	@Mock
	private CatShelterService catShelterService;

	@Mock
	private CatAdopterService catAdopterService;

	@Mock
	private Cat cat;

	@Mock
	private CatAdopter catAdopter;

	@Test
	void findAllRegisters() {
		CatRegisterDTO catRegisterDTO1 = new CatRegisterDTO(ID_1, ADOPTERS_CHAT_ID_1, DOG_ID_1, ADOPTER_ID_1, TRIAL_PERIOD_1, REGISTRATION_DATE_1, LAST_DATE_OF_REPORTS_1);
		CatRegisterDTO catRegisterDTO2 = new CatRegisterDTO(ID_2, ADOPTERS_CHAT_ID_2, DOG_ID_2, ADOPTER_ID_2, TRIAL_PERIOD_2, REGISTRATION_DATE_2, LAST_DATE_OF_REPORTS_2);
		List<CatRegisterDTO> listDTO = Arrays.asList(catRegisterDTO1, catRegisterDTO2);

		when(service.findAll()).thenReturn(listDTO);

		ResponseEntity<List<CatRegisterDTO>> listResponseEntity = controller.findAllRegisters();

		verify(service, only()).findAll();

		assertTrue(controller.findAllRegisters().getStatusCode().is2xxSuccessful());
		assertFalse(Objects.requireNonNull(listResponseEntity.getBody()).isEmpty());
		assertSame(listDTO.get(1), Objects.requireNonNull(listResponseEntity.getBody()).get(1));
		assertNotSame(listDTO.get(1), listResponseEntity.getBody().get(0));
	}

	@Test
	void findByChatId() {
		CatRegister catRegister = new CatRegister(ADOPTERS_CHAT_ID_1, new Cat(NAME, AGE, DISABILITY, COMMENTS), new CatAdopter(CHAT_ID, FULL_NAME, PHONE_NUMBER), TRIAL_PERIOD_1);

		when(service.findByAdoptersChatId(ADOPTERS_CHAT_ID_1)).thenReturn(Optional.of(catRegister));

		ResponseEntity<CatRegisterDTO> responseEntity = controller.findByChatId(ADOPTERS_CHAT_ID_1);

		verify(service, only()).findByAdoptersChatId(ADOPTERS_CHAT_ID_1);


		assertTrue(controller.findByChatId(ADOPTERS_CHAT_ID_1).getStatusCode().is2xxSuccessful());
		assertFalse(controller.findByChatId(ID_1).getStatusCode().is2xxSuccessful());
		assertFalse(controller.findByChatId(CHAT_ID).getStatusCode().is2xxSuccessful());
		assertTrue(controller.findByChatId(ADOPTERS_CHAT_ID_2).getStatusCode().is4xxClientError());

		assertNotNull(controller.findByChatId(ADOPTERS_CHAT_ID_1));

		assertEquals(catRegister.getCat().getId(), Objects.requireNonNull(responseEntity.getBody()).getDogId());
	}

	@Test
	void notFoundDogRegisterByChatId() {
		when(service.findByAdoptersChatId(ADOPTERS_CHAT_ID_2)).thenReturn(Optional.empty());

		ResponseEntity<CatRegisterDTO> responseEntity = controller.findByChatId(ADOPTERS_CHAT_ID_2);

		verify(service, only()).findByAdoptersChatId(ADOPTERS_CHAT_ID_2);

		assertFalse(responseEntity.getStatusCode().is2xxSuccessful());
		assertTrue(controller.findByChatId(ADOPTERS_CHAT_ID_2).getStatusCode().is4xxClientError());
		assertEquals(404, controller.findByChatId(ADOPTERS_CHAT_ID_2).getStatusCodeValue());
	}

	@Test
	void findByCatId() {
		CatRegister catRegister = new CatRegister(ADOPTERS_CHAT_ID_1, new Cat(NAME, AGE, DISABILITY, COMMENTS), new CatAdopter(CHAT_ID, FULL_NAME, PHONE_NUMBER), TRIAL_PERIOD_1);

		when(service.findByCatId(DOG_ID_1)).thenReturn(Optional.of(catRegister));

		ResponseEntity<CatRegisterDTO> responseEntity = controller.findByCatId(DOG_ID_1);

		verify(service, only()).findByCatId(DOG_ID_1);

		assertTrue(controller.findByCatId(ID_1).getStatusCode().is4xxClientError());
		assertTrue(controller.findByCatId(DOG_ID_1).getStatusCode().is2xxSuccessful());
		assertTrue(service.findByCatId(DOG_ID_1).isPresent());

		assertEquals(catRegister.getAdoptersChatId(), Objects.requireNonNull(responseEntity.getBody()).getAdoptersChatId());

		assertNull(responseEntity.getBody().getId());
		responseEntity.getBody().setId(ID_1);
		assertNotNull(responseEntity.getBody().getId());
	}

	@Test
	void findByAdopterId() {
		CatRegister catRegister = new CatRegister(ADOPTERS_CHAT_ID_1, new Cat(NAME, AGE, DISABILITY, COMMENTS), new CatAdopter(CHAT_ID, FULL_NAME, PHONE_NUMBER), TRIAL_PERIOD_1);

		when(service.findByCatAdopterId(ADOPTER_ID_1)).thenReturn(Optional.of(catRegister));

		ResponseEntity<CatRegisterDTO> responseEntity = controller.findByAdopterId(ADOPTER_ID_1);

		verify(service, only()).findByCatAdopterId(ADOPTER_ID_1);
		verify(service, never()).findByCatAdopterId(ADOPTER_ID_2);

		assertTrue(controller.findByAdopterId(ADOPTER_ID_1).getStatusCode().is2xxSuccessful());
		assertTrue(controller.findByAdopterId(ADOPTER_ID_2).getStatusCode().is4xxClientError());
		assertTrue(service.findByCatAdopterId(ADOPTER_ID_1).isPresent());
		assertTrue(service.findByCatAdopterId(ADOPTER_ID_2).isEmpty());

		assertNull(Objects.requireNonNull(responseEntity.getBody()).getId());
		responseEntity.getBody().setId(ID_1);
		assertNotNull(responseEntity.getBody().getId());

		assertEquals(112233L, responseEntity.getBody().getAdoptersChatId());
	}

	@Test
	void addNewCatRegister() {
	}

	@Test
	void changeTrialPeriodOfRegister() {
		controller.changeTrialPeriodOfRegister(ID_1, TRIAL_PERIOD_2);
		verify(service, only()).updateTrialPeriod(ID_1, TRIAL_PERIOD_2);
		verify(service, never()).updateTrialPeriod(ID_1, TRIAL_PERIOD_1);
		verify(service, never()).updateTrialPeriod(ID_2, TRIAL_PERIOD_2);
	}

	@Test
	void deleteRegister() {
		controller.deleteRegister(ID_1);
		verify(service, only()).deleteCatRegister(ID_1);
		verify(service, never()).deleteCatRegister(ID_2);
	}
}