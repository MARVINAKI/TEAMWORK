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
@Order(3)
public class DogShelterPetTransferHandler extends AbstractTelegramBotButtonHandler {

	public DogShelterPetTransferHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/howToAdoptDog");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Приют для собак Пёсики
							Приветсвие пользователя
							*(К) Правила знакомства с животными до того, как забрать его из приюта (кнопка на инструкции)
							*(К) Возможность выдать список документов, необходимых для того, чтобы взять питомца из приюта (ссылки для скачивания документов или сразу файлы)
							*(К) Список рекомендаций по траспортировке питомцев (кнопка на инструкции)
							*(К) Список рекомендаций по обустройству дома для питомцев (маленьких, взрослых, с ограниченными возможностями) (кнопка на инструкции)
							*(К) Список рекомендаций кинолога (ТОЛЬКО ДЛЯ СОБАК, кнопка на инструкции)
							*(К)(БД) Список рекомендуемых кинологов (ТОЛЬКО ДЛЯ СОБАК, кнопка на инструкции)
							*(К)(О) Список возможных причин отказа в выдаче питомца (кнопка на инструкции)
							*(К)(БД) Принять и записать контактные данные для связи (кнопка на форму для приема контактных данных)
							*(К)(О) Если нет нужного поля, то есть возможность вызвать волонтёра (кнопка на вызов волонтёра)
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Правила").callbackData("/dogShelterRules"),
				new InlineKeyboardButton("Документы").callbackData("/dogShelterDocuments"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Рекомендации").callbackData("/dogShelterOutRecommendations"),
				new InlineKeyboardButton("Кинологи").callbackData("/cynologists"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Причины отказа").callbackData("/dogShelterReasonForRefusal"),
				new InlineKeyboardButton("Обратная связь").callbackData("/dogShelterFeedback"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вызвать волонтёра").callbackData("/dogShelterVolunteer"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/dogShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
