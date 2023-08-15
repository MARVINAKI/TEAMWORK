package com.example.teamwork.timer;

import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс-таймер для запуска автоматической очистки кэша
 *
 * @author Kostya
 */
@Component
public class CacheTimer {

	private final CacheManager cacheManager;

	public CacheTimer(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * Каждый первый день месяца в 5 часов утра очищать кэш
	 */
	@Scheduled(cron = "0 0 5 1 * ?")
	public void cleanCache() {
		for (String s : Arrays.asList("reports", "cat_register", "dog_register")) {
			Objects.requireNonNull(cacheManager.getCache(s)).clear();
		}
	}
}
