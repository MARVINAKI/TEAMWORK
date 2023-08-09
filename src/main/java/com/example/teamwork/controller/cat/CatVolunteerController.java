package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatVolunteerDTO;
import com.example.teamwork.model.CatVolunteer;
import com.example.teamwork.service.cat.CatVolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catVolunteer")
public class CatVolunteerController {

	private final CatVolunteerService catVolunteerService;

	public CatVolunteerController(CatVolunteerService catVolunteerService) {
		this.catVolunteerService = catVolunteerService;
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
	public void addCatVolunteer(@PathVariable String fullName) {
		catVolunteerService.addCatVolunteer(fullName);
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
	public ResponseEntity<List<CatVolunteerDTO>> findAllCatsVolunteer() {
		return ResponseEntity.ok().body(catVolunteerService.findAll());
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
	public ResponseEntity<CatVolunteerDTO> findCatVolunteer(@PathVariable Long id) {
		Optional<CatVolunteer> catVolunteer = catVolunteerService.findById(id);
		return catVolunteer.isEmpty() ? ResponseEntity.notFound().header("Error", "Object not found in DB").build() : ResponseEntity.ok(CatVolunteer.convert(catVolunteer.get()));
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
	public void deleteCatVolunteer(@PathVariable Long id) {
		catVolunteerService.deleteCatVolunteer(id);
	}
}
