package com.example.teamwork.handlers.buttonHandlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
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
class DocumentsHandlerTest {

	@InjectMocks
	private DocumentsHandler handler;
	@Mock
	private TelegramBot telegramBot;

	private Update updateCat;
	private Update updateDog;

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_DATA));
		updateCat = getUpdate(json, "/catShelterDocuments");
		updateDog = getUpdate(json, "/dogShelterDocuments");
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

		ArgumentCaptor<SendDocument> argumentCaptor = ArgumentCaptor.forClass(SendDocument.class);

		verify(telegramBot, times(3)).execute(argumentCaptor.capture());

		SendDocument sendDocument = argumentCaptor.getValue();

		assertEquals(updateCat.callbackQuery().data(), "/catShelterDocuments");
		assertEquals(sendDocument.getParameters().get("chat_id"), BTN_FROM_ID);
		assertNull(sendDocument.getParameters().get("text"));

	}

	@Test
	void realizationButtonDogTest() {
		handler.checkButton(updateDog);
		handler.realizationButton(updateDog);

		ArgumentCaptor<SendDocument> argumentCaptor = ArgumentCaptor.forClass(SendDocument.class);

		verify(telegramBot, times(3)).execute(argumentCaptor.capture());

		SendDocument sendDocument = argumentCaptor.getValue();

		assertEquals(updateDog.callbackQuery().data(), "/dogShelterDocuments");
		assertEquals(sendDocument.getParameters().get("chat_id"), BTN_FROM_ID);
		assertNull(sendDocument.getParameters().get("text"));

	}
}