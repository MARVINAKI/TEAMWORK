package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendDocument;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * Обработчик кнопки общих рекомендаций, относящихся к питомцу, в telegram bot приюта для собак
 *
 * @author Kostya
 */
@Component
public class DogShelterOutRecommendationsHandler extends AbstractTelegramBotButtonHandler {

	@Value("${documents.file.path}")
	private String pathToFile;

	public DogShelterOutRecommendationsHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	/**
	 * Проверка на нажатие именно нашей кнопки,
	 * <b>true</b> если соответсвует и <b>false</b> если нет.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 * @return <b>true / false</b>
	 */
	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterOutRecommendations");
	}

	/**
	 * Реализация функционала нашей кнопки.
	 * Выдаёт пользователю актуальные документы c общими рекомендациями по обращению,
	 * совместному проживанию, уходом и т.д.
	 * Предлагает пользователю вернуться в предыдущее меню.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@SneakyThrows
	@Override
	public void realizationButton(Update update) {
		File file = ResourceUtils.getFile(pathToFile + "Рекомендации_к_питомцу_собачий_приют.docx");
		SendDocument sendDocument = new SendDocument(update.callbackQuery().from().id(), file);
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptDog"));
		this.telegramBot.execute(sendDocument.replyMarkup(keyboardMarkup));
	}
}
