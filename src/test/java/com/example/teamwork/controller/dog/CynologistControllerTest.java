package com.example.teamwork.controller.dog;

import com.example.teamwork.model.Cynologist;
import com.example.teamwork.service.dog.CynologistService;
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
class CynologistControllerTest {

	@InjectMocks
	private CynologistController controller;

	@Mock
	private CynologistService service;

	@Test
	void addCynologist() {
		controller.addCynologist(FULL_NAME_1, EXPERIENCE_1, PHONE_NUMBER_1, EMAIL_1, COMMENTS_1);
		verify(service, only()).addCynologist(FULL_NAME_1, EXPERIENCE_1, PHONE_NUMBER_1, EMAIL_1, COMMENTS_1);
	}

	@Test
	void findCynologistById() {
		Cynologist cynologist = new Cynologist(FULL_NAME_1, EXPERIENCE_1, PHONE_NUMBER_1, EMAIL_1, COMMENTS_1);
		cynologist.setId(ID_1);

		when(service.findById(ID_1)).thenReturn(Optional.of(cynologist));

		ResponseEntity<Cynologist> responseEntity = controller.findCynologistById(ID_1);

		verify(service, only()).findById(ID_1);

		assertTrue(service.findById(ID_1).isPresent());
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		assertFalse(controller.findCynologistById(ID_2).getStatusCode().is2xxSuccessful());

		assertSame(cynologist, responseEntity.getBody());
	}

	@Test
	void findAllCynologists() {
		Cynologist cynologist1 = new Cynologist(FULL_NAME_1, EXPERIENCE_1, PHONE_NUMBER_1, EMAIL_1, COMMENTS_1);
		Cynologist cynologist2 = new Cynologist(FULL_NAME_2, EXPERIENCE_2, PHONE_NUMBER_2, EMAIL_2, COMMENTS_2);
		List<Cynologist> list = Arrays.asList(cynologist1, cynologist2);

		when(service.findAll()).thenReturn(list);

		ResponseEntity<List<Cynologist>> listResponseEntity = controller.findAllCynologists();

		verify(service, only()).findAll();

		assertFalse(service.findAll().isEmpty());
		assertTrue(controller.findAllCynologists().getStatusCode().is2xxSuccessful());
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> controller.findAllCynologists().getBody().get(Objects.requireNonNull(listResponseEntity.getBody()).size()));
	}

	@Test
	void deleteCynologist() {
		controller.deleteCynologist(ID_2);
		verify(service, only()).deleteById(ID_2);
		verify(service, never()).deleteById(ID_1);
	}
}