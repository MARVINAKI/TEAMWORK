package com.example.teamwork.handlers.buttonHandlers.catHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

/**
 * Обработчик кнопки "Главного меню" в telegram bot приюта для кошек
 *
 * @author Kostya
 */
@Component
public class CatShelterHandler extends AbstractTelegramBotButtonHandler {

	public CatShelterHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	/**
	 * Проверка на нажатие именно нашей кнопки,
	 * <b>true</b> если соответсвует и <b>false</b> если нет.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 * @return <b>true / false</b>
	 */
	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelter");
	}

	/**
	 * Реализация функционала нашей кнопки.
	 * Выдаёт пользователю основные пункты-возможности.
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@Override
	public void realizationButton(Update update) {
		String information = """
							Вы выбрали кошачий приют
							Здесь вы можете получить подробную информацию о кошачем приюте
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