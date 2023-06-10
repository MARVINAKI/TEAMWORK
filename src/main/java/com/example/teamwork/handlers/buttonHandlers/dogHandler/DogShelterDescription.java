package com.example.teamwork.handlers.buttonHandlers.dogHandler;

import com.example.teamwork.handlers.buttonHandlers.AbstractTelegramBotButtonHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Component
@Order(4)
public class DogShelterDescription extends AbstractTelegramBotButtonHandler {

	@Value("${image.file.path}")
	private String pathToFile;

	public DogShelterDescription(TelegramBot telegramBot) {
		super(telegramBot);
	}

	@Override
	public boolean checkButton(Update update) {
		return update.callbackQuery() != null && update.callbackQuery().data().equals("/dogShelterDescription");
	}

	@SneakyThrows
	@Override
	public void realizationButton(Update update) {
		String information = """
							График работы приюта: ежедневно, с 9:00 по 20:00
							Адрес приюта: г.Астана, ул.Александра Солженицына, 23Ас1
							ДОПОЛНИТЕЛЬНО: описание нюнсов, объезды, если такое имеется...
				""";
		File image = ResourceUtils.getFile(pathToFile + "AddressDogShelter.jpg");
		InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
		keyboardMarkup.addRow(
				new InlineKeyboardButton("Вернуться назад").callbackData("/infoAboutDogShelter"));
		SendMessage sendMessage = new SendMessage(update.callbackQuery().from().id(), information);
		SendPhoto sendPhoto = new SendPhoto(update.callbackQuery().from().id(), image);
		this.telegramBot.execute(sendPhoto);
		this.telegramBot.execute(sendMessage.replyMarkup(keyboardMarkup));
	}
}
