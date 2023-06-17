package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.example.teamwork.service.VolunteerCallService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(26)
public class DogShelterVolunteerHandler extends AbstractTelegramBotButtonHandler {

	private final VolunteerCallService volunteerCallService;
	public DogShelterVolunteerHandler(TelegramBot telegramBot, VolunteerCallService volunteerCallService) {
		super(telegramBot);
		this.volunteerCallService = volunteerCallService;
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterVolunteer");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Мы отправили запрос на консультацию с волонтёром!
							С вами скоро свяжутся через telegram!
				""";
		volunteerCallService.registrationVolunteerCall(update.callbackQuery().from().id());
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(new InlineKeyboardButton("Вернуться назад").callbackData("/dogShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
