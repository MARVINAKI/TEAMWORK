package com.example.teamwork.handlers.messageHandlers;

import com.example.teamwork.model.Feedback;
import com.example.teamwork.service.FeedbackService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(2)
public class FeedbackHandler extends AbstractTelegramBotMessageHandler {

	private final FeedbackService feedbackService;

	// group(1) - fullName
	// group(2) - phoneNumber
	// group(3) - email
	// group(4) - comments
	Pattern pattern = Pattern.compile("([а-яА-ЯЁё\\s]+)\\s+(\\d{10,11})\\s+((\\w)+@+[a-zA-Z\\d]+\\.+([a-zA-Z]{1,3}))\\s+([a-zA-Zа-яА-ЯЁё\\d\\s]+)");

	public FeedbackHandler(TelegramBot telegramBot, FeedbackService feedbackService) {
		super(telegramBot);
		this.feedbackService = feedbackService;
	}

	@Override
	public boolean checkMessage(Update update) {
		if (update.message() == null) {
			return false;
		} else {
			Matcher matcher = pattern.matcher(update.message().text());
			return update.message() != null && matcher.find();
		}
	}

	@Override
	public void realizationMessage(Update update) {
		Feedback feedback = new Feedback();
		Matcher matcher = pattern.matcher(update.message().text());
		if (matcher.find()) {
			feedback.setFullName(matcher.group(1));
			feedback.setPhoneNumber(matcher.group(2));
			feedback.setEmail(matcher.group(3));
			feedback.setComments(matcher.group(4));
			feedback.setChatId(update.message().chat().id());
			feedback.setRequestTime(LocalDateTime.now());
			feedbackService.addClientRequest(feedback);
			SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Ваш запрос принят, ожидайте звонка или сообщения");
			this.telegramBot.execute(sendMessage);
		}
	}
}
