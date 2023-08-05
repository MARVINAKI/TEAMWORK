package com.example.teamwork.handlers.buttonHandlers;

import com.example.teamwork.service.dog.CynologistService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CynologistsHandlerTest {

	@InjectMocks
	private CynologistsHandler handler;

	@Mock
	private TelegramBot telegramBot;

	@Mock
	private CynologistService service;
	private Update update;

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_DATA));
		update = getUpdate(json, "/cynologists");
	}

	@Test
	void checkButton() {
		assertNotNull(update.callbackQuery());

		assertTrue(handler.checkButton(update));
	}

	@Test
	void realizationButton() {
		handler.realizationButton(update);

		verify(service, only()).findAll();

		ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

		verify(telegramBot, times(2)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
	}
}