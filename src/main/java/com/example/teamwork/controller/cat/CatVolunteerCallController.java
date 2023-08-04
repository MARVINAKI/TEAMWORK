package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatVolunteerCallDTO;
import com.example.teamwork.model.CatVolunteerCall;
import com.example.teamwork.service.cat.CatVolunteerCallService;
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
@RequestMapping("/catVolunteerCall")
public class CatVolunteerCallController {

	private final CatVolunteerCallService catVolunteerCallService;

	public CatVolunteerCallController(CatVolunteerCallService catVolunteerCallService) {
		this.catVolunteerCallService = catVolunteerCallService;
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
	public ResponseEntity<CatVolunteerCall> findCatVolunteerCall(@PathVariable Long id) {
		Optional<CatVolunteerCall> catVolunteerCall = catVolunteerCallService.findById(id);
		return catVolunteerCall.isEmpty() ? ResponseEntity.notFound().header("Error", "Object not found in DB").build()
				: ResponseEntity.ok(catVolunteerCall.get());
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
	public ResponseEntity<List<CatVolunteerCallDTO>> findAllVolunteersCall() {
		return ResponseEntity.ok().body(catVolunteerCallService.findAllVolunteerCalls());
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
		catVolunteerCallService.closeVolunteerCall(id);
	}
}
