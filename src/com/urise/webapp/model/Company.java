package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Company {
    private final Link homepage;
    private final List<Position> position;

    public Company(Link homepage, List<Position> position) {
        this.homepage = homepage;
        this.position = position;
        //        this.startWork = startWork;
//        this.endWork = endWork;
//        this.position = position;
//        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Position p : position)
            sb.append(p.toString()).append(System.lineSeparator());

        return homepage.getName() + System.lineSeparator() + sb.toString();
//        return "Company{" +
//                "homepage=" + homepage +
//                ", position=" + position +
//                '}';
    }

    public static class Position {
        private final String position;
        private final String description;
        private final LocalDate startWork;
        private final LocalDate endWork;

        public Position(String position, String description, LocalDate startWork, LocalDate endWork) {
            Objects.requireNonNull(position, "Позиция работы, учебы не должно быть пустым");
            Objects.requireNonNull(startWork, "Начало работы не должно быть пустым");
            Objects.requireNonNull(endWork, "Конец работы не должно быть пустым");

            this.position = position;
            this.description = description;
            this.startWork = startWork;
            this.endWork = endWork;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getStartWork() {
            return startWork;
        }

        public LocalDate getEndWork() {
            return endWork;
        }

        @Override
        public String toString() {
            String datePattern = "MM/yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
            return dateFormatter.format(startWork) + "-" + dateFormatter.format(endWork) + "  " +
                    position + System.lineSeparator() + "                 " + description;
//            return "Position{" +
//                    "position='" + position + '\'' + System.lineSeparator()+
//                    ", description='" + description + '\'' +System.lineSeparator()+
//                    ", startWork=" + startWork +System.lineSeparator()+
//                    ", endWork=" + endWork +System.lineSeparator()+
//                    '}';
        }
    }
}
