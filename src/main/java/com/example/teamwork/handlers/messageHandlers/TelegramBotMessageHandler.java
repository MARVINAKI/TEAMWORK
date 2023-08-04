package com.example.teamwork.handlers.messageHandlers;

import com.pengrad.telegrambot.model.Update;

public interface TelegramBotMessageHandler {

	boolean checkMessage(Update update);

	void realizationMessage(Update update);
}
