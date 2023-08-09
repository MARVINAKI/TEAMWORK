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
class ShelterHandlerTest {

	@InjectMocks
	private ShelterHandler handler;
	@Mock
	private TelegramBot telegramBot;
	private Update updateCat;
	private Update updateDog;

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_DATA));
		updateCat = getUpdate(json, "/catShelter");
		updateDog = getUpdate(json, "/dogShelter");
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
		String information = """
							Вы выбрали кошачий приют
							Здесь вы можете получить подробную информацию о кошачьем приюте
							Узнать как оформить все документы для усыновления питомца, получить информацию и рекомендации о бытовых вопросах сожительства
							Отправить форму отчёта о питомце, взятом из приюта
							Или получить помощь волонтёра, если у Вас остались какие-то нерешенные вопросы
							Если хотите выбрать другой приют наберите "/start"
				""";
		handler.checkButton(updateCat);
		handler.realizationButton(updateCat);

		ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

		verify(telegramBot, times(1)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(updateCat.callbackQuery().data(), "/catShelter");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		assertEquals(sendMessage.getParameters().get("text"), information);
	}

	@Test
	void realizationButtonDogTest() {
		String information = """
							Вы выбрали собачий приют
							Здесь вы можете получить подробную информацию о собачем приюте
							Узнать как оформить все документы для усыновления питомца, получить информацию и рекомендации о бытовых вопросах сожительства
							Отправить форму отчёта о питомце, взятом из приюта
							Или получить помощь волонтёра, если у Вас остались какие-то нерешенные вопросы
							Если хотите выбрать другой приют наберите "/start"
				""";
		handler.checkButton(updateDog);
		handler.realizationButton(updateDog);

		ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

		verify(telegramBot, times(1)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(updateDog.callbackQuery().data(), "/dogShelter");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		assertEquals(sendMessage.getParameters().get("text"), information);
	}
}