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
@Order(10)
public class CatShelterInfoHandler extends AbstractTelegramBotButtonHandler {

	public CatShelterInfoHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/infoAboutCatShelter");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Кошачий приют Котики
							Приветсвие пользователя
							Описание о приюте
							*(К)(БД?) Выдать расписание работы приюта и адрес, схему проезда (кнопки и возможность отправки изображения со схемой)
							*(К)(БД) Контактные данные охраны для оформления пропуска на машину (кнопка и дальнейшая форма для машины)
							*(К) Общие рекомендации о технике безопасности на территории приюта (кнопка на дальнейшие инструкции)
							*(К)(БД) Принять и записать контактные данные для связи (кнопка на форму для приема контактных данных)
							*(К)(О) Если нет нужного поля, то есть возможность вызвать волонтёра (кнопка на вызов волонтёра)
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Информация о приюте").callbackData("/catShelterDescription"),
				new InlineKeyboardButton("Регистрация").callbackData("/catShelterRegistration"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Рекомендации").callbackData("/catShelterInRecommendations"),
				new InlineKeyboardButton("Обратная связь").callbackData("/catShelterFeedback"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вызвать волонтёра").callbackData("/volunteerHelp"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/comeBack"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
