package com.example.teamwork.controller;

import com.example.teamwork.DTO.CatDTO;
import com.example.teamwork.service.CatShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
			)
	})
	@PostMapping()
	public ResponseEntity<CatDTO> addPetToCatShelter(CatDTO catDTO) {
		catShelterService.save(catDTO);
		return ResponseEntity.ok().body(catDTO);
	}

	@Operation(summary = "Поиск питомца по идентификационному номеру")
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Питомец найден",
					content = {
							@Content(
									mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = CatDTO.class)
							)
					}
			),
			@ApiResponse(
					responseCode = "500",
					description = "Введён некорректный параметр для поиска или объект не найден"
			)
	})
	@GetMapping("/get")
	public ResponseEntity<CatDTO> findCatById(@Parameter(description = "Идентификационный номер", required = true) Long id) {
		return ResponseEntity.ok().body(catShelterService.getById(id));
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
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteCatById(Long id) {
		catShelterService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
