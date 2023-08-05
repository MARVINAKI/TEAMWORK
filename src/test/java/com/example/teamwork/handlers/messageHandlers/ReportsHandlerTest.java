package com.example.teamwork.handlers.messageHandlers;

import com.example.teamwork.constant.Status;
import com.example.teamwork.service.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.teamwork.constant.Constant.FILENAME_JSON_PHOTO;
import static com.example.teamwork.constant.Constant.PATH_TO_JSON_FILE;
import static com.example.teamwork.handlers.UpdateForTest.getUpdate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ReportsHandlerTest {

	@InjectMocks
	private ReportsHandler handler;
	@Mock
	private TelegramBot telegramBot;
	@Mock
	private ReportService service;
	private Update updateCat;
	private Update updateDog;

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_PHOTO));
		updateCat = getUpdate(json, "/catShelterGetReport");
		updateDog = getUpdate(json, "/dogShelterGetReport");
	}

	@Test
	void checkMessage() {
		assertNotNull(updateCat.message());
		assertNotNull(updateDog.message());

		assertTrue(handler.checkMessage(updateCat, Status.CAT_REPORT));
		assertTrue(handler.checkMessage(updateDog, Status.DOG_REPORT));

	}

	@Test
	void realizationMessage() {

		assertNotNull(updateCat.message().photo());
		assertNotNull(updateCat.message().caption());
		assertNotNull(updateDog.message().photo());
		assertNotNull(updateDog.message().caption());

		PhotoSize photoSize = updateCat.message().photo()[updateCat.message().photo().length - 1];
		GetFile getFile = new GetFile(photoSize.fileId());
		assertNotNull(getFile);
	}
}