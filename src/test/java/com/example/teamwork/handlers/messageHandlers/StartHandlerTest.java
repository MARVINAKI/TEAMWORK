package com.example.teamwork.handlers.messageHandlers;

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
class StartHandlerTest {

	@InjectMocks
	private StartHandler handler;

	@Mock
	private TelegramBot telegramBot;

	String information = """
				   			Приветствуем Вас!
							Мы - волонтёры муниципального приюта для бездомных животных "Новая жизнь" города Астана
							Здесь Вы можете подарить новую жизнь одному или нескольким, большому или маленькому, но ищущему своего Человека и Дом, прекрасному питомцу!
							Выберите интересующим Вас приют!
				""";

	private Update update;

	@SneakyThrows
	@BeforeEach
	void createUpdate() {
		String json = Files.readString(Paths.get(PATH_TO_JSON_FILE + FILENAME_JSON_TEXT));
		update = getUpdate(json, "/start");
	}

	@Test
	void checkMessage() {
		assertNotNull(update.message());

		assertTrue(handler.checkMessage(update));
	}

	@Test
	void realizationMessage() {
		handler.realizationMessage(update);

		ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);

		verify(telegramBot, times(1)).execute(argumentCaptor.capture());

		SendMessage sendMessage = argumentCaptor.getValue();

		assertEquals(sendMessage.getParameters().get("chat_id"), MSG_CHAT_ID);
		assertEquals(sendMessage.getParameters().get("text"), information);
	}
}