package com.example.teamwork.handlers.messageHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class StartHandler extends AbstractTelegramBotMessageHandler {

	public StartHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkMessage(Update update) {
		return update.message() != null && update.message().text().trim().equalsIgnoreCase("/start");
	}

	@Override
	public void realizationMessage(Update update) {
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Dog shelter").callbackData("/dogShelter"),
				new InlineKeyboardButton("Cat shelter").callbackData("/catShelter"));
		SendMessage sendMessage = new SendMessage(update.message().chat().id(), "It is work!");
		telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));

	}
}
