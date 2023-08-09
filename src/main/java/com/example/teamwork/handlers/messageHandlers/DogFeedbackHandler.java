package com.example.teamwork.handlers.messageHandlers;

import com.example.teamwork.constant.Status;
import com.example.teamwork.model.DogFeedback;
import com.example.teamwork.service.dog.DogFeedbackService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DogFeedbackHandler implements MessageWithStatusHandler {

	private final TelegramBot telegramBot;
	private final DogFeedbackService dogFeedbackService;


	/**
	 * Шаблон на проверку сообщения для обратной связи
	 * group(1) - fullName
	 * group(2) - phoneNumber
	 * group(3) - email
	 * group(4) - comments
	 */
	private final Pattern pattern = Pattern.compile("([а-яА-ЯЁё\\s]+)\\s+(\\d{10,11})\\s+((\\w)+@+[a-zA-Z\\d]+\\.+([a-zA-Z]{1,3}))\\s+([a-zA-Zа-яА-ЯЁё\\d\\s+])");

	public DogFeedbackHandler(TelegramBot telegramBot, DogFeedbackService dogFeedbackService) {
		this.telegramBot = telegramBot;
		this.dogFeedbackService = dogFeedbackService;
	}

	@Override
	public boolean checkMessage(Update update, Status status) {
		if (update.message() != null && status.getDescription().equals("/dogShelterFeedback")) {
			Matcher matcher = pattern.matcher(update.message().text());
			return matcher.find();
		}
		return false;
	}

	@Override
	public void realizationMessage(Update update) {
		Matcher matcher = pattern.matcher(update.message().text());
		if (matcher.find()) {
			DogFeedback dogFeedback = new DogFeedback();
			dogFeedback.setFullName(matcher.group(1));
			dogFeedback.setPhoneNumber(matcher.group(2));
			dogFeedback.setEmail(matcher.group(3));
			dogFeedback.setComments(matcher.group(4));
			dogFeedback.setChatId(update.message().chat().id());
			dogFeedback.setRequestTime(LocalDateTime.now());
			dogFeedbackService.addClientRequest(dogFeedback);
			SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Ваш запрос принят, ожидайте звонка или сообщения...гав!");
			this.telegramBot.execute(sendMessage);
		} else {
			this.telegramBot.execute(new SendMessage(update.message().chat().id(), "Неверный формат сообщения (Пример: ФИО Телефон Почта Комментарий)...гав!"));
		}
	}
}
