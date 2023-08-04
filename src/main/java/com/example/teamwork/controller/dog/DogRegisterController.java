package com.example.teamwork.controller.dog;

import com.example.teamwork.DTO.dog.DogRegisterDTO;
import com.example.teamwork.model.Dog;
import com.example.teamwork.model.DogAdopter;
import com.example.teamwork.model.DogRegister;
import com.example.teamwork.service.dog.DogAdopterService;
import com.example.teamwork.service.dog.DogRegisterService;
import com.example.teamwork.service.dog.DogShelterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dog_register")
public class DogRegisterController {

	private final DogRegisterService dogRegisterService;
	private final DogShelterService dogShelterService;
	private final DogAdopterService dogAdopterService;

	public DogRegisterController(DogRegisterService dogRegisterService, DogShelterService dogShelterService, DogAdopterService dogAdopterService) {
		this.dogRegisterService = dogRegisterService;
		this.dogShelterService = dogShelterService;
		this.dogAdopterService = dogAdopterService;
	}

	@Operation(summary = "Поиск всех журналов")
	@GetMapping("/registers")
	public ResponseEntity<List<DogRegisterDTO>> findAllRegisters() {
		return ResponseEntity.ok().body(dogRegisterService.findAll());
	}

	@Operation(summary = "Поиск журнала по идентификационному номеру телеграмма усыновителя")
	@GetMapping("/chat_id={chatId}")
	public ResponseEntity<DogRegisterDTO> findByChatId(@PathVariable Long chatId) {
		Optional<DogRegister> dogRegister = dogRegisterService.findByAdoptersChatId(chatId);
		return dogRegister.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(DogRegister.convert(dogRegister.get()));
	}

	@Operation(summary = "Поиск журнала по идентификационному номеру собаки")
	@GetMapping("/dog_id={dogId}")
	public ResponseEntity<DogRegisterDTO> findByDogId(@PathVariable Long dogId) {
		Optional<DogRegister> dogRegister = dogRegisterService.findByDogId(dogId);
		return dogRegister.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(DogRegister.convert(dogRegister.get()));
	}

	@Operation(summary = "Поиск журнала по идентификационному номеру усыновителя")
	@GetMapping("/dog_adopter_id={dogAdopterId}")
	public ResponseEntity<DogRegisterDTO> findByAdopterId(@PathVariable Long dogAdopterId) {
		Optional<DogRegister> dogRegister = dogRegisterService.findByDogAdopterId(dogAdopterId);
		return dogRegister.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(DogRegister.convert(dogRegister.get()));
	}

	@Operation(summary = "Заведение новой записи об усыновлении")
	@PostMapping("/dog_id={dogId}/adopter_id={adopterId}/trial_period={trialPeriod}")
	public ResponseEntity<Void> addNewDogRegister(@PathVariable Long dogId,
												  @PathVariable Long adopterId,
												  @PathVariable Integer trialPeriod) {

		Optional<DogAdopter> dogAdopter = dogAdopterService.findById(adopterId);
		Optional<Dog> dog = dogShelterService.findById(dogId);

		if (dogAdopter.isEmpty() || dog.isEmpty()) {
			return ResponseEntity.badRequest().build();
		} else {
			dogRegisterService.addDogRegister(new DogRegister(dogAdopter.get().getChatId(), dog.get(), dogAdopter.get(), trialPeriod));
			return ResponseEntity.ok().build();
		}
	}

	@Operation(summary = "Изменение испытательного срока")
	@PutMapping("/id={id}/trial_period={trialPeriod}")
	public void changeTrialPeriodOfRegister(@PathVariable Long id, @PathVariable Integer trialPeriod) {
		dogRegisterService.updateTrialPeriod(id, trialPeriod);
	}

	@Operation(summary = "Удаление записи журнала об усыновлении по идентификационному номеру")
	@DeleteMapping("/id={id}")
	public void deleteRegister(@PathVariable Long id) {
		dogRegisterService.deleteDogRegister(id);
	}
}
