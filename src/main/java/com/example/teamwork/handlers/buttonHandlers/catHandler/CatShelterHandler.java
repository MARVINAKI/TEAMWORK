package com.example.teamwork.handlers.buttonHandlers.catHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(9)
public class CatShelterHandler extends AbstractTelegramBotButtonHandler {

	public CatShelterHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelter");
	}

	@Override
	public void realizationButton(Update update) {
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Информация о приюте").callbackData("/infoAboutCatShelter"),
				new InlineKeyboardButton("Как взять питомца из приюта").callbackData("/howToAdoptCat"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Прислать отчёт о питомце").callbackData("/catShelterGetReport"),
				new InlineKeyboardButton("Вызвать волонтёра").callbackData("/volunteerHelp"));
		keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/comeBack"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), "Вы выбрали приют \"Котики\"");
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}