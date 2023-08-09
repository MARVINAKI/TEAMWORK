package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogVolunteerDTO;
import com.example.teamwork.model.DogVolunteer;
import com.example.teamwork.service.dog.DogVolunteerService;
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
class DogVolunteerControllerTest {


	@InjectMocks
	private DogVolunteerController dogVolunteerController;

	@Mock
	private DogVolunteerService dogVolunteerService;

	@Test
	void findAllDogsVolunteerTest() {

		DogVolunteerDTO dogVolunteerDTO1 = new DogVolunteerDTO(ID_1, FULL_NAME_1);
		DogVolunteerDTO dogVolunteerDTO2 = new DogVolunteerDTO(ID_2, FULL_NAME_2);
		DogVolunteerDTO dogVolunteerDTO3 = new DogVolunteerDTO(ID_3, FULL_NAME_3);

		List<DogVolunteerDTO> listDTO = Arrays.asList(dogVolunteerDTO1, dogVolunteerDTO2, dogVolunteerDTO3);

		when(dogVolunteerService.findAll()).thenReturn(listDTO);

		ResponseEntity<List<DogVolunteerDTO>> list = dogVolunteerController.findAllDogsVolunteer();

		verify(dogVolunteerService, times(1)).findAll();

		assertNotNull(list);
		assertNotNull(listDTO);
		assertEquals(listDTO.size(), Objects.requireNonNull(list.getBody()).size());
		assertEquals(listDTO.containsAll(list.getBody()), list.getBody().containsAll(listDTO));
		assertEquals(listDTO.indexOf(dogVolunteerDTO1), list.getBody().indexOf(dogVolunteerDTO1));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.getBody().get(list.getBody().size()));

	}

	@Test
	void addDogVolunteerTest() {

		dogVolunteerController.addDogVolunteer(FULL_NAME_1);

		verify(dogVolunteerService, only()).addDogVolunteer(FULL_NAME_1);

		verify(dogVolunteerService, never()).addDogVolunteer(FULL_NAME_2);

		verify(dogVolunteerService, never()).addDogVolunteer(FULL_NAME_3);
	}

	@Test
	void findDogVolunteerByIdTest() {

		DogVolunteer dogVolunteer = new DogVolunteer(FULL_NAME_1);
		dogVolunteer.setId(ID_1);

		when(dogVolunteerService.findById(ID_1)).thenReturn(Optional.of(dogVolunteer));

		ResponseEntity<DogVolunteerDTO> volunteer = dogVolunteerController.findDogVolunteer(ID_1);

		verify(dogVolunteerService, times(1)).findById(ID_1);

		assertTrue(dogVolunteerService.findById(ID_1).isPresent());
		assertEquals(200, volunteer.getStatusCodeValue());
		assertEquals(dogVolunteer.getId(), Objects.requireNonNull(volunteer.getBody()).getId());
		assertEquals(dogVolunteer.getFullName(), volunteer.getBody().getFullName());

	}

	@Test
	void notFoundDogVolunteerByIdTest() {
		when(dogVolunteerService.findById(ID_2)).thenReturn(Optional.empty());
		ResponseEntity<DogVolunteerDTO> volunteer = dogVolunteerController.findDogVolunteer(ID_2);

		verify(dogVolunteerService, only()).findById(ID_2);

		assertTrue(dogVolunteerService.findById(ID_2).isEmpty());
		assertTrue(volunteer.getStatusCode().is4xxClientError());
		assertEquals(404, volunteer.getStatusCodeValue());

	}

	@Test
	void deleteDogVolunteerByIdTest() {

		dogVolunteerController.deleteDogVolunteer(ID_1);

		verify(dogVolunteerService, only()).deleteDogVolunteer(ID_1);

		verify(dogVolunteerService, never()).deleteDogVolunteer(ID_2);

		verify(dogVolunteerService, never()).deleteDogVolunteer(ID_3);

	}
}