package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogVolunteerDTO;
import com.example.teamwork.model.DogVolunteer;
import com.example.teamwork.service.dog.DogVolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возможность добавить, найти или удалить волонтёра в/из БД собачего приюта.
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/dog_volunteer")
public class DogVolunteerController {

	private final DogVolunteerService dogVolunteerService;

	public DogVolunteerController(DogVolunteerService dogVolunteerService) {
		this.dogVolunteerService = dogVolunteerService;
	}

	@Operation(summary = "Добавление волонтёра в БД собачего приюта",
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
	@PostMapping("/full_name={fullName}")
	public void addDogVolunteer(@PathVariable String fullName) {
		dogVolunteerService.addDogVolunteer(fullName);
	}

	@Operation(summary = "Поиск всех волонтёров в БД",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Волонтёр(ы) найден(ы)"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Объект(ы) в БД не найден(ы)"
					)
			})
	@GetMapping("/volunteers")
	public ResponseEntity<List<DogVolunteerDTO>> findAllDogsVolunteer() {
		return ResponseEntity.ok(dogVolunteerService.findAll());
	}

	@Operation(summary = "Поиск волонтёра в БД по id",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Волонтёр найден"
					),
					@ApiResponse(
							responseCode = "500",
							description = "Введён некорректный параметр для поиска или объект не найден"
					)
			})
	@GetMapping("/{id}")
	public ResponseEntity<DogVolunteerDTO> findDogVolunteer(@PathVariable Long id) {
		Optional<DogVolunteer> dogVolunteer = dogVolunteerService.findById(id);
		return dogVolunteer.isEmpty() ? ResponseEntity.notFound().header("Error", "Object not found in DB").build() : ResponseEntity.ok(DogVolunteer.convert(dogVolunteer.get()));
	}

	@Operation(summary = "Удаление волонтёра из БД собачего приюта",
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
	public void deleteDogVolunteer(@PathVariable Long id) {
		dogVolunteerService.deleteDogVolunteer(id);
	}
}
