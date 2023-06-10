package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class DogShelterRegistrationHandler extends AbstractTelegramBotButtonHandler {

	public DogShelterRegistrationHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterRegistration");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
				   			Вы можете зарегестрировать своё автотранспортное средство для заезда на территорию приюта.
				   			Перед заездом ознакомьтесь с рекомендациями по нахождению на территории приюта.
							Контактные данные охраны для оформления пропуска:
							8(347)111-11-11
							8(347)222-22-22
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/infoAboutDogShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}