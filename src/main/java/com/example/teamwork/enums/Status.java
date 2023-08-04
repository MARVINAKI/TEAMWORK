package com.example.teamwork.enums;

public enum Status {
	CAT_FEEDBACK("/catShelterFeedback"),
	DOG_FEEDBACK("/dogShelterFeedback"),
	CAT_REPORT("/catShelterGetReport"),
	DOG_REPORT("/dogShelterGetReport");

	private final String description;

	Status(String description) {
		this.description = description;
	}

	public final String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return description;
	}
}
