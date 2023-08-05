package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatFeedbackDTO;
import com.example.teamwork.service.cat.CatFeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.teamwork.constant.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatFeedbackControllerTest {

	@InjectMocks
	private CatFeedbackController controller;

	@Mock
	private CatFeedbackService service;

	@Test
	void findAllOpenClientRequests() {
		CatFeedbackDTO catFeedbackDTO1 = new CatFeedbackDTO(ID_1, FULL_NAME_1, PHONE_NUMBER_1, EMAIL_1, COMMENTS_1, VOLUNTEER_ID_1);
		CatFeedbackDTO catFeedbackDTO2 = new CatFeedbackDTO(ID_2, FULL_NAME_2, PHONE_NUMBER_2, EMAIL_2, COMMENTS_2, VOLUNTEER_ID_2);
		CatFeedbackDTO catFeedbackDTO3 = new CatFeedbackDTO(ID_3, FULL_NAME_3, PHONE_NUMBER_3, EMAIL_3, COMMENTS_3, VOLUNTEER_ID_3);
		List<CatFeedbackDTO> listDTO = Arrays.asList(catFeedbackDTO1, catFeedbackDTO2, catFeedbackDTO3);

		when(service.findOpenRequests()).thenReturn(listDTO);

		ResponseEntity<List<CatFeedbackDTO>> listResponseEntity = controller.findAllOpenClientRequests();

		assertTrue(controller.findAllOpenClientRequests().getStatusCode().is2xxSuccessful());
		assertFalse(service.findOpenRequests().isEmpty());

		assertSame(service.findOpenRequests().get(1), Objects.requireNonNull(controller.findAllOpenClientRequests().getBody()).get(1));

		assertEquals(3, Objects.requireNonNull(controller.findAllOpenClientRequests().getBody()).size());

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