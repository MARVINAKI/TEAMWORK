package com.example.teamwork.timer;

import com.example.teamwork.repository.dog.DogRegisterRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReportsTimer {

	private final static Long CHAT_ID_OF_VOLUNTEER = 977515525L;
	private final TelegramBot telegramBot;
	private final DogRegisterRepository dogRegisterRepository;

	public ReportsTimer(TelegramBot telegramBot, DogRegisterRepository dogRegisterRepository) {
		this.telegramBot = telegramBot;
		this.dogRegisterRepository = dogRegisterRepository;
	}

	/**
	 *	Класс-таймер с одной функцией - оповещением пользователя о просрочке по отчётам
	 */
	@Scheduled(cron = "30 30 20 * * ?")
	public void reportReminder() {
		dogRegisterRepository.findAll().stream()
				.filter(dogRegister -> dogRegister.getTrialPeriod() > 0)
				.forEach(dogRegister -> {

			/*
			Изменение испытаьельного срока вне зависимости от отчётов.
			*/
					dogRegisterRepository.findById(dogRegister.getId()).ifPresent(value -> {
						value.setTrialPeriod(dogRegister.getTrialPeriod() - 1);
						dogRegisterRepository.saveAndFlush(value);
					});

					boolean emptyReport = dogRegister.getLastDateOfReports() == null;
					boolean noFirstReport = (LocalDateTime.now().getDayOfYear() - dogRegister.getRegistrationDate().getDayOfYear()) > 2;
					boolean oneDayNoReport = LocalDateTime.now().getDayOfYear() > dogRegister.getLastDateOfReports().getDayOfYear();
					boolean twoDayNoReport = (LocalDateTime.now().getDayOfYear() - dogRegister.getLastDateOfReports().getDayOfYear()) > 2;

			/*
			Полное отсутвие информации от усыновителя или отсутствие более 2 дней. Отправка запроса на связь волонтёру.
			 */
					if ((emptyReport || noFirstReport || twoDayNoReport)) {
						SendMessage sendMessage = new SendMessage(CHAT_ID_OF_VOLUNTEER, String.format("Нет ответа от усыновителя более 2 дней. id=%s, chatId=%s", dogRegister.getId(), dogRegister.getAdoptersChatId()));
						telegramBot.execute(sendMessage);
					}
			/*
			Ежедневное напоминание о необходимости отправки отчёта.
			 */
					if (emptyReport || oneDayNoReport) {
						SendMessage sendMessage = new SendMessage(CHAT_ID_OF_VOLUNTEER, "Дорогой усыновитель, уведомляем Вас об обязательной отпраке ежедневного отчёте о проживании питомца.");
						telegramBot.execute(sendMessage);
					}
				});
	}
}
