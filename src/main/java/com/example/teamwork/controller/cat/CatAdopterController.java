package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatAdopterDTO;
import com.example.teamwork.model.CatAdopter;
import com.example.teamwork.service.cat.CatAdopterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер предназначен для сотрудников приюта.
 * Возможность добавления, удалении и просмотра информации
 * об усыновителях
 *
 * @author Kostya
 */
@RestController
@RequestMapping("/cat_adopters")
public class CatAdopterController {

	private final CatAdopterService catAdopterService;

	public CatAdopterController(CatAdopterService catAdopterService) {
		this.catAdopterService = catAdopterService;
	}

	@Operation(summary = "Добавить усыновителя для кошачьего приюта")
	@PostMapping("/chat_id={chatId}/full_name={fullName}/phone_number={phoneNumber}")
	public ResponseEntity<Void> addCatAdopter(@PathVariable Long chatId,
											  @PathVariable String fullName,
											  @PathVariable String phoneNumber) {
		catAdopterService.addAdopter(new CatAdopter(chatId, fullName, phoneNumber));
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Поиск всех усыновителей в кошачьем приюте")
	@GetMapping("/adopters")
	public ResponseEntity<List<CatAdopterDTO>> findAllCatAdopters() {
		return ResponseEntity.ok().body(catAdopterService.findAllAdopters());
	}

	@Operation(summary = "Поиск усыновителя по имени")
	@GetMapping("/name={name}")
	public ResponseEntity<CatAdopterDTO> findCatAdopterByName(@PathVariable String name) {
		Optional<CatAdopter> catAdopter = catAdopterService.findByFullName(name);
		return catAdopter.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(CatAdopter.convert(catAdopter.get()));
	}

	@Operation(summary = "Поиск усыновителя по идентификационному номеру")
	@GetMapping("/id={id}")
	public ResponseEntity<CatAdopterDTO> findById(@PathVariable Long id) {
		Optional<CatAdopter> catAdopter = catAdopterService.findById(id);
		return catAdopter.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(CatAdopter.convert(catAdopter.get()));
	}

	@Operation(summary = "Удаление усыновителя из базы данных")
	@DeleteMapping("/id={id}")
	public void deleteCatAdopter(@PathVariable Long id) {
		catAdopterService.deleteAdopter(id);
	}
}
