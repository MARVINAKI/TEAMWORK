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
@Order(14)
public class CatShelterPetTransferHandler extends AbstractTelegramBotButtonHandler {

	public CatShelterPetTransferHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/howToAdoptCat");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Кошачий приют Котики
							Приветсвие пользователя
							*(К) Правила знакомства с животными до того, как забрать его из приюта (кнопка на инструкции)
							*(К) Возможность выдать список документов, необходимых для того, чтобы взять питомца из приюта (ссылки для скачивания документов или выслать файлы в телегу)
							*(К) Список рекомендаций по траспортировке питомцев (кнопка на инструкции)
							*(К) Список рекомендаций по обустройству дома для питомцев (маленьких, взрослых, с ограниченными возможностями) (кнопка на инструкции)
							*(К) Список возможных причин отказа в выдаче питомца (кнопка на инструкции)
							*(К)(БД) Принять и записать контактные данные для связи (кнопка на форму для приема контактных данных)
							*(К)(О) Если нет нужного поля, то есть возможность вызвать волонтёра (кнопка на вызов волонтёра)
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Правила").callbackData("/catShelterRules"),
				new InlineKeyboardButton("Документы").callbackData("/catShelterDocuments"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Рекомендации").callbackData("/catShelterOutRecommendations"),
				new InlineKeyboardButton("Причины отказа").callbackData("/reasonForRefusal"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Обратная связь").callbackData("/catShelterFeedback"),
				new InlineKeyboardButton("Вызвать волонтёра").callbackData("/volunteerHelp"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/comeBack"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
