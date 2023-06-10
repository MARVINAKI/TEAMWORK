package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DogShelterHandler extends AbstractTelegramBotButtonHandler {

	public DogShelterHandler(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelter");
	}

	@Override
	public void realizationButton(Update update) {
		String information = """
							Вы выбрали собачий приют
							Здесь вы можете получить подробную информацию о собачем приюте
							Узнать как оформить все документы для усыновления питомца, получить информацию и рекомендации о бытовых вопросах сожительства
							Отправить форму отчёта о питомце, взятом из приюта
							Или получить помощь волонтёра, если у Вас остались какие-то нерешенные вопросы
							Если хотите выбрать другой приют наберите "/start"
				""";
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Информация о приюте").callbackData("/infoAboutDogShelter"),
				new InlineKeyboardButton("Как взять питомца из приюта").callbackData("/howToAdoptDog"));
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Прислать отчёт о питомце").callbackData("/dogShelterGetReport"),
				new InlineKeyboardButton("Вызвать волонтёра").callbackData("/dogShelterVolunteer"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}