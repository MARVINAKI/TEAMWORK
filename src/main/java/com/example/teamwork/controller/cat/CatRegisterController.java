package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatRegisterDTO;
import com.example.teamwork.model.Cat;
import com.example.teamwork.model.CatAdopter;
import com.example.teamwork.model.CatRegister;
import com.example.teamwork.service.cat.CatAdopterService;
import com.example.teamwork.service.cat.CatRegisterService;
import com.example.teamwork.service.cat.CatShelterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Реализованы возможности:
 * заведение нового журнала об усыновлении,
 * поиск информации по журналам,
 * изменение испытательного срока,
 * удаление журналов.
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/cat_register")
public class CatRegisterController {
	private final CatRegisterService catRegisterService;
	private final CatShelterService catShelterService;
	private final CatAdopterService catAdopterService;

	public CatRegisterController(CatRegisterService catRegisterService, CatShelterService catShelterService, CatAdopterService catAdopterService) {
		this.catRegisterService = catRegisterService;
		this.catShelterService = catShelterService;
		this.catAdopterService = catAdopterService;
	}

	@Operation(summary = "Поиск всех журналов")
	@GetMapping("/registers")
	public ResponseEntity<List<CatRegisterDTO>> findAllRegisters() {
		return ResponseEntity.ok().body(catRegisterService.findAll());
	}

	@Operation(summary = "Поиск журнала по идентификационному номеру телеграмма усыновителя")
	@GetMapping("/chat_id={chatId}")
	public ResponseEntity<CatRegisterDTO> findByChatId(@PathVariable Long chatId) {
		Optional<CatRegister> catRegister = catRegisterService.findByAdoptersChatId(chatId);
		return catRegister.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(CatRegister.convert(catRegister.get()));
	}

	@Operation(summary = "Поиск журнала по идентификационному номеру кошки")
	@GetMapping("/cat_id={catId}")
	public ResponseEntity<CatRegisterDTO> findByCatId(@PathVariable Long catId) {
		Optional<CatRegister> catRegister = catRegisterService.findByCatId(catId);
		return catRegister.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(CatRegister.convert(catRegister.get()));
	}

	@Operation(summary = "Поиск журнала по идентификационному номеру усыновителя")
	@GetMapping("/cat_adopter_id={catAdopterId}")
	public ResponseEntity<CatRegisterDTO> findByAdopterId(@PathVariable Long catAdopterId) {
		Optional<CatRegister> catRegister = catRegisterService.findByCatAdopterId(catAdopterId);
		return catRegister.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(CatRegister.convert(catRegister.get()));
	}

	@Operation(summary = "Заведение новой записи об усыновлении")
	@PostMapping("/cat_id={catId}/adopter_id={adopterId}/trial_period={trialPeriod}")
	public ResponseEntity<Void> addNewCatRegister(@PathVariable Long catId,
												  @PathVariable Long adopterId,
												  @PathVariable Integer trialPeriod) {

		Optional<CatAdopter> catAdopter = catAdopterService.findById(adopterId);
		Optional<Cat> cat = catShelterService.findById(catId);

		if (catAdopter.isEmpty() || cat.isEmpty()) {
			return ResponseEntity.badRequest().build();
		} else {
			catRegisterService.addCatRegister(new CatRegister(catAdopter.get().getChatId(), cat.get(), catAdopter.get(), trialPeriod));
			return ResponseEntity.ok().build();
		}
	}

	@Operation(summary = "Изменение испытательного срока")
	@PutMapping("/id={id}/trial_period={trialPeriod}")
	public void changeTrialPeriodOfRegister(@PathVariable Long id, @PathVariable Integer trialPeriod) {
		catRegisterService.updateTrialPeriod(id, trialPeriod);
	}

	@Operation(summary = "Удаление записи журнала об усыновлении по идентификационному номеру")
	@DeleteMapping("/id={id}")
	public void deleteRegister(@PathVariable Long id) {
		catRegisterService.deleteCatRegister(id);
	}
}
