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

	/**
	 * Обработчик кнопки "Как забрать питомца из приюта" в telegram bot приюта для кошек
	 *
	 * @author Kostya
	 */
	public CatShelterPetTransferHandler(TelegramBot telegramBot) {
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
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/howToAdoptCat");
	}

	/**
	 * Реализация функционала нашей кнопки.
	 * Несёт больше информацилнный характер и выполняет транзитные функции
	 *
	 * @param update сообщение в telegram bot от пользователя.
	 */
	@Override
	public void realizationButton(Update update) {
		String information = """
							Приветсвуем Вас!
							В данном разделе мы подготовили для Вас несколько полезных вещей,
							таких как свод правил, рекомендованных до того как забрать питомца из приюта,
							общих рекомендаций по обращению и сожительству с питомцем,
							а также список необходимых документов и шаблоны бланков.
							Если у Вас остались нерешенные вопросы, Вы всегда можете обратиться за помощью
							волонтёров.
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
				new InlineKeyboardButton("Вернуться назад").callbackData("/catShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
