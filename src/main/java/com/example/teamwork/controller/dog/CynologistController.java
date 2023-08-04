package com.example.teamwork.controller.dog;

import com.example.teamwork.model.Cynologist;
import com.example.teamwork.service.dog.CynologistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
					),
					@ApiResponse(
							responseCode = "400",
							description = "Параметры запроса отсутствуют или имеют некорректный формат"
					)
			})
	@PostMapping("/full_name={fullName}/experience={experience}/phone_number={phoneNumber}/email={eMail}/comments={comments}")
	public void addCynologist(@PathVariable String fullName,
							  @PathVariable Integer experience,
							  @PathVariable String phoneNumber,
							  @PathVariable String eMail,
							  @PathVariable String comments) {
		cynologistService.addCynologist(fullName, experience, phoneNumber, eMail, comments);
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
	@GetMapping("/{id}")
	public ResponseEntity<Cynologist> findCynologistById(@PathVariable Long id) {
		Optional<Cynologist> cynologist = cynologistService.findById(id);
		return cynologist.isEmpty() ? ResponseEntity.notFound().header("Error", "Object not found in DB").build() : ResponseEntity.ok(cynologist.get());
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
	public ResponseEntity<List<Cynologist>> findAllCynologists() {
		return ResponseEntity.ok(cynologistService.findAll());
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
	@DeleteMapping("/{id}")
	public void deleteCynologist(@PathVariable Long id) {
		cynologistService.deleteById(id);
	}
}
