package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Component
@Order(17)
public class DogShelterDocumentsHandler extends AbstractTelegramBotButtonHandler {

	@Value("${documents.file.path}")
	private String pathToDocs;

	public DogShelterDocumentsHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterDocuments");
	}

	@SneakyThrows
	@Override
	public void realizationButton(Update update) {
		File file1 = ResourceUtils.getFile(pathToDocs + "Бланк_1_собачий_приют.docx");
		File file2 = ResourceUtils.getFile(pathToDocs + "Бланк_2_собачий_приют.docx");
		File file3 = ResourceUtils.getFile(pathToDocs + "Список_документов_собачий_приют.docx");
		SendDocument sendDocument1 = new SendDocument(update.callbackQuery().from().id(), file1);
		SendDocument sendDocument2 = new SendDocument(update.callbackQuery().from().id(), file2);
		SendDocument sendDocument3 = new SendDocument(update.callbackQuery().from().id(), file3);
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptDog"));
		this.telegramBot.execute(sendDocument1);
		this.telegramBot.execute(sendDocument2);
		this.telegramBot.execute(sendDocument3.replyMarkup(keyboardMarkup));
	}
}
