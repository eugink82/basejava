package com.urise.webapp;

import com.urise.webapp.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestDataTemp {

    public static void main(String[] args) throws IOException {
        Resume r = new Resume("Григорий Кислин");
        //  r.addContacts();
        //  r.printContacts();
        r.contacts.put(ContactType.SKYPE, "grigory.kislin");
        r.contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        r.contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        r.contacts.put(ContactType.PHONE, "http://gkislin.ru/");

        SimpleTextSection simple1 = new SimpleTextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");
        SimpleTextSection simple2 = new SimpleTextSection("Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры.");

        List<String> list1 = new ArrayList<>();
        list1.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                " \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                " Удаленное взаимодействие (JMS/AKKA)\"." +
                " Организация онлайн стажировок и ведение проектов. Более 1000 выпускников. ");

        list1.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                " Интеграция с Twilio, DuoSecurity," +
                " Google Authenticator, Jira, Zendesk. ");
        list1.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления " +
                "окружением на стеке: Scala/Play/Anorm/JQuery." +
                " Разработка SSO аутентификации и авторизации различных ERP модулей," +
                " интеграция CIFS/SMB java сервера. ");

        ListSection listSection1 = new ListSection(list1);

        List<String> list2 = new ArrayList<>();
        list2.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list2.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list2.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        ListSection listSection2 = new ListSection(list2);

        List<Company.Position> positionJavaOnlineProject = new ArrayList<>();
        positionJavaOnlineProject.add(new Company.Position("Автор проекта.", "Создание, организация и проведение  Java онлайн проектов и стажировок.",
                LocalDate.of(2013, 10, 1), LocalDate.now()));
        Company companyJavaOnlineProject = new Company(new Link("Java Online Projects", "JavaOnlineProjects.html"), positionJavaOnlineProject);
        List<Company.Position> positionWrike = new ArrayList<>();
        positionWrike.add(new Company.Position("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы " +
                "управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO." +
                "Проектирование и разработка онлайн платформы управления проектами " +
                "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                LocalDate.of(2014, 10, 2), LocalDate.of(2016, 1, 31)));
        positionWrike.add(new Company.Position("Java архитектор", "Организация процесса разработки системы ERP для " +
                "разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы " +
                "(кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД" +
                " и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online" +
                " редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons," +
                " Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell " +
                "remote scripting via ssh tunnels, PL/Python",
                LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1)));
        Company companyWrike = new Company(new Link("Wrike", "wrike.html"), positionWrike);
        List<Company.Position> positionYota = new ArrayList<>();
        positionYota.add(new Company.Position("Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\"" +
                " (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования," +
                " статистики и мониторинга фреймворка. Разработка online JMX клиента " +
                "(Python/ Jython, Django, ExtJS)", LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1)));
        Company companyYota = new Company(new Link("Yota", "Yota.html"), positionYota);

        List<Company> listCompany2 = new ArrayList<>();
        listCompany2.add(companyJavaOnlineProject);
        listCompany2.add(companyWrike);
        listCompany2.add(companyYota);
        CompanySection companySection1 = new CompanySection(listCompany2);

        List<Company.Position> positionCoursera = new ArrayList<>();
        positionCoursera.add(new Company.Position("\"Functional Programming Principles in Scala\" by Martin Odersky", "",
                LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1)));
        Company companyCoursera = new Company(new Link("Coursera", "Coursera.html"), positionCoursera);

        List<Company.Position> positionUniv = new ArrayList<>();
        positionUniv.add(new Company.Position("Аспирантура (программист С, С++)", "",
                LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1)));
        positionUniv.add(new Company.Position("Инженер (программист Fortran, C))", "",
                LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1)));
        Company companyUniv = new Company(new Link("Санкт-Петербургский национальный исследовательский университет" +
                " информационных технологий, механики и оптики", "Pietari.html"), positionUniv);

        List<Company> listCompany3 = new ArrayList<>();
        listCompany3.add(companyCoursera);
        listCompany3.add(companyUniv);
        CompanySection companySection2 = new CompanySection(listCompany3);

        r.sections.put(SectionType.OBJECTIVE, simple1);
        r.sections.put(SectionType.PERSONAL, simple2);
        r.sections.put(SectionType.ACHIEVEMENT, listSection1);
        r.sections.put(SectionType.QUALIFICATIONS, listSection2);
        r.sections.put(SectionType.EXPERIENCE, companySection1);
        r.sections.put(SectionType.EDUCATION, companySection2);

        System.out.println(r.getFullName());
        System.out.println();
        for (Map.Entry<ContactType, String> m : r.contacts.entrySet()) {
            System.out.println(m.getKey() + ": " + m.getValue());
        }
        System.out.println("__________________________________________________________________");

        for (Map.Entry<SectionType, Sections> m : r.sections.entrySet()) {
            SectionType s = null;
            if (m.getKey() != s) {
                System.out.println();
                System.out.println();
                System.out.println(m.getKey().getTitle());
                System.out.println();
            }
            //m.getValue().printSection();
            System.out.println(m.getValue());
        }


    }
}
