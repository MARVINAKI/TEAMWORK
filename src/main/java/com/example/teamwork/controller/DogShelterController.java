package com.example.teamwork.controller;

import com.example.teamwork.DTO.DogDTO;
import com.example.teamwork.service.DogShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возвожность добавить, найти и удалить питомца в/из приюта.
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/DogShelter")
public class DogShelterController {
	private final DogShelterService dogShelterService;

	public DogShelterController(DogShelterService dogShelterService) {
		this.dogShelterService = dogShelterService;
	}

	@Operation(summary = "Добавление питомца в БД кошачего приюта")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Успешное добавление питомца в БД"
			)
	})
	@PostMapping
	public ResponseEntity<DogDTO> addPetToDogShelter(DogDTO dogDTO) {
		dogShelterService.save(dogDTO);
		return ResponseEntity.ok().body(dogDTO);
	}

	@Operation(summary = "Поиск питомца по идентификационному номеру",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Питомец найден"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Введён некорректный параметр для поиска или объект не найден"
					)
			})
	@GetMapping("/get")
	public ResponseEntity<DogDTO> findDogById(Long id) {
		return ResponseEntity.ok().body(dogShelterService.getById(id));
	}

	@Operation(summary = "Удаление питомца из БД собачего приюта",
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
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteDogById(Long id) {
		dogShelterService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
