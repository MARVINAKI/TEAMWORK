package com.example.teamwork.controller;

import com.example.teamwork.DTO.CynologistDTO;
import com.example.teamwork.service.CynologistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возможность добавить, найти или удалить кинолога в/из БД собачего приюта.
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/cynologist")
public class CynologistController {

	private final CynologistService cynologistService;

	public CynologistController(CynologistService cynologistService) {
		this.cynologistService = cynologistService;
	}

	@Operation(summary = "Добавление кинолога в БД собачего приюта",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешное добавление в БД"
					)
			})
	@PostMapping
	public ResponseEntity<CynologistDTO> addCynologist(CynologistDTO cynologistDTO) {
		cynologistService.save(cynologistDTO);
		return ResponseEntity.ok().body(cynologistDTO);
	}

	@Operation(summary = "Поиск кинолога в БД по id",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Кинолог найден"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Введён некорректный параметр для поиска или объект не найден"
					)
			})
	@GetMapping("/findCynologist")
	public ResponseEntity<CynologistDTO> findCynologistById(Long id) {
		return ResponseEntity.ok(cynologistService.getById(id));
	}

	@Operation(summary = "Поиск всех кинологов в БД",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Кинолог(и) найден(ы)"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Объект(ы) в БД не найден(ы)"
					)
			})
	@GetMapping("/cynologists")
	public ResponseEntity<List<CynologistDTO>> findAllCynologists() {
		return ResponseEntity.ok().body(cynologistService.getAll());
	}

	@Operation(summary = "Удаление кинолога из БД собачего приюта",
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
	@DeleteMapping("/deleteCynologist")
	public ResponseEntity<Void> deleteCynologist(Long id) {
		cynologistService.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
