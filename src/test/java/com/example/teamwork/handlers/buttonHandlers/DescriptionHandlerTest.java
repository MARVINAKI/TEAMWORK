package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.teamwork.constant.Constant.*;
import static com.example.teamwork.handlers.UpdateForTest.getUpdate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DescriptionHandlerTest {

	@InjectMocks
	private DescriptionHandler handler;
	@Mock
	private TelegramBot telegramBot;
	private Update updateCat;
	private Update updateDog;

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_DATA));
		updateCat = getUpdate(json, "/catShelterDescription");
		updateDog = getUpdate(json, "/dogShelterDescription");
	}

	@Test
	void checkButtonTest() {
		assertNotNull(updateCat.callbackQuery());
		assertNotNull(updateDog.callbackQuery());

		assertTrue(handler.checkButton(updateCat));
		assertTrue(handler.checkButton(updateDog));
	}

	@Test
	void realizationButtonCatTest() {
		handler.checkButton(updateCat);
		handler.realizationButton(updateCat);

		ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

		verify(telegramBot, times(2)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(updateCat.callbackQuery().data(), "/catShelterDescription");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		String information = """
							График работы приюта: ежедневно, с 9:00 по 20:00, без перерыва
							Адрес приюта: г.Астана, 4-й Рощинский проезд, 19с4
				""";
		assertEquals(sendMessage.getParameters().get("text"), information);
	}

	@Test
	void realizationButtonDogTest() {
		handler.checkButton(updateDog);
		handler.realizationButton(updateDog);

		ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

		verify(telegramBot, times(2)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(updateDog.callbackQuery().data(), "/dogShelterDescription");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		String information = """
							График работы приюта: ежедневно, с 9:00 по 20:00, без перерыва
							Адрес приюта: г.Астана, ул.Александра Солженицына, 23Ас1
				""";
		assertEquals(sendMessage.getParameters().get("text"), information);
	}
}