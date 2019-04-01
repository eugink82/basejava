package com.urise.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String tittle;

    SectionType(String tittle) {
        this.tittle = tittle;
    }

    public String getTittle() {
        return tittle;
    }
}
