package com.example.teamwork.handlers.messageHandlers;

import com.pengrad.telegrambot.TelegramBot;

public abstract class AbstractTelegramBotMessageHandler implements TelegramBotMessageHandler {
	TelegramBot telegramBot;

	public AbstractTelegramBotMessageHandler(TelegramBot telegramBot) {
		this.telegramBot = telegramBot;
	}
}
