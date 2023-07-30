package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogDTO;
import com.example.teamwork.model.Dog;
import com.example.teamwork.service.dog.DogShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возвожность добавить, найти и удалить питомца в/из приют(e,а).
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
			),
			@ApiResponse(
					responseCode = "400",
					description = "Параметры запроса отсутствуют или имеют некорректный формат"
			)
	})
	@PostMapping("/save_test/name={name}/age={age}/disability={disability}/comments={comments}")
	public void addPetToDogShelter(@PathVariable String name,
								   @PathVariable Integer age,
								   @PathVariable String disability,
								   @PathVariable String comments) {
		dogShelterService.addPetToShelter(name, age, disability, comments);
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
	@GetMapping("/id={id}")
	public ResponseEntity<DogDTO> findDogById(@PathVariable Long id) {
		Optional<Dog> dog = dogShelterService.findById(id);
		return dog.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(Dog.convert(dog.get()));
	}

	@Operation(summary = "Поиск всех питомцев в приюте")
	@GetMapping("/dogs")
	public ResponseEntity<List<DogDTO>> findAllDogsInTheShelter() {
		return ResponseEntity.ok().body(dogShelterService.findAll());
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
	@DeleteMapping("/id={id}")
	public void deleteDogById(@PathVariable Long id) {
		dogShelterService.deleteById(id);
	}
}
