package com.example.teamwork.timer;

import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class CacheTimer {

	private final CacheManager cacheManager;

	public CacheTimer(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Scheduled(cron = "0 0 23 7 * ?")
	public void cleanCache() {
		for (String s : Arrays.asList("reports", "cat_register", "dog_register")) {
			Objects.requireNonNull(cacheManager.getCache(s)).clear();
		}
	}
}
