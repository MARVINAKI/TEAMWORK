package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

/**
 * Обработчик кнопки "Как забрать питомца из приюта" в telegram bot приюта для собак
 *
 * @author Kostya
 */
@Component
public class PetTransferHandler extends AbstractTelegramBotButtonHandler {

	private String marker;

	public PetTransferHandler(TelegramBot telegramBot) {
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
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/howToAdoptDog")) {
			marker = update.callbackQuery().data();
			return true;
		}
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/howToAdoptCat")) {
			marker = update.callbackQuery().data();
			return true;
		}
		return false;
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
							общих рекомендаций по обращению и сожительству с питомцем, информацию по кинологам (для собачего приюта),
							а также список необходимых документов и шаблоны бланков.
							Если у Вас остались нерешенные вопросы, Вы всегда можете обратиться за помощью
							волонтёров.
				""";
		if (marker.equals("/howToAdoptDog")) {
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
		if (marker.equals("/howToAdoptCat")) {
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
}
