package com.example.teamwork.handlers.messageHandlers.cat;

import com.example.teamwork.enums.Status;
import com.example.teamwork.handlers.messageHandlers.MessageWithStatusHandler;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

@Component
public class CatReportHandler implements MessageWithStatusHandler {
	@Override
	public boolean checkMessage(Update update, Status status) {
		return false;
	}

	@Override
	public void realizationMessage(Update update) {

	}
}
