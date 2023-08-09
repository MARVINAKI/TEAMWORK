package com.example.teamwork.constant;

public enum Disability {
	MOVEMENT("Передвижение"),
	VISION("Зрение"),
	INTERNAL("Внутренние нарушения"),
	NO("Нет"),
	UNDEFINED("Не определено");

	final String disability;

	Disability(String disability) {
		this.disability = disability;
	}

	public final String getDisability() {
		return disability;
	}

	@Override
	public String toString() {
		return disability;
	}
}
