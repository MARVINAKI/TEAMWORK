package com.example.teamwork.handlers.messageHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartHandler extends AbstractTelegramBotMessageHandler {

	public StartHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkMessage(Update update) {
		return update.message() != null && "/start".equalsIgnoreCase(update.message().text());
	}

	@Override
	public void realizationMessage(Update update) {
		String information = """
				   			Приветствуем Вас!
							Мы - волонтёры муниципального приюта для бездомных животных "Новая жизнь" города Астана
							Здесь Вы можете подарить новую жизнь одному или нескольким, большому или маленькому, но ищущему своего Человека и Дом, прекрасному питомцу!
							Выберите интересующим Вас приют!
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Собачий приют").callbackData("/dogShelter"),
				new InlineKeyboardButton("Кошачий приют").callbackData("/catShelter"));
		SendMessage sendMessage = new SendMessage(update.message().chat().id(), information);
		telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));

	}
}
