package com.example.teamwork.handlers.buttonHandlers.catHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class CatShelterGetReportHandler extends AbstractTelegramBotButtonHandler {

	public CatShelterGetReportHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterGetReport");
	}

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
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/dogShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
