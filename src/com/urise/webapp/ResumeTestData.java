package com.urise.webapp;

import com.urise.webapp.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) throws IOException {
        Resume r=new Resume("Григорий Кислин");
      //  r.addContacts();
      //  r.printContacts();
        r.mapContacts.put(ContactType.SKYPE,"grigory.kislin");
        r.mapContacts.put(ContactType.EMAIL,"gkislin@yandex.ru");
        r.mapContacts.put(ContactType.PROFILE,"https://github.com/gkislin");
        r.mapContacts.put(ContactType.PHONE,"http://gkislin.ru/");

        SimpleTextSection simple1=new SimpleTextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям");
        SimpleTextSection simple2=new SimpleTextSection("Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры.");

        List<String> list1=new ArrayList<>();
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

        ListSection listSection1=new ListSection(list1);

        List<String> list2=new ArrayList<>();
        list2.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        list2.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        list2.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");

        ListSection listSection2=new ListSection(list2);

        Company company1=new Company("Java Online Projects", LocalDate.of(2013,10,1),
                LocalDate.now(),"Автор проекта.","Создание, организация и проведение" +
                " Java онлайн проектов и стажировок.");
        Company company2=new Company("Wrike",LocalDate.of(2014,10,1),
                LocalDate.of(2016,01,31),"Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами " +
                        "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                        " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        List<Company> listCompany1=new ArrayList<>();
        listCompany1.add(company1);
        listCompany1.add(company2);
        CompanySection companySection1=new CompanySection(listCompany1);
        Company company3=new Company("Coursera",LocalDate.of(2013,03,1),LocalDate.of(2013,05,31),
                "\"Functional Programming Principles in Scala\" by Martin Odersky","");
        Company company4=new Company("Luxoft",LocalDate.of(2011,03,1),LocalDate.of(2011,04,30),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"","");

        List<Company> listCompany2=new ArrayList<>();
        listCompany2.add(company3);
        listCompany2.add(company4);
        CompanySection companySection2=new CompanySection(listCompany2);


        r.mapSections.put(SectionType.OBJECTIVE,simple1);
        r.mapSections.put(SectionType.PERSONAL,simple2);
        r.mapSections.put(SectionType.ACHIEVEMENT,listSection1);
        r.mapSections.put(SectionType.QUALIFICATIONS,listSection2);
        r.mapSections.put(SectionType.EXPERIENCE,companySection1);
        r.mapSections.put(SectionType.EDUCATION,companySection2);

        System.out.println(r.getFullName());
        System.out.println();
        for(Map.Entry<ContactType,String> m: r.mapContacts.entrySet()){
            System.out.println(m.getKey()+": "+m.getValue());
        }
        System.out.println("__________________________________________________________________");

        for(Map.Entry<SectionType,Sections> m: r.mapSections.entrySet()){
            SectionType s=null;
            if(m.getKey()!=s) {
                System.out.println();
                System.out.println();
                System.out.println(m.getKey().getTittle());
                System.out.println();
            }
            m.getValue().printSection();
        }






    }
}
