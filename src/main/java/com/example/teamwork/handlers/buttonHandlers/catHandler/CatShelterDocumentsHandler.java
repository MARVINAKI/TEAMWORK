package com.example.teamwork.handlers.buttonHandlers.catHandler;

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
 * Обработчик кнопки "Документы" в telegram bot приюта для кошек
 *
 * @author Kostya
 */
@Component
public class CatShelterDocumentsHandler extends AbstractTelegramBotButtonHandler {

	@Value("${documents.file.path}")
	private String pathToFile;

	public CatShelterDocumentsHandler(TelegramBot telegramBot) {
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
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterDocuments");
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
		File file1 = ResourceUtils.getFile(pathToFile + "Бланк_1_кошачий_приют.docx");
		File file2 = ResourceUtils.getFile(pathToFile + "Бланк_2_кошачий_приют.docx");
		File file3 = ResourceUtils.getFile(pathToFile + "Список_документов_кошачий_приют.docx");
		SendDocument sendDocument1 = new SendDocument(update.callbackQuery().from().id(), file1);
		SendDocument sendDocument2 = new SendDocument(update.callbackQuery().from().id(), file2);
		SendDocument sendDocument3 = new SendDocument(update.callbackQuery().from().id(), file3);
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptCat"));
		this.telegramBot.execute(sendDocument1);
		this.telegramBot.execute(sendDocument2);
		this.telegramBot.execute(sendDocument3.replyMarkup(keyboardMarkup));
	}
}
