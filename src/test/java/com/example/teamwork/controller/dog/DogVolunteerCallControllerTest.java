package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogVolunteerCallDTO;
import com.example.teamwork.model.DogVolunteer;
import com.example.teamwork.model.DogVolunteerCall;
import com.example.teamwork.service.dog.DogVolunteerCallService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogVolunteerCallControllerTest {

	private static final Long ID_1 = 1L;
	private static final Long ID_2 = 2L;
	private static final Long ID_3 = 3L;
	private static final Long CHAT_ID_1 = 112233L;
	private static final Long CHAT_ID_2 = 223344L;
	private static final Long CHAT_ID_3 = 334455L;
	private static final LocalDateTime REQUEST_TIME_1 = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).minusDays(1);
	private static final LocalDateTime REQUEST_TIME_2 = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
	private static final LocalDateTime REQUEST_TIME_3 = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1);
	private static final Long DOG_VOLUNTEER_ID_1 = 11L;
	private static final Long DOG_VOLUNTEER_ID_2 = 22L;
	private static final Long DOG_VOLUNTEER_ID_3 = 33L;
	private static final String FULL_NAME = "Ivanov Ivan";

	@InjectMocks
	private DogVolunteerCallController controller;

	@Mock
	private DogVolunteerCallService service;

	@Test
	void findDogVolunteerCallTest() {

		DogVolunteer dogVolunteer = new DogVolunteer();
		dogVolunteer.setId(ID_1);
		dogVolunteer.setFullName(FULL_NAME);

		DogVolunteerCall dogVolunteerCall = new DogVolunteerCall();
		dogVolunteerCall.setId(ID_1);
		dogVolunteerCall.setChatId(CHAT_ID_1);
		dogVolunteerCall.setRequestTime(REQUEST_TIME_1);
		dogVolunteerCall.setDogVolunteer(dogVolunteer);


		when(service.findById(ID_1)).thenReturn(Optional.of(dogVolunteerCall));

		ResponseEntity<DogVolunteerCallDTO> entity = controller.findDogVolunteerCall(ID_1);

		verify(service, only()).findById(ID_1);

		assertTrue(service.findById(ID_1).isPresent());
		assertTrue(entity.getStatusCode().is2xxSuccessful());

		assertNotNull(dogVolunteerCall.getDogVolunteer().getId());
		assertNotNull(dogVolunteerCall.getDogVolunteer().getFullName());

		assertEquals(dogVolunteerCall.getId(), Objects.requireNonNull(entity.getBody()).getId());
		assertEquals(dogVolunteerCall.getDogVolunteer().getId(), entity.getBody().getDogVolunteerId());
	}

	@Test
	void notFoundDogVolunteerCallTest() {

		when(service.findById(ID_2)).thenReturn(Optional.empty());

		ResponseEntity<DogVolunteerCallDTO> entity = controller.findDogVolunteerCall(ID_2);

		verify(service, only()).findById(ID_2);

		assertFalse(entity.getStatusCode().is2xxSuccessful());
		assertTrue(entity.getStatusCode().is4xxClientError());
		assertEquals(404, entity.getStatusCodeValue());

	}

	@Test
	void findAllVolunteerCallsTest() {

		DogVolunteerCallDTO dogVolunteerCallDTO1 = new DogVolunteerCallDTO(ID_1, CHAT_ID_1, REQUEST_TIME_1, DOG_VOLUNTEER_ID_1);
		DogVolunteerCallDTO dogVolunteerCallDTO2 = new DogVolunteerCallDTO(ID_2, CHAT_ID_2, REQUEST_TIME_2, DOG_VOLUNTEER_ID_2);
		DogVolunteerCallDTO dogVolunteerCallDTO3 = new DogVolunteerCallDTO(ID_3, CHAT_ID_3, REQUEST_TIME_3, DOG_VOLUNTEER_ID_3);

		List<DogVolunteerCallDTO> listDTO = Arrays.asList(dogVolunteerCallDTO1, dogVolunteerCallDTO2, dogVolunteerCallDTO3);

		when(service.findAllVolunteerCalls()).thenReturn(listDTO);

		ResponseEntity<List<DogVolunteerCallDTO>> listEntity = controller.findAllVolunteerCalls();

		verify(service, times(1)).findAllVolunteerCalls();

		assertTrue(listEntity.getStatusCode().is2xxSuccessful());
		assertEquals(200, listEntity.getStatusCodeValue());
		assertFalse(Objects.requireNonNull(listEntity.getBody()).isEmpty());

		assertEquals(3, listEntity.getBody().size());
		assertEquals(listEntity.getBody().get(0), dogVolunteerCallDTO1);
		assertEquals(listEntity.getBody().get(1), dogVolunteerCallDTO2);
		assertEquals(listEntity.getBody().get(2), dogVolunteerCallDTO3);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> listEntity.getBody().get(listEntity.getBody().size()));
	}

	@Test
	void closeVolunteerCallTest() {
		controller.closeVolunteerCall(ID_3);
		verify(service, only()).closeVolunteerCall(ID_3);
		verify(service, never()).closeVolunteerCall(ID_2);
		verify(service, never()).closeVolunteerCall(ID_1);
	}
}