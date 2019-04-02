package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Company {
    private String name;
    private LocalDate startWork;
    private LocalDate endWork;
    private String position;
    private String descriptionPosition;

    public Company(String name, LocalDate startWork, LocalDate endWork, String position, String descriptionPosition) {
        this.name = name;
        this.startWork = startWork;
        this.endWork = endWork;
        this.position = position;
        this.descriptionPosition = descriptionPosition;
    }

    @Override
    public String toString() {
        String DatePattern = "MM/yyyy";
        DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern(DatePattern);
        return name + System.lineSeparator() + dateFormater.format(startWork) + "-" + dateFormater.format(endWork) + "  "
                + position + System.lineSeparator() + descriptionPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (name != null ? !name.equals(company.name) : company.name != null) return false;
        if (startWork != null ? !startWork.equals(company.startWork) : company.startWork != null) return false;
        if (endWork != null ? !endWork.equals(company.endWork) : company.endWork != null) return false;
        if (position != null ? !position.equals(company.position) : company.position != null) return false;
        return descriptionPosition != null ? descriptionPosition.equals(company.descriptionPosition) : company.descriptionPosition == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (startWork != null ? startWork.hashCode() : 0);
        result = 31 * result + (endWork != null ? endWork.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (descriptionPosition != null ? descriptionPosition.hashCode() : 0);
        return result;
    }
}
