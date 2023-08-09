package com.example.teamwork.handlers.messageHandlers;

import com.example.teamwork.constant.Status;
import com.pengrad.telegrambot.model.Update;

public interface MessageWithStatusHandler {

	boolean checkMessage(Update update, Status status);

	void realizationMessage(Update update);
}
