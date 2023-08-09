package com.example.teamwork.handlers.buttonHandlers;

import com.example.teamwork.service.cat.CatVolunteerCallService;
import com.example.teamwork.service.dog.DogVolunteerCallService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

/**
 * Обработчик кнопки вызова волонтёра в telegram bot приюта.
 *
 * @author Kostya
 */
@Component
public class VolunteerHandler extends AbstractTelegramBotButtonHandler {

	private String marker;

	private final DogVolunteerCallService dogVolunteerCallService;
	private final CatVolunteerCallService catVolunteerCallService;
	public VolunteerHandler(TelegramBot telegramBot, DogVolunteerCallService dogVolunteerCallService, CatVolunteerCallService catVolunteerCallService) {
		super(telegramBot);
		this.dogVolunteerCallService = dogVolunteerCallService;
		this.catVolunteerCallService = catVolunteerCallService;
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
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterVolunteer")) {
			marker = update.callbackQuery().data();
			return true;
		}
		if (update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterVolunteer")) {
			marker = update.callbackQuery().data();
			return true;
		}
		return false;
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Мы отправили запрос на консультацию с волонтёром!
							С вами скоро свяжутся через telegram!
				""";
		if (marker.equals("/dogShelterVolunteer")) {
			dogVolunteerCallService.registrationVolunteerCall(update.callbackQuery().from().id());
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/dogShelter"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
		if (marker.equals("/catShelterVolunteer")) {
			catVolunteerCallService.registrationVolunteerCall(update.callbackQuery().from().id());
			InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
			keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/catShelter"));
			SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
			this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
		}
	}
}
