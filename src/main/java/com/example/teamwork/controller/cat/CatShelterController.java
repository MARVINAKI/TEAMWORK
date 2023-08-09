package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatDTO;
import com.example.teamwork.model.Cat;
import com.example.teamwork.service.cat.CatShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возвожность добавить, найти и удалить питомца в/из приюта.
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/CatShelter")
public class CatShelterController {
	private final CatShelterService catShelterService;

	public CatShelterController(CatShelterService catShelterService) {
		this.catShelterService = catShelterService;
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
	@PostMapping("/name={name}/age={age}/disability={disability}/comments={comments}")
	public void addPetToCatShelter(@PathVariable String name,
								   @PathVariable Integer age,
								   @PathVariable String disability,
								   @PathVariable String comments) {
		catShelterService.addPetToShelter(name, age, disability, comments);
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
	public ResponseEntity<CatDTO> findCatById(@PathVariable Long id) {
		Optional<Cat> cat = catShelterService.findById(id);
		return cat.isEmpty() ? ResponseEntity.notFound().header("Error", "Object not found in DB").build() : ResponseEntity.ok(Cat.convert(cat.get()));
	}

	@Operation(summary = "Поиск всех питомцев в приюте")
	@GetMapping("/cats")
	public ResponseEntity<List<CatDTO>> findAllCatsInTheShelter() {
		return ResponseEntity.ok().body(catShelterService.findAll());
	}

	@Operation(summary = "Удаление питомца из БД кошачего приюта",
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
	@DeleteMapping("/{id}")
	public void deleteCatById(@PathVariable Long id) {
		catShelterService.deleteById(id);
	}
}
