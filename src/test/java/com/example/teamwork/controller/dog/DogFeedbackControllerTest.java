package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogFeedbackDTO;
import com.example.teamwork.service.dog.DogFeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DogFeedbackControllerTest {

	private static final Long ID_1 = 1L;
	private static final Long ID_2 = 2L;
	private static final Long ID_3 = 3L;
	private static final String FULL_NAME_1 = "name1";
	private static final String FULL_NAME_2 = "name2";
	private static final String FULL_NAME_3 = "name3";
	private static final String PHONE_NUMBER_1 = "89171111111";
	private static final String PHONE_NUMBER_2 = "89172222222";
	private static final String PHONE_NUMBER_3 = "89173333333";
	private static final String EMAIL_1 = "email1@mail.ru";
	private static final String EMAIL_2 = "email2@mail.ru";
	private static final String EMAIL_3 = "email3@mail.ru";
	private static final String COMMENTS_1 = "text1";
	private static final String COMMENTS_2 = "text2";
	private static final String COMMENTS_3 = "text3";
	private static final Long VOLUNTEER_ID_1 = 11L;
	private static final Long VOLUNTEER_ID_2 = 22L;
	private static final Long VOLUNTEER_ID_3 = 33L;

	@InjectMocks
	private DogFeedbackController controller;

	@Mock
	private DogFeedbackService service;

	@Test
	void findAllClientsRequest() {
		DogFeedbackDTO dogFeedbackDTO1 = new DogFeedbackDTO(ID_1, FULL_NAME_1, PHONE_NUMBER_1, EMAIL_1, COMMENTS_1, VOLUNTEER_ID_1);
		DogFeedbackDTO dogFeedbackDTO2 = new DogFeedbackDTO(ID_2, FULL_NAME_2, PHONE_NUMBER_2, EMAIL_2, COMMENTS_2, VOLUNTEER_ID_2);
		DogFeedbackDTO dogFeedbackDTO3 = new DogFeedbackDTO(ID_3, FULL_NAME_3, PHONE_NUMBER_3, EMAIL_3, COMMENTS_3, VOLUNTEER_ID_3);
		List<DogFeedbackDTO> listDTO = Arrays.asList(dogFeedbackDTO1, dogFeedbackDTO2, dogFeedbackDTO3);

		when(service.findOpenRequests()).thenReturn(listDTO);

		ResponseEntity<List<DogFeedbackDTO>> listResponseEntity = controller.findAllClientsRequest();

		assertTrue(controller.findAllClientsRequest().getStatusCode().is2xxSuccessful());
		assertFalse(service.findOpenRequests().isEmpty());

		assertSame(service.findOpenRequests().get(1), Objects.requireNonNull(controller.findAllClientsRequest().getBody()).get(1));

		assertEquals(3, Objects.requireNonNull(controller.findAllClientsRequest().getBody()).size());

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> Objects.requireNonNull(listResponseEntity.getBody()).get(listResponseEntity.getBody().size() + 1));
	}

	@Test
	void closeProcessedRequest() {
		controller.closeProcessedRequest(ID_1);
		verify(service, only()).closeProcessedRequest(ID_1);
		verify(service, never()).closeProcessedRequest(ID_2);
		verify(service, never()).closeProcessedRequest(ID_3);
	}
}