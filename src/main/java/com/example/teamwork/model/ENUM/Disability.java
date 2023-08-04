package com.example.teamwork.model.ENUM;

public enum Disability {

	NO("Нет"),
	MOVEMENT("Передвижение"),
	BLINDNESS("Слепота"),
	INTERNAL("Внутренние"),
	OTHER("Другое");

	private final String disability;

	Disability(String disability) {
		this.disability = disability;
	}

	public String getDisability() {
		return disability;
	}

	@Override
	public String toString() {
		return "Disability{" +
				"disability='" + disability + '\'' +
				'}';
	}
}
