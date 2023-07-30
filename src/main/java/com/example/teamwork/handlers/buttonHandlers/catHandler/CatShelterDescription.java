package com.example.teamwork.handlers.buttonHandlers.catHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

/**
 * Обработчик кнопки "Описание" в telegram bot приюта для кошек
 *
 * @author Kostya
 */
@Component
public class CatShelterDescription extends AbstractTelegramBotButtonHandler {

	@Value("${image.file.path}")
	private String pathToFile;

	public CatShelterDescription(TelegramBot telegramBot) {
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
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterDescription");
	}

	/**
	 * Реализация нашей кнопки.
	 * Выдаёт пользователю график работы приюта и адрес, текстовый, а также дополнительно в
	 * виде картинки/схемы.
	 * Предлагает пользователю в предыдущее меню.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@SneakyThrows
	@Override
	public void realizationButton(Update update) {
		String information = """
							График работы приюта: ежедневно, с 9:00 по 20:00, без перерыва
							Адрес приюта: г.Астана, 4-й Рощинский проезд, 19с4
				""";
		File file = ResourceUtils.getFile(pathToFile + "AddressCatShelter.jpg");
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/comeBack"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		SendPhoto sendPhoto = new SendPhoto(update.callbackQuery().from().id(), file);
		this.telegramBot.execute(sendPhoto);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
