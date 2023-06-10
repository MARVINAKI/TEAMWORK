package com.example.teamwork.service;

import com.example.teamwork.model.Feedback;
import com.example.teamwork.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;

	public FeedbackService(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	public void addClientRequest(Feedback feedback) {
		feedbackRepository.save(feedback);
	}
}
