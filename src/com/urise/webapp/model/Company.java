package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Company {
    private final Link homepage;
    private final LocalDate startWork;
    private final LocalDate endWork;
    private final String position;
    private final String description;

    public Company(String name, String url, LocalDate startWork, LocalDate endWork, String position, String description) {
        Objects.requireNonNull(startWork, "Начало работы не должно быть пустым");
        Objects.requireNonNull(endWork, "Конец работы не должно быть пустым");
        Objects.requireNonNull(position, "Позиция работы, учебы не должно быть пустым");
        this.homepage = new Link(name, url);
        this.startWork = startWork;
        this.endWork = endWork;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        String DatePattern = "MM/yyyy";
        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern(DatePattern);
        return homepage.getName() + System.lineSeparator() + dateFormater.format(startWork) + "-" + dateFormater.format(endWork) + "  "
                + position + System.lineSeparator() + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!homepage.equals(company.homepage)) return false;
        if (!startWork.equals(company.startWork)) return false;
        if (!endWork.equals(company.endWork)) return false;
        if (!position.equals(company.position)) return false;
        return description != null ? description.equals(company.description) : company.description == null;
    }

    @Override
    public int hashCode() {
        int result = homepage.hashCode();
        result = 31 * result + startWork.hashCode();
        result = 31 * result + endWork.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
