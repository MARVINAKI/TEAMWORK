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
class ReasonForRefusalHandlerTest {

	@InjectMocks
	private ReasonForRefusalHandler handler;
	@Mock
	private TelegramBot telegramBot;
	private Update updateCat;
	private Update updateDog;

	private final String information = """
						Возможные причины отказа в выдаче питомца:
						- Причина 1;
						- Причина 2;
						- Причина 3;
						- Причина 4;
						- Причина 5;
						- Причина 6;
						- Причина 7;
			""";

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_DATA));
		updateCat = getUpdate(json, "/catShelterReasonForRefusal");
		updateDog = getUpdate(json, "/dogShelterReasonForRefusal");
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

		verify(telegramBot, times(1)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(updateCat.callbackQuery().data(), "/catShelterReasonForRefusal");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		assertEquals(sendMessage.getParameters().get("text"), information);
	}

	@Test
	void realizationButtonDogTest() {
		handler.checkButton(updateDog);
		handler.realizationButton(updateDog);

		ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

		verify(telegramBot, times(1)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(updateDog.callbackQuery().data(), "/dogShelterReasonForRefusal");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		assertEquals(sendMessage.getParameters().get("text"), information);
	}
}