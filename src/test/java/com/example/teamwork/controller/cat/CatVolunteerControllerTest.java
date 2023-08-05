package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatVolunteerDTO;
import com.example.teamwork.model.CatVolunteer;
import com.example.teamwork.service.cat.CatVolunteerService;
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
class CatVolunteerControllerTest {

	@InjectMocks
	private CatVolunteerController controller;

	@Mock
	private CatVolunteerService service;

	@Test
	void addCatVolunteer() {
		controller.addCatVolunteer(FULL_NAME_1);

		verify(service, only()).addCatVolunteer(FULL_NAME_1);

		verify(service, never()).addCatVolunteer(FULL_NAME_2);

		verify(service, never()).addCatVolunteer(FULL_NAME_3);
	}

	@Test
	void findAllCatsVolunteer() {
		CatVolunteerDTO catVolunteerDTO1 = new CatVolunteerDTO(ID_1, FULL_NAME_1);
		CatVolunteerDTO catVolunteerDTO2 = new CatVolunteerDTO(ID_2, FULL_NAME_2);
		CatVolunteerDTO catVolunteerDTO3 = new CatVolunteerDTO(ID_3, FULL_NAME_3);

		List<CatVolunteerDTO> listDTO = Arrays.asList(catVolunteerDTO1, catVolunteerDTO2, catVolunteerDTO3);

		when(service.findAll()).thenReturn(listDTO);

		ResponseEntity<List<CatVolunteerDTO>> list = controller.findAllCatsVolunteer();

		verify(service, times(1)).findAll();

		assertNotNull(list);
		assertNotNull(listDTO);
		assertEquals(listDTO.size(), Objects.requireNonNull(list.getBody()).size());
		assertEquals(listDTO.containsAll(list.getBody()), list.getBody().containsAll(listDTO));
		assertEquals(listDTO.indexOf(catVolunteerDTO1), list.getBody().indexOf(catVolunteerDTO1));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.getBody().get(list.getBody().size()));
	}

	@Test
	void findCatVolunteer() {
		CatVolunteer catVolunteer = new CatVolunteer(FULL_NAME_1);
		catVolunteer.setId(ID_1);

		when(service.findById(ID_1)).thenReturn(Optional.of(catVolunteer));

		ResponseEntity<CatVolunteerDTO> volunteer = controller.findCatVolunteer(ID_1);

		verify(service, times(1)).findById(ID_1);

		assertTrue(service.findById(ID_1).isPresent());
		assertEquals(200, volunteer.getStatusCodeValue());
		assertEquals(catVolunteer.getId(), Objects.requireNonNull(volunteer.getBody()).getId());
		assertEquals(catVolunteer.getFullName(), volunteer.getBody().getFullName());
	}

	@Test
	void deleteCatVolunteer() {
		controller.deleteCatVolunteer(ID_1);

		verify(service, only()).deleteCatVolunteer(ID_1);

		verify(service, never()).deleteCatVolunteer(ID_2);

		verify(service, never()).deleteCatVolunteer(ID_3);
	}
}