package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogFeedbackDTO;
import com.example.teamwork.service.dog.DogFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возможность просмотреть открытые запросы по шаблону "Обратная связь" и
 * закрыть запрос, если он обработан волонтёром
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/dogFeedback")
public class DogFeedbackController {

	private final DogFeedbackService dogFeedbackService;

	public DogFeedbackController(DogFeedbackService dogFeedbackService) {
		this.dogFeedbackService = dogFeedbackService;
	}

	@Operation(summary = "Поиск всех открытых запросов по шаблону \"Обратная связь\"",
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
	@GetMapping("/requests")
	public ResponseEntity<List<DogFeedbackDTO>> findAllClientsRequest() {
		List<DogFeedbackDTO> list = dogFeedbackService.findOpenRequests();
		return list.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(list);
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
	public void closeProcessedRequest(@PathVariable Long id) {
		dogFeedbackService.closeProcessedRequest(id);
	}
}
