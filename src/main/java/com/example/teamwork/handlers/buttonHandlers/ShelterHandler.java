package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

/**
 * Обработчик кнопки "Главного меню" в telegram bot приюта
 *
 * @author Kostya
 */
@Component
public class ShelterHandler extends AbstractTelegramBotButtonHandler {

	private String marker;

	public ShelterHandler(TelegramBot telegramBot) {
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
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelter")) {
			marker = update.callbackQuery().data();
			return true;
		}
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelter")) {
			marker = update.callbackQuery().data();
			return true;
		}
		return false;
	}

	/**
	 * Реализация функционала нашей кнопки.
	 * Выдаёт пользователю основные пункты-возможности.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@Override
	public void realizationButton(Update update) {
		if (marker.equals("/dogShelter")) {
			String information = """
								Вы выбрали собачий приют
								Здесь вы можете получить подробную информацию о собачем приюте
								Узнать как оформить все документы для усыновления питомца, получить информацию и рекомендации о бытовых вопросах сожительства
								Отправить форму отчёта о питомце, взятом из приюта
								Или получить помощь волонтёра, если у Вас остались какие-то нерешенные вопросы
								Если хотите выбрать другой приют наберите "/start"
					""";
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Информация о приюте").callbackData("/infoAboutDogShelter"),
					new InlineKeyboardButton("Как взять питомца из приюта").callbackData("/howToAdoptDog"));
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Прислать отчёт о питомце").callbackData("/dogShelterGetReport"),
					new InlineKeyboardButton("Вызвать волонтёра").callbackData("/dogShelterVolunteer"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
		if (marker.equals("/catShelter")) {
			String information = """
								Вы выбрали кошачий приют
								Здесь вы можете получить подробную информацию о кошачьем приюте
								Узнать как оформить все документы для усыновления питомца, получить информацию и рекомендации о бытовых вопросах сожительства
								Отправить форму отчёта о питомце, взятом из приюта
								Или получить помощь волонтёра, если у Вас остались какие-то нерешенные вопросы
								Если хотите выбрать другой приют наберите "/start"
					""";
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Информация о приюте").callbackData("/infoAboutCatShelter"),
					new InlineKeyboardButton("Как взять питомца из приюта").callbackData("/howToAdoptCat"));
			keyboardMarkup.addRow(
					new InlineKeyboardButton("Прислать отчёт о питомце").callbackData("/catShelterGetReport"),
					new InlineKeyboardButton("Вызвать волонтёра").callbackData("/catShelterVolunteer"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
	}
}
