package com.example.teamwork.handlers;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

public class UpdateForTest {

	public static Update getUpdate(String json, String data) {
		return BotUtils.fromJson(json.replace("%message_text%", data), Update.class);
	}
}
