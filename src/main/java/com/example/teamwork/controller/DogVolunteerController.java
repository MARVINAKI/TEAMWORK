package com.example.teamwork.controller;

import com.example.teamwork.DTO.DogVolunteerDTO;
import com.example.teamwork.service.DogVolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возможность добавить, найти или удалить волонтёра в/из БД собачего приюта.
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/dogVolunteer")
public class DogVolunteerController {

	private final DogVolunteerService dogVolunteerService;

	public DogVolunteerController(DogVolunteerService dogVolunteerService) {
		this.dogVolunteerService = dogVolunteerService;
	}

	@Operation(summary = "Добавление волонтёра в БД собачего приюта",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешное добавление в БД"
					)
			})
	@PostMapping()
	public ResponseEntity<DogVolunteerDTO> addDogVolunteer(DogVolunteerDTO dogVolunteerDTO) {
		dogVolunteerService.addDogVolunteer(dogVolunteerDTO);
		return ResponseEntity.ok(dogVolunteerDTO);
	}

	@Operation(summary = "Поиск всех волонтёров в БД",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Волонтёр(ы) найден(ы)"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Объект(ы) в БД не найден(ы)"
					)
			})
	@GetMapping("/dogsVolunteer")
	public ResponseEntity<List<DogVolunteerDTO>> getAllDogsVolunteer() {
		return ResponseEntity.ok().body(dogVolunteerService.getAllDogsVolunteer());
	}

	@Operation(summary = "Поиск волонтёра в БД по id",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Волонтёр найден"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Введён некорректный параметр для поиска или объект не найден"
					)
			})
	@GetMapping("/dogVolunteer")
	public ResponseEntity<DogVolunteerDTO> getDogVolunteer(Long id) {
		return ResponseEntity.ok().body(dogVolunteerService.findDogVolunteer(id));
	}

	@Operation(summary = "Удаление волонтёра из БД собачего приюта",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешное удаление из БД"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Введён некорректный параметр для поиска или объект не найден"
					)
			})
	@DeleteMapping("/deleteVolunteer")
	public ResponseEntity<Void> deleteDogVolunteer(Long id) {
		dogVolunteerService.deleteDogVolunteer(id);
		return ResponseEntity.ok().build();
	}
}
