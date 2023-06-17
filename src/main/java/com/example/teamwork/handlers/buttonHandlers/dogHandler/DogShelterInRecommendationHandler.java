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

/**
 * Обработчик кнопки по рекомендациям ТБ в telegram bot приюта для собак
 *
 * @author Kostya
 */
@Component
@Order(6)
public class DogShelterInRecommendationHandler extends AbstractTelegramBotButtonHandler {

	@Value("${documents.file.path}")
	private String pathToFile;

	public DogShelterInRecommendationHandler(TelegramBot telegramBot) {
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
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterInRecommendations");
	}

	/**
	 * Реализация функционала нашей кнопки.
	 * Выдаёт пользователю актуальные документы по технике безопаности.
	 * Предлагает пользователю вернуться в предыдущее меню.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@SneakyThrows
	@Override
	public void realizationButton(Update update) {
		File file = ResourceUtils.getFile(pathToFile + "Рекомендации по ТБ в собачьем приюте.docx");
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/infoAboutDogShelter"));
		SendDocument sendDocument = new SendDocument(update.callbackQuery().from().id(), file);
		this.telegramBot.execute(sendDocument.replyMarkup(keyboardMarkup));
	}
}
