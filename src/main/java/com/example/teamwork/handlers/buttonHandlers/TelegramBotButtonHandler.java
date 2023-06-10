package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.model.Update;

public interface TelegramBotButtonHandler {

	boolean checkButton(Update update);

	void realizationButton(Update update);
}
