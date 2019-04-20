package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String NEW_UUID = "abrakadabra";

    private static final Resume RESUME1;
    private static final Resume RESUME2;
    private static final Resume RESUME3;
    private static final Resume NEW_RESUME;

    static{
        RESUME1 = new Resume(UUID_1, "Ivanov");
        RESUME2 = new Resume(UUID_2, "Sidorov");
        RESUME3 = new Resume(UUID_3, "Murzabekov");
        NEW_RESUME = new Resume(NEW_UUID, "Konoplev");
        RESUME1.addContact(ContactType.EMAIL,"testmail@ya.ru");
        RESUME1.addContact(ContactType.PHONE,"9999999");
        RESUME1.addSection(SectionType.OBJECTIVE, new SimpleTextSection("Objective1"));
        RESUME1.addSection(SectionType.PERSONAL,new SimpleTextSection("Personal data"));
        RESUME1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievment11","Achievment12","Achievment13"));
        RESUME1.addSection(SectionType.QUALIFICATIONS,new ListSection("Java","SQL","JavaScript"));
        RESUME1.addSection(SectionType.EXPERIENCE,
                    new CompanySection(
                            new Company("Company11","http://Company11.ru",
                                    new Company.Position("position1","content1",2005, Month.JANUARY),
                                    new Company.Position("position2","content2",2001,Month.MARCH,2005,Month.JANUARY))));
        RESUME1.addSection(SectionType.EDUCATION,
                    new CompanySection(
                            new Company("Institute",null,
                                    new Company.Position("asspirant",null,2001,Month.MARCH,2005,Month.JANUARY),
                                    new Company.Position("student","It fakultet",1996,Month.SEPTEMBER,2000,Month.DECEMBER)),
                            new Company("Company12","http://Company12.ru")));
        RESUME2.addContact(ContactType.SKYPE,"skype11");
        RESUME2.addContact(ContactType.PHONE,"222222");
        RESUME2.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("Company2","http://Company2.ru",
                                new Company.Position("position1","content1",2015, Month.JANUARY)
                               )));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resume1 = new Resume(UUID_1, "Andrei Petrov");
        storage.update(resume1);
        Assert.assertSame(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(NEW_RESUME);
    }

    @Test
    public void save() {
        storage.save(NEW_RESUME);
        Assert.assertEquals(4, storage.size());
        Assert.assertSame(NEW_RESUME, storage.get(NEW_UUID));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME1);
    }


    @Test
    public void get() {
        Assert.assertEquals(RESUME1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int numElemsBeforeSave = storage.size();
        storage.delete(UUID_1);
        Assert.assertEquals(storage.size(), numElemsBeforeSave - 1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(NEW_UUID);
    }

    @Test
    public void getAll() {
        /*Замена Resume[] на List<Resume>*/
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3, list.size());
        List<Resume> listTestResumes = Arrays.asList(RESUME1, RESUME2, RESUME3);
        Collections.sort(listTestResumes);
        Assert.assertEquals(listTestResumes, list);
    }


}