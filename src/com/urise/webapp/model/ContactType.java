package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    SKYPE("Skype"),
    EMAIL("Эл.почта"),
    PROFILE("Профиль в соц.сети"),
    HOMEPAGE("Домашняя страница");

    private String tittle;

    ContactType(String tittle) {
        this.tittle = tittle;
    }

    public String getTittle() {
        return tittle;
    }
}
