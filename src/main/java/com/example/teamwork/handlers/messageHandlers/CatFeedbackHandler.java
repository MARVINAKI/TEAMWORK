package com.example.teamwork.handlers.messageHandlers;

import com.example.teamwork.constant.Status;
import com.example.teamwork.model.CatFeedback;
import com.example.teamwork.service.cat.CatFeedbackService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CatFeedbackHandler implements MessageWithStatusHandler {

	private final TelegramBot telegramBot;
	private final CatFeedbackService catFeedbackService;

	private final Pattern pattern = Pattern.compile("([а-яА-ЯЁё\\s]+)\\s+(\\d{10,11})\\s+((\\w)+@+[a-zA-Z\\d]+\\.+([a-zA-Z]{1,3}))\\s+([a-zA-Zа-яА-ЯЁё\\d\\s]+)");

	public CatFeedbackHandler(TelegramBot telegramBot, CatFeedbackService catFeedbackService) {
		this.telegramBot = telegramBot;
		this.catFeedbackService = catFeedbackService;
	}

	@Override
	public boolean checkMessage(Update update, Status status) {
		if (update.message() != null && status.getDescription().equals("/catShelterFeedback")) {
			Matcher matcher = pattern.matcher(update.message().text());
			return matcher.find();
		}
		return false;
	}

	@Override
	public void realizationMessage(Update update) {
		Matcher matcher = pattern.matcher(update.message().text());
		if (matcher.find()) {
			CatFeedback catFeedback = new CatFeedback();
			catFeedback.setFullName(matcher.group(1));
			catFeedback.setPhoneNumber(matcher.group(2));
			catFeedback.setEmail(matcher.group(3));
			catFeedback.setComments(matcher.group(4));
			catFeedback.setChatId(update.message().chat().id());
			catFeedback.setRequestTime(LocalDateTime.now());
			catFeedbackService.addClientRequest(catFeedback);
			SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Ваш запрос принят , ожидайте звонка или сообщения...мяу!");
			this.telegramBot.execute(sendMessage);
		} else {
			this.telegramBot.execute(new SendMessage(update.message().chat().id(), "Неверный формат сообщения (Пример: ФИО Телефон Почта Комментарий)...мяу!"));
		}
	}
}
