package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatFeedbackDTO;
import com.example.teamwork.service.cat.CatFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catFeedback")
public class CatFeedbackController {

	private final CatFeedbackService catFeedbackService;

	public CatFeedbackController(CatFeedbackService catFeedbackService) {
		this.catFeedbackService = catFeedbackService;
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
	public ResponseEntity<List<CatFeedbackDTO>> findAllOpenClientRequests() {
		return ResponseEntity.ok().body(catFeedbackService.findOpenRequests());
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
		catFeedbackService.closeProcessedRequest(id);
	}
}
