package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

/**
 * Обработчик кнопки "Причины отказа" в telegram bot приюта.
 *
 * @author Kostya
 */
@Component
public class ReasonForRefusalHandler extends AbstractTelegramBotButtonHandler {

	private String marker;

	public ReasonForRefusalHandler(TelegramBot telegramBot) {
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
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterReasonForRefusal")) {
			marker = update.callbackQuery().data();
			return true;
		}
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterReasonForRefusal")) {
			marker = update.callbackQuery().data();
			return true;
		}
		return false;
	}

	/**
	 * Реализация нашей кнопки.
	 * Выдаёт пользователю список возможных причин отказа в выдаче питомца.
	 * Предлагает пользователю вернуться в предыдущее меню.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@Override
	public void realizationButton(Update update) {
		String information = """
							Возможные причины отказа в выдаче питомца:
							- Причина 1;
							- Причина 2;
							- Причина 3;
							- Причина 4;
							- Причина 5;
							- Причина 6;
							- Причина 7;
				""";
		if (marker.equals("/dogShelterReasonForRefusal")) {
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptDog"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
		if (marker.equals("/catShelterReasonForRefusal")) {
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptCat"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
	}
}
