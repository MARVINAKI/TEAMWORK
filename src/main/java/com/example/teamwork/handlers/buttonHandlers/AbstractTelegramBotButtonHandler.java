package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;

public abstract class AbstractTelegramBotButtonHandler implements TelegramBotButtonHandler {
	protected final TelegramBot telegramBot;

	public AbstractTelegramBotButtonHandler(TelegramBot telegramBot) {
		this.telegramBot = telegramBot;
	}
}
