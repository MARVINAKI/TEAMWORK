package com.example.teamwork.handlers.messageHandlers;

import com.example.teamwork.constant.Status;
import com.example.teamwork.model.Report;
import com.example.teamwork.service.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Component
public class ReportsHandler implements MessageWithStatusHandler {

	private String marker;
	private final TelegramBot telegramBot;
	private final ReportService reportService;
	@Value("${photo.file.path}")
	private String pathForPhoto;

	public ReportsHandler(TelegramBot telegramBot, ReportService reportService) {
		this.telegramBot = telegramBot;
		this.reportService = reportService;
	}

	@Override
	public boolean checkMessage(Update update, Status status) {
		if (update.message() != null && status.getDescription().equals("/dogShelterGetReport")) {
			marker = status.getDescription();
			return true;
		}
		if (update.message() != null && status.getDescription().equals("/catShelterGetReport")) {
			marker = status.getDescription();
			return true;
		}
		return false;
	}

	@SneakyThrows
	@Override
	public void realizationMessage(Update update) {
		Long chatId = update.message().chat().id();
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(new InlineKeyboardButton("Попробовать снова")
				.callbackData(marker.equals("/dogShelterGetReport")
						? "/dogShelterGetReport"
						: "/catShelterGetReport"));

		if (update.message().photo() != null && update.message().caption() != null) {
			PhotoSize photoSize = update.message().photo()[update.message().photo().length - 1];
			GetFile getFile = new GetFile(photoSize.fileId());
			GetFileResponse getFileResponse = telegramBot.execute(getFile);
			if (getFileResponse.isOk()) {
				String fileName = StringUtils.getFilename(getFileResponse.file().filePath());
				byte[] file = telegramBot.getFileContent(getFileResponse.file());
				Files.write(Path.of(pathForPhoto + fileName), file);
				Report report = new Report();
				report.setChatId(chatId);
				report.setFileName(fileName);
				report.setDescription(update.message().caption());
				report.setDispatchTime(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
				reportService.addReport(report);
				this.telegramBot.execute(new SendMessage(chatId, "Отчёт принят и отправлен на проверку! Спасибо!"));
			} else {
				this.telegramBot.execute(new SendMessage(chatId, "Проблема с обработкой фотографии, попробуйте ещё раз или обратитесь к волонтёрам приюта. Спасибо!"));
			}
		} else if (update.message().photo() != null && update.message().caption() == null) {
			this.telegramBot.execute(new SendMessage(chatId, "Отсутствует информация о питомце!").replyMarkup(keyboardMarkup));
		} else {
			this.telegramBot.execute(new SendMessage(chatId, "Отсутствует фото питомца!").replyMarkup(keyboardMarkup));
		}
	}
}
