package com.example.teamwork.listener;

import com.example.teamwork.handlers.buttonHandlers.TelegramBotButtonHandler;
import com.example.teamwork.handlers.messageHandlers.TelegramBotMessageHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {
	private final TelegramBot telegramBot;
	private final List<TelegramBotButtonHandler> buttonHandlers;
	private final List<TelegramBotMessageHandler> messageHandlers;

	public TelegramBotUpdatesListener(TelegramBot telegramBot, List<TelegramBotButtonHandler> buttonHandlers, List<TelegramBotMessageHandler> messageHandlers) {
		this.telegramBot = telegramBot;
		this.buttonHandlers = buttonHandlers;
		this.messageHandlers = messageHandlers;
		this.telegramBot.setUpdatesListener(this);
	}

	@Override
	public int process(List<Update> updates) {
		updates.stream()
				.filter(update -> update.message() != null || update.callbackQuery() != null)
				.forEach(update -> {
					realizationButtonHandlers(update);
					realizationMessageHandlers(update);
				});
		return UpdatesListener.CONFIRMED_UPDATES_ALL;
	}

	/**
	 * Метод поиска и реализации нужного текстового обработчика telegram bot.
	 * @param update сообщение пользователя для обработки
	 */
	private void realizationMessageHandlers(Update update) {
		for (TelegramBotMessageHandler messageHandler : messageHandlers) {
			if (messageHandler.checkMessage(update)) {
				messageHandler.realizationMessage(update);
				break;
			}
		}
	}

	/**
	 * Метод поиска и реализации нужного обработчика кнопок telegram bot.
	 * @param update сообщение пользователя для обработки
	 */
	private void realizationButtonHandlers(Update update) {
		for (TelegramBotButtonHandler buttonHandler : buttonHandlers) {
			if (buttonHandler.checkButton(update)) {
				buttonHandler.realizationButton(update);
				break;
			}
		}
	}
}
