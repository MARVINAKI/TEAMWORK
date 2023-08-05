package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogRegisterDTO;
import com.example.teamwork.model.Dog;
import com.example.teamwork.model.DogAdopter;
import com.example.teamwork.model.DogRegister;
import com.example.teamwork.service.dog.DogAdopterService;
import com.example.teamwork.service.dog.DogRegisterService;
import com.example.teamwork.service.dog.DogShelterService;
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
class DogRegisterControllerTest {




	@InjectMocks
	private DogRegisterController controller;

	@Mock
	private DogRegisterService dogRegisterService;

	@Mock
	private DogShelterService dogShelterService;

	@Mock
	private DogAdopterService dogAdopterService;

	@Mock
	private Dog dog;

	@Mock
	private DogAdopter dogAdopter;

	@Test
	void findAllRegisters() {
		DogRegisterDTO dogRegisterDTO1 = new DogRegisterDTO(ID_1, ADOPTERS_CHAT_ID_1, DOG_ID_1, ADOPTER_ID_1, TRIAL_PERIOD_1, REGISTRATION_DATE_1, LAST_DATE_OF_REPORTS_1);
		DogRegisterDTO dogRegisterDTO2 = new DogRegisterDTO(ID_2, ADOPTERS_CHAT_ID_2, DOG_ID_2, ADOPTER_ID_2, TRIAL_PERIOD_2, REGISTRATION_DATE_2, LAST_DATE_OF_REPORTS_2);
		List<DogRegisterDTO> listDTO = Arrays.asList(dogRegisterDTO1, dogRegisterDTO2);

		when(dogRegisterService.findAll()).thenReturn(listDTO);

		ResponseEntity<List<DogRegisterDTO>> listResponseEntity = controller.findAllRegisters();

		verify(dogRegisterService, only()).findAll();

		assertTrue(controller.findAllRegisters().getStatusCode().is2xxSuccessful());
		assertFalse(Objects.requireNonNull(listResponseEntity.getBody()).isEmpty());
		assertSame(listDTO.get(1), Objects.requireNonNull(listResponseEntity.getBody()).get(1));
		assertNotSame(listDTO.get(1), listResponseEntity.getBody().get(0));

	}

	@Test
	void findDogRegisterByChatId() {
		DogRegister dogRegister = new DogRegister(ADOPTERS_CHAT_ID_1, new Dog(NAME, AGE, DISABILITY, COMMENTS), new DogAdopter(CHAT_ID, FULL_NAME, PHONE_NUMBER), TRIAL_PERIOD_1);

		when(dogRegisterService.findByAdoptersChatId(ADOPTERS_CHAT_ID_1)).thenReturn(Optional.of(dogRegister));

		ResponseEntity<DogRegisterDTO> responseEntity = controller.findByChatId(ADOPTERS_CHAT_ID_1);

		verify(dogRegisterService, only()).findByAdoptersChatId(ADOPTERS_CHAT_ID_1);


		assertTrue(controller.findByChatId(ADOPTERS_CHAT_ID_1).getStatusCode().is2xxSuccessful());
		assertFalse(controller.findByChatId(ID_1).getStatusCode().is2xxSuccessful());
		assertFalse(controller.findByChatId(CHAT_ID).getStatusCode().is2xxSuccessful());
		assertTrue(controller.findByChatId(ADOPTERS_CHAT_ID_2).getStatusCode().is4xxClientError());

		assertNotNull(controller.findByChatId(ADOPTERS_CHAT_ID_1));

		assertEquals(dogRegister.getDog().getId(), Objects.requireNonNull(responseEntity.getBody()).getDogId());
	}

	@Test
	void notFoundDogRegisterByChatId() {
		when(dogRegisterService.findByAdoptersChatId(ADOPTERS_CHAT_ID_2)).thenReturn(Optional.empty());

		ResponseEntity<DogRegisterDTO> responseEntity = controller.findByChatId(ADOPTERS_CHAT_ID_2);

		verify(dogRegisterService, only()).findByAdoptersChatId(ADOPTERS_CHAT_ID_2);

		assertFalse(responseEntity.getStatusCode().is2xxSuccessful());
		assertTrue(controller.findByChatId(ADOPTERS_CHAT_ID_2).getStatusCode().is4xxClientError());
		assertEquals(404, controller.findByChatId(ADOPTERS_CHAT_ID_2).getStatusCodeValue());
	}

	@Test
	void findByDogId() {
		DogRegister dogRegister = new DogRegister(ADOPTERS_CHAT_ID_1, new Dog(NAME, AGE, DISABILITY, COMMENTS), new DogAdopter(CHAT_ID, FULL_NAME, PHONE_NUMBER), TRIAL_PERIOD_1);

		when(dogRegisterService.findByDogId(DOG_ID_1)).thenReturn(Optional.of(dogRegister));

		ResponseEntity<DogRegisterDTO> responseEntity = controller.findByDogId(DOG_ID_1);

		verify(dogRegisterService, only()).findByDogId(DOG_ID_1);

		assertTrue(controller.findByDogId(ID_1).getStatusCode().is4xxClientError());
		assertTrue(controller.findByDogId(DOG_ID_1).getStatusCode().is2xxSuccessful());
		assertTrue(dogRegisterService.findByDogId(DOG_ID_1).isPresent());

		assertEquals(dogRegister.getAdoptersChatId(), Objects.requireNonNull(responseEntity.getBody()).getAdoptersChatId());

		assertNull(responseEntity.getBody().getId());
		responseEntity.getBody().setId(ID_1);
		assertNotNull(responseEntity.getBody().getId());
	}

	@Test
	void findByAdopterId() {
		DogRegister dogRegister = new DogRegister(ADOPTERS_CHAT_ID_1, new Dog(NAME, AGE, DISABILITY, COMMENTS), new DogAdopter(CHAT_ID, FULL_NAME, PHONE_NUMBER), TRIAL_PERIOD_1);

		when(dogRegisterService.findByDogAdopterId(ADOPTER_ID_1)).thenReturn(Optional.of(dogRegister));

		ResponseEntity<DogRegisterDTO> responseEntity = controller.findByAdopterId(ADOPTER_ID_1);

		verify(dogRegisterService, only()).findByDogAdopterId(ADOPTER_ID_1);
		verify(dogRegisterService, never()).findByDogAdopterId(ADOPTER_ID_2);

		assertTrue(controller.findByAdopterId(ADOPTER_ID_1).getStatusCode().is2xxSuccessful());
		assertTrue(controller.findByAdopterId(ADOPTER_ID_2).getStatusCode().is4xxClientError());
		assertTrue(dogRegisterService.findByDogAdopterId(ADOPTER_ID_1).isPresent());
		assertTrue(dogRegisterService.findByDogAdopterId(ADOPTER_ID_2).isEmpty());

		assertNull(Objects.requireNonNull(responseEntity.getBody()).getId());
		responseEntity.getBody().setId(ID_1);
		assertNotNull(responseEntity.getBody().getId());

		assertEquals(112233L, responseEntity.getBody().getAdoptersChatId());
	}

	@Test
	void addNewDogRegister() {
	}

	@Test
	void changeTrialPeriodOfRegister() {
		controller.changeTrialPeriodOfRegister(ID_1, TRIAL_PERIOD_2);
		verify(dogRegisterService, only()).updateTrialPeriod(ID_1, TRIAL_PERIOD_2);
		verify(dogRegisterService, never()).updateTrialPeriod(ID_1, TRIAL_PERIOD_1);
		verify(dogRegisterService, never()).updateTrialPeriod(ID_2, TRIAL_PERIOD_2);
	}

	@Test
	void deleteRegister() {
		controller.deleteRegister(ID_1);
		verify(dogRegisterService, only()).deleteDogRegister(ID_1);
		verify(dogRegisterService, never()).deleteDogRegister(ID_2);
	}
}