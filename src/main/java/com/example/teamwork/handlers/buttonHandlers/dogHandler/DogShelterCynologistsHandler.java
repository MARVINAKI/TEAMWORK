package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.example.teamwork.model.Cynologist;
import com.example.teamwork.service.dog.CynologistService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Component
public class DogShelterCynologistsHandler extends AbstractTelegramBotButtonHandler {

	@Value("${documents.file.path}")
	private String pathToDocs;
	private final CynologistService cynologistService;

	public DogShelterCynologistsHandler(TelegramBot telegramBot, CynologistService cynologistService) {
		super(telegramBot);
		this.cynologistService = cynologistService;
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/cynologists");
	}

	@SneakyThrows
	@Override
	public void realizationButton(Update update) {
		File file = ResourceUtils.getFile(pathToDocs + "Рекомендации_кинолога.docx");
		StringBuilder cynologists = new StringBuilder("\n");
		for (Cynologist cynologist : cynologistService.findAll()) {
			cynologists.append("\n" + "ФИО: ")
					.append(cynologist.getFullName()).append("\n").append("Опыт работы: ")
					.append(cynologist.getExperience()).append("\n").append("Номер телефона: ")
					.append(cynologist.getPhoneNumber()).append("\n").append("Электронная почта: ")
					.append(cynologist.getEMail()).append("\n").append("Комментарии: ")
					.append(cynologist.getComments()).append("\n -----------------------------------------");
		}
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptDog"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), cynologists.toString());
		SendDocument sendDocument = new SendDocument(update.callbackQuery().from().id(), file);
		this.telegramBot.execute(sendDocument);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
