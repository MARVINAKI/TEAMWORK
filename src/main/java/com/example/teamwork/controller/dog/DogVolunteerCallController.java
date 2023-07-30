package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogVolunteerCallDTO;
import com.example.teamwork.model.DogVolunteerCall;
import com.example.teamwork.service.dog.DogVolunteerCallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возможность просмотреть открытые запросы на консультацию через telegram и
 * закрыть запрос, если он обработан волонтёром
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/dogVolunteerCall")
public class DogVolunteerCallController {

	private final DogVolunteerCallService dogVolunteerCallService;

	public DogVolunteerCallController(DogVolunteerCallService dogVolunteerCallService) {
		this.dogVolunteerCallService = dogVolunteerCallService;
	}

	@Operation(summary = "Поиск запроса в БД по id",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Запрос найден"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Введён некорректный параметр для поиска или объект не найден"
					)
			})
	@GetMapping("/{id}")
	public ResponseEntity<DogVolunteerCallDTO> findDogVolunteerCall(@PathVariable Long id) {
		Optional<DogVolunteerCall> dogVolunteerCall = dogVolunteerCallService.findById(id);
		return dogVolunteerCall.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(DogVolunteerCall.convert(dogVolunteerCall.get()));
	}

	@Operation(summary = "Поиск всех открытых запросов на консультацию",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Запрос(ы) найден(ы)"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Запрос(ы) в БД не найден(ы)"
					)
			})
	@GetMapping("/calls")
	public ResponseEntity<List<DogVolunteerCallDTO>> findAllVolunteerCalls() {
		return ResponseEntity.ok().body(dogVolunteerCallService.findAllVolunteerCalls());
	}

	@Operation(summary = "Удаление/закрытие запроса после его обработки сотрудником",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Успешное удаление записи запроса из БД"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Введён некорректный параметр для поиска или объект не найден"
					)
			})
	@DeleteMapping("/{id}")
	public void closeVolunteerCall(@PathVariable Long id) {
		dogVolunteerCallService.closeVolunteerCall(id);
	}
}
