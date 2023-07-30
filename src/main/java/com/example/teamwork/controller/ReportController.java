package com.example.teamwork.controller;

import com.example.teamwork.DTO.ReportDTO;
import com.example.teamwork.model.Report;
import com.example.teamwork.service.dog.DogRegisterService;
import com.example.teamwork.service.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report")
public class ReportController {

	private final TelegramBot telegramBot;
	private final ReportService reportService;
	private final DogRegisterService dogRegisterService;

	public ReportController(TelegramBot telegramBot, ReportService reportService, DogRegisterService dogRegisterService) {
		this.telegramBot = telegramBot;
		this.reportService = reportService;
		this.dogRegisterService = dogRegisterService;
	}

	@Operation(summary = "Изменение статуса отчёта после проверки")
	@PutMapping("/id={id}/status={status}")
	public void changeStatusOfReport(@PathVariable Long id, @PathVariable Boolean status) {
		Optional<Report> report = reportService.findById(id);
		Long chatId = report.orElseThrow(() -> new NotFoundException("Объект с id=" + id + " не найден")).getChatId();
		LocalDateTime dateTime = report.orElseThrow(NullPointerException::new).getDispatchTime();
		if (status) {
			reportService.updateStatus(id, true);
			dogRegisterService.updateReportsDate(chatId, dateTime);
		} else {
			SendMessage sendMessage = new SendMessage(chatId, """
									Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо.
									Пожалуйста, подойди ответственнее к этому занятию.
									В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного
					""");
			telegramBot.execute(sendMessage);
			reportService.deleteReport(id);
		}

	}

	@Operation(summary = "Поиск всех отчётов от определенного усыновителя")
	@GetMapping("/reports/chat_id={chatId}")
	public ResponseEntity<List<ReportDTO>> findAllByChatId(@PathVariable Long chatId) {
		return ResponseEntity.ok(reportService.findAllByChatId(chatId));
	}

	@Operation(summary = "Поиск по идентификационному номеру")
	@GetMapping("/id={id}")
	public ResponseEntity<ReportDTO> findById(@PathVariable Long id) {
		Optional<Report> report = reportService.findById(id);
		return report.isEmpty() ?
				ResponseEntity.notFound().header("Error", "Object not found in DB").build() :
				ResponseEntity.ok(Report.convert(report.get()));
	}

	@Operation(summary = "Удаление отчёта")
	@DeleteMapping("/id={id}")
	public void deleteReport(@PathVariable Long id) {
		reportService.deleteReport(id);
	}
}
