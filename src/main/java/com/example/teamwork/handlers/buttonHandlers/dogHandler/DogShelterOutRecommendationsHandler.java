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
@Order(18)
public class DogShelterOutRecommendationsHandler extends AbstractTelegramBotButtonHandler {

	public DogShelterOutRecommendationsHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterOutRecommendations");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Выдать список рекомендаций по транспортировке питомца
							Выдать список рекомендаций по обустройству дома для маленького питомца
							Выдать список рекомендаций по обустройству дома для взрослого питомца
							Выдать список рекомендаций по обустройству дома для питомца с ограниченными возможностями (зрение, передвижение)
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/comeBack"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
