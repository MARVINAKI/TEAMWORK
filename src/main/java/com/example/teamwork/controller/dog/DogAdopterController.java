package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogAdopterDTO;
import com.example.teamwork.model.DogAdopter;
import com.example.teamwork.service.dog.DogAdopterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dog_adopters")
public class DogAdopterController {

	private final DogAdopterService dogAdopterService;

	public DogAdopterController(DogAdopterService dogAdopterService) {
		this.dogAdopterService = dogAdopterService;
	}

	@Operation(summary = "Добавить усыновителя для собачего приюта")
	@PostMapping("/chat_id={chatId}/full_name={fullName}/phone_number{phoneNumber}")
	public ResponseEntity<Void> addDogAdopter(@PathVariable Long chatId,
											  @PathVariable String fullName,
											  @PathVariable String phoneNumber) {
		dogAdopterService.addAdopter(new DogAdopter(chatId, fullName, phoneNumber));
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Поиск всех усыновителей в собачем приюте")
	@GetMapping("/adopters")
	public ResponseEntity<List<DogAdopterDTO>> findAllDogAdopters() {
		return ResponseEntity.ok().body(dogAdopterService.findAllAdopters());
	}

	@Operation(summary = "Поиск усыновителя по имени")
	@GetMapping("/name={name}")
	public ResponseEntity<DogAdopterDTO> findDogAdopterByName(@PathVariable String name) {
		Optional<DogAdopter> dogAdopter = dogAdopterService.findByName(name);
		return dogAdopter.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(DogAdopter.convert(dogAdopter.get()));
	}

	@Operation(summary = "Поиск усыновителя по идентификационному номеру")
	@GetMapping("/id={id}")
	public ResponseEntity<DogAdopterDTO> findById(@PathVariable Long id) {
		Optional<DogAdopter> dogAdopter = dogAdopterService.findById(id);
		return dogAdopter.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(DogAdopter.convert(dogAdopter.get()));
	}

	@Operation(summary = "Удаление усыновителя из базы данных")
	@DeleteMapping("/id={id}")
	public void deleteDogAdopter(@PathVariable Long id) {
		dogAdopterService.deleteAdopter(id);
	}
}
