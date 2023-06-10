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
@Order(2)
public class DogShelterInfoHandler extends AbstractTelegramBotButtonHandler {

	public DogShelterInfoHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/infoAboutDogShelter");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
				   			Выберите интресующий Вас раздел:
				(( Описание )) - узнать график работы, адрес приюта, схема проезда;
				(( Регистрация )) - получить контактные данные охраны для оформления пропуска на машину;
				(( Рекомендации )) - ознакомиться с общими рекомендациями о технике безопасности при нахождении на территории приюта;
				(( Обратная связь )) - вы можете оставить свои данные для того чтобы мы связались с вами;
				(( Вызвать волонтёра )) - если Вы не смогли найти ответы на свои вопросы, то можно позвать волонтёра.												
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Описание").callbackData("/dogShelterDescription"),
				new InlineKeyboardButton("Регистрация").callbackData("/dogShelterRegistration"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Рекомендации").callbackData("/dogShelterInRecommendations"),
				new InlineKeyboardButton("Обратная связь").callbackData("/dogShelterFeedback"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вызвать волонтёра").callbackData("/dogShelterVolunteer"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/dogShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
