package com.example.teamwork.listener;

import com.example.teamwork.enums.Status;
import com.example.teamwork.handlers.buttonHandlers.TelegramBotButtonHandler;
import com.example.teamwork.handlers.messageHandlers.MessageWithStatusHandler;
import com.example.teamwork.handlers.messageHandlers.TelegramBotMessageHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {
	private final List<TelegramBotButtonHandler> buttonHandlers;
	private final List<TelegramBotMessageHandler> messageHandlers;
	private final List<MessageWithStatusHandler> messageWithStatusHandlers;
	private final Map<Long, Status> map = new HashMap<>();

	public TelegramBotUpdatesListener(TelegramBot telegramBot, List<TelegramBotButtonHandler> buttonHandlers, List<TelegramBotMessageHandler> messageHandlers, List<MessageWithStatusHandler> messageWithStatusHandlers) {
		this.buttonHandlers = buttonHandlers;
		this.messageHandlers = messageHandlers;
		this.messageWithStatusHandlers = messageWithStatusHandlers;
		telegramBot.setUpdatesListener(this);
	}

	@Override
	public int process(List<Update> updates) {
		updates.stream()
				.filter(update -> update.message() != null || update.callbackQuery() != null)
				.forEach(update -> {
					Status status = map.get(getChatId(update));
					realizationButtonHandlers(update);
					realizationMessageHandlers(update);
					realizationHandlerWithStatus(update, status);
				});
		return UpdatesListener.CONFIRMED_UPDATES_ALL;
	}

	/**
	 * Метод поиска и реализации нужного текстового обработчика telegram bot.
	 *
	 * @param update сообщение пользователя для обработки
	 */
	private void realizationMessageHandlers(Update update) {
		for (TelegramBotMessageHandler messageHandler : messageHandlers) {
			if (messageHandler.checkMessage(update)) {
				messageHandler.realizationMessage(update);
				return;
			}
		}
	}

	/**
	 * Метод поиска и реализации нужного обработчика кнопок telegram bot.
	 *
	 * @param update сообщение пользователя для обработки
	 */
	private void realizationButtonHandlers(Update update) {
		for (TelegramBotButtonHandler buttonHandler : buttonHandlers) {
			if (buttonHandler.checkButton(update)) {
				for (Status status : Status.values()) {
					if (update.callbackQuery().data().equals(status.getDescription())) {
						map.put(update.callbackQuery().from().id(), status);
					}
				}
				buttonHandler.realizationButton(update);
				return;
			}
		}
	}

	private void realizationHandlerWithStatus(Update update, Status status) {
		if (status != null) {
			for (MessageWithStatusHandler messageWithStatusHandler : messageWithStatusHandlers) {
				if (messageWithStatusHandler.checkMessage(update, status)) {
					messageWithStatusHandler.realizationMessage(update);
					map.remove(getChatId(update));
					return;
				}
			}
		}
	}

	private Long getChatId(Update update) {
		return update.message() != null ? update.message().chat().id() : update.callbackQuery().from().id();
	}
}
