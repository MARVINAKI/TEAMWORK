package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class DogShelterReasonForRefusalHandler extends AbstractTelegramBotButtonHandler {

	public DogShelterReasonForRefusalHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterReasonForRefusal");
	}

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
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/howToAdoptDog"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
