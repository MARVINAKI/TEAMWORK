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
class PetTransferHandlerTest {

	@InjectMocks
	private PetTransferHandler handler;
	@Mock
	private TelegramBot telegramBot;
	private Update updateCat;
	private Update updateDog;
	private final String information = """
						Приветсвуем Вас!
						В данном разделе мы подготовили для Вас несколько полезных вещей,
						таких как свод правил, рекомендованных до того как забрать питомца из приюта,
						общих рекомендаций по обращению и сожительству с питомцем, информацию по кинологам (для собачего приюта),
						а также список необходимых документов и шаблоны бланков.
						Если у Вас остались нерешенные вопросы, Вы всегда можете обратиться за помощью
						волонтёров.
			""";

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_DATA));
		updateCat = getUpdate(json, "/howToAdoptCat");
		updateDog = getUpdate(json, "/howToAdoptDog");
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

		assertEquals(updateCat.callbackQuery().data(), "/howToAdoptCat");
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

		assertEquals(updateDog.callbackQuery().data(), "/howToAdoptDog");
		assertEquals(sendMessage.getParameters().get("chat_id"), BTN_FROM_ID);
		assertEquals(sendMessage.getParameters().get("text"), information);
	}

}