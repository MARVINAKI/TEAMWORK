package com.example.teamwork.handlers.buttonHandlers.catHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(27)
public class CatShelterVolunteerHandler extends AbstractTelegramBotButtonHandler {

	public CatShelterVolunteerHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return false;
	}

	@Override
	public void realizationButton(Update update) {

	}
}
