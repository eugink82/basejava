package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Company {
    protected String name;
    protected LocalDate startWork;
    protected LocalDate endWork;
    protected String position;
    protected String descriptionPosition;


    public Company() {
    }


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
        DateTimeFormatter dateFormater=DateTimeFormatter.ofPattern(DatePattern);
        return name+System.lineSeparator()+dateFormater.format(startWork)+"-"+dateFormater.format(endWork)+"  "
                +position+System.lineSeparator()+descriptionPosition;
    }
}
