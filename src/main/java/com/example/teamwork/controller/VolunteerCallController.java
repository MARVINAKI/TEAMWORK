package com.example.teamwork.controller;

import com.example.teamwork.DTO.VolunteerCallDTO;
import com.example.teamwork.service.VolunteerCallService;
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
 * Возможность просмотреть открытые запросы на консультацию через telegram и
 * закрыть запрос, если он обработан волонтёром
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/volunteerCall")
public class VolunteerCallController {

	private final VolunteerCallService volunteerCallService;

	public VolunteerCallController(VolunteerCallService volunteerCallService) {
		this.volunteerCallService = volunteerCallService;
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
	@GetMapping
	public ResponseEntity<VolunteerCallDTO> getVolunteerCall(Long id) {
		return ResponseEntity.ok().body(volunteerCallService.findVolunteerCall(id));
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
	@GetMapping("/volunteersCall")
	public ResponseEntity<List<VolunteerCallDTO>> getAllCalls() {
		return ResponseEntity.ok().body(volunteerCallService.findAllVolunteerCalls());
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
	@DeleteMapping("/closeVolunteerCall")
	public ResponseEntity<Void> closeVolunteerCall(Long id) {
		volunteerCallService.closeVolunteerCall(id);
		return ResponseEntity.ok().build();
	}
}
