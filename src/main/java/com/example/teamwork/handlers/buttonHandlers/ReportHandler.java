package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

/**
 * Обработчик кнопки "Ежедневный отчёт" в telegram bot приюта.
 *
 * @author Kostya
 */
@Component
public class ReportHandler extends AbstractTelegramBotButtonHandler {

	private String marker;

	public ReportHandler(TelegramBot telegramBot) {
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
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterGetReport")) {
			marker = update.callbackQuery().data();
			return true;
		}
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterGetReport")) {
			marker = update.callbackQuery().data();
			return true;
		}
		return false;
	}

	/**
	 * Реализация нашей кнопки.
	 * Выдаёт пользователю информацию о ежедневном отчёте.
	 * Предлагает пользователю вернуться в предыдущее меню.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@Override
	public void realizationButton(Update update) {
		String information = """
				 					Форма ежедневного отчёта.
				 	В течение месяца Вы должны присылать информацию о том, как животное чувствует себя на новом месте.
				 	В ежедневный отчёт входит следующая информация:
					- Фото животного.
					- Рацион животного.
				  	- Общее самочувствие и привыкание к новому месту.
				  	- Изменение в поведении: отказ от старых привычек, приобретение новых.
				  	Отчёт нужно присылать каждый день, ограничений в сутках по времени сдачи отчёта нет.
				""";
		if (marker.equals("/dogShelterGetReport")) {
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/dogShelter"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
		if (marker.equals("/catShelterGetReport")) {
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/dogShelter"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
	}
}
