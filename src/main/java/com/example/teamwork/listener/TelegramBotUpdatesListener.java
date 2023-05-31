package com.example.teamwork.listener;

import com.example.teamwork.handlers.buttonHandlers.TelegramBotButtonHandler;
import com.example.teamwork.handlers.messageHandlers.TelegramBotMessageHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

	private Queue<Update> session = new ArrayDeque<>();
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
					if (update.callbackQuery() != null) {
						if (update.callbackQuery().data().equals("/comeBack") && !session.isEmpty()) {
							realizationButtonHandlers(session.poll());
						} else {
							session.add(update);
						}
					}
					if (update.message() != null && update.message().text().equals("/start")) {
						session.clear();
					}
					realizationButtonHandlers(update);
					realizationMessageHandlers(update);
				});
		return UpdatesListener.CONFIRMED_UPDATES_ALL;
	}

	private void realizationMessageHandlers(Update update) {
		for (TelegramBotMessageHandler messageHandler : messageHandlers) {
			if (messageHandler.checkMessage(update)) {
				messageHandler.realizationMessage(update);
				break;
			}
		}
	}

	private void realizationButtonHandlers(Update update) {
		for (TelegramBotButtonHandler buttonHandler : buttonHandlers) {
			if (buttonHandler.checkButton(update)) {
				buttonHandler.realizationButton(update);
				break;
			}
		}
	}
}
