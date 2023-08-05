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
class RegistrationHandlerTest {

	@InjectMocks
	private RegistrationHandler handler;
	@Mock
	private TelegramBot telegramBot;
	private Update updateCat;
	private Update updateDog;

	private final String information = """
			   			Вы можете зарегестрировать своё автотранспортное средство для заезда на территорию приюта.
			   			Перед заездом ознакомьтесь с рекомендациями по нахождению на территории приюта.
						Контактные данные охраны для оформления пропуска:
						8(347)111-11-11
						8(347)222-22-22
			""";

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_DATA));
		updateCat = getUpdate(json, "/catShelterRegistration");
		updateDog = getUpdate(json, "/dogShelterRegistration");
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

		assertEquals(updateCat.callbackQuery().data(), "/catShelterRegistration");
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

		assertEquals(updateDog.callbackQuery().data(), "/dogShelterRegistration");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		assertEquals(sendMessage.getParameters().get("text"), information);
	}
}