package com.example.teamwork.handlers.buttonHandlers.catHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.example.teamwork.service.cat.CatVolunteerCallService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class CatShelterVolunteerHandler extends AbstractTelegramBotButtonHandler {

	private final CatVolunteerCallService catVolunteerCallService;

	public CatShelterVolunteerHandler(TelegramBot telegramBot, CatVolunteerCallService catVolunteerCallService) {
		super(telegramBot);
		this.catVolunteerCallService = catVolunteerCallService;
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/catShelterVolunteer");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Мы отправили запрос на консультацию с волонтёром!
							С вами скоро свяжутся через telegram!
				""";
		catVolunteerCallService.registrationVolunteerCall(update.callbackQuery().from().id());
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/catShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
