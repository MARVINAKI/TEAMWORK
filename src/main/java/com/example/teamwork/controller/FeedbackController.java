package com.example.teamwork.controller;

import com.example.teamwork.DTO.FeedbackDTO;
import com.example.teamwork.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возможность просмотреть открытые запросы по шаблону "Обратная связь" и
 * закрыть запрос, если он обработан волонтёром
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

	private final FeedbackService feedbackService;

	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
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
	@GetMapping("/getAll")
	public ResponseEntity<List<FeedbackDTO>> getAllClientsRequest() {

		return ResponseEntity.ok().body(feedbackService.findOpenRequests());
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
	@DeleteMapping("/closeRequest")
	public ResponseEntity<Void> closeProcessedRequest(Long id) {
		feedbackService.closeProcessedRequest(id);
		return ResponseEntity.ok().build();
	}
}
