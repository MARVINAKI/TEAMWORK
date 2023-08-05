package com.example.teamwork.handlers.buttonHandlers;

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
 * Обработчик кнопки "Правила" в telegram bot приюта
 *
 * @author Kostya
 */
@Component
public class ShelterRulesHandler extends AbstractTelegramBotButtonHandler {

	private String marker;

	@Value("${documents.file.path}")
	private String pathToFile;

	public ShelterRulesHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	/**
	 * Проверка на нажатие именно нашей кнопки,
	 * <b>true</b> если соответсвует и <b>false</b> если нет.
	 * Уставливаем строковый указатель на принадлежность к приюту, для запуска
	 * корректной реализации обработчика
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 * @return <b>true / false</b>
	 */
	@Override
	public boolean checkButton(Update update) {
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterRules")) {
			marker = update.callbackQuery().data();
			return true;
		}
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterRules")) {
			marker = update.callbackQuery().data();
			return true;
		}
		return false;
	}

	/**
	 * Реализация функционала нашей кнопки.
	 * Выдаёт пользователю актуальные документы.
	 * Предлагает пользователю вернуться в предыдущее меню.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@SneakyThrows
	@Override
	public void realizationButton(Update update) {
		if (marker.equals("/dogShelterRules")) {
			File file = ResourceUtils.getFile(pathToFile + "Правила по собачьему приюту.docx");
			SendDocument sendDocument = new SendDocument(update.callbackQuery().from().id(), file);
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptDog"));
			this.telegramBot.execute(sendDocument.replyMarkup(keyboardMarkup));
		}
		if (marker.equals("/catShelterRules")) {
			File file = ResourceUtils.getFile(pathToFile + "Правила по кошачьему приюту.docx");
			SendDocument sendDocument = new SendDocument(update.callbackQuery().from().id(), file);
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptCat"));
			this.telegramBot.execute(sendDocument.replyMarkup(keyboardMarkup));
		}
	}
}
