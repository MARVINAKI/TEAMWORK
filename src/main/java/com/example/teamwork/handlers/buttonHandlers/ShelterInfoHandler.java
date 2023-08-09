package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

/**
 * Обработчик кнопки информации о приюте в telegram bot
 *
 * @author Kostya
 */
@Component
public class ShelterInfoHandler extends AbstractTelegramBotButtonHandler {

	private String marker;

	public ShelterInfoHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	/**
	 * Проверка на нажатие именно нашей кнопки,
	 * <b>true</b> если соответсвует и <b>false</b> если нет.
	 * Уставливаем строковый указатель на принадлежность к приюту, для запуска
	 * корректной реализации обработчика
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 * @return <b>true / false</b>
	 */
	@Override
	public boolean checkButton(Update update) {
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/infoAboutDogShelter")) {
			marker = update.callbackQuery().data();
			return true;
		}
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/infoAboutCatShelter")) {
			marker = update.callbackQuery().data();
			return true;
		}
		return false;
	}

	/**
	 * Реализация функционала нашей кнопки.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@Override
	public void realizationButton(Update update) {
		String information = """
				   			Выберите интресующий Вас раздел:
				Описание - узнать график работы, адрес приюта, схема проезда;
				Регистрация - получить контактные данные охраны для оформления пропуска на машину;
				Рекомендации - ознакомиться с общими рекомендациями о технике безопасности при нахождении на территории приюта;
				Обратная связь - вы можете оставить свои данные для того чтобы мы связались с вами;
				Вызвать волонтёра - если Вы не смогли найти ответы на свои вопросы, то можно позвать волонтёра.
				""";
		if (marker.equals("/infoAboutDogShelter")) {
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
		if (marker.equals("/infoAboutCatShelter")) {
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Описание").callbackData("/catShelterDescription"),
					new InlineKeyboardButton("Регистрация").callbackData("/catShelterRegistration"));
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Рекомендации").callbackData("/catShelterInRecommendations"),
					new InlineKeyboardButton("Обратная связь").callbackData("/catShelterFeedback"));
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Вызвать волонтёра").callbackData("/catShelterVolunteer"));
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Вернуться назад").callbackData("/catShelter"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
	}
}
