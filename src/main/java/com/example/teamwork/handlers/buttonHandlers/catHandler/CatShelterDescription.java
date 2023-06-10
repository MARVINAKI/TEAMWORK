package com.example.teamwork.handlers.buttonHandlers.catHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(11)
public class CatShelterDescription extends AbstractTelegramBotButtonHandler {

	public CatShelterDescription(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterDescription");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							График работы приюта: ежедневно, с 9:00 по 20:00
							Адрес приюта: г.Астана, ул.Александра Солженицына, 23Ас1
							https://yandex.ru/maps/213/moscow/house/ulitsa_aleksandra_solzhenitsyna_23as1/Z04YcAFjS0wAQFtvfXt1dX9lZw==/?ll=37.663297%2C55.744364&z=16.82
							ДОПОЛНИТЕЛЬНО: описание нюнсов, объезды, если такое имеется...
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/comeBack"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
