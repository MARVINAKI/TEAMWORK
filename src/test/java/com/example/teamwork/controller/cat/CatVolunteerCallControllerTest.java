package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatVolunteerCallDTO;
import com.example.teamwork.model.CatVolunteer;
import com.example.teamwork.model.CatVolunteerCall;
import com.example.teamwork.service.cat.CatVolunteerCallService;
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
class CatVolunteerCallControllerTest {

	@InjectMocks
	private CatVolunteerCallController controller;

	@Mock
	private CatVolunteerCallService service;

	@Test
	void findCatVolunteerCall() {
		CatVolunteer catVolunteer = new CatVolunteer(FULL_NAME);
		catVolunteer.setId(ID_1);

		CatVolunteerCall catVolunteerCall = new CatVolunteerCall(CHAT_ID_1);
		catVolunteerCall.setId(ID_1);
		catVolunteerCall.setRequestTime(REQUEST_TIME_1);
		catVolunteerCall.setCatVolunteer(catVolunteer);

		when(service.findById(ID_1)).thenReturn(Optional.of(catVolunteerCall));

		ResponseEntity<CatVolunteerCall> entity = controller.findCatVolunteerCall(ID_1);

		verify(service, only()).findById(ID_1);

		assertTrue(service.findById(ID_1).isPresent());
		assertTrue(entity.getStatusCode().is2xxSuccessful());

		assertNotNull(catVolunteerCall.getCatVolunteer().getId());
		assertNotNull(catVolunteerCall.getCatVolunteer().getFullName());

		assertEquals(catVolunteerCall.getId(), Objects.requireNonNull(entity.getBody()).getId());
		assertEquals(catVolunteerCall.getCatVolunteer().getId(), entity.getBody().getCatVolunteer().getId());
	}

	@Test
	void findAllVolunteersCall() {
		CatVolunteerCallDTO catVolunteerCallDTO1 = new CatVolunteerCallDTO(ID_1, CHAT_ID_1, REQUEST_TIME_1, DOG_VOLUNTEER_ID_1);
		CatVolunteerCallDTO catVolunteerCallDTO2 = new CatVolunteerCallDTO(ID_2, CHAT_ID_2, REQUEST_TIME_2, DOG_VOLUNTEER_ID_2);
		CatVolunteerCallDTO catVolunteerCallDTO3 = new CatVolunteerCallDTO(ID_3, CHAT_ID_3, REQUEST_TIME_3, DOG_VOLUNTEER_ID_3);

		List<CatVolunteerCallDTO> listDTO = Arrays.asList(catVolunteerCallDTO1, catVolunteerCallDTO2, catVolunteerCallDTO3);

		when(service.findAllVolunteerCalls()).thenReturn(listDTO);

		ResponseEntity<List<CatVolunteerCallDTO>> listEntity = controller.findAllVolunteersCall();

		verify(service, times(1)).findAllVolunteerCalls();

		assertTrue(listEntity.getStatusCode().is2xxSuccessful());
		assertEquals(200, listEntity.getStatusCodeValue());
		assertFalse(Objects.requireNonNull(listEntity.getBody()).isEmpty());

		assertEquals(3, listEntity.getBody().size());
		assertEquals(listEntity.getBody().get(0), catVolunteerCallDTO1);
		assertEquals(listEntity.getBody().get(1), catVolunteerCallDTO2);
		assertEquals(listEntity.getBody().get(2), catVolunteerCallDTO3);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> listEntity.getBody().get(listEntity.getBody().size()));
	}

	@Test
	void closeVolunteerCall() {
		controller.closeVolunteerCall(ID_3);
		verify(service, only()).closeVolunteerCall(ID_3);
		verify(service, never()).closeVolunteerCall(ID_2);
		verify(service, never()).closeVolunteerCall(ID_1);
	}
}