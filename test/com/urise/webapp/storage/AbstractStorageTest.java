package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AbstractStorageTest {
    protected Storage storage;
    //protected static final File STORAGE_DIR=new File("C:\\basejava\\storage");
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    private static final String UUID_1 = UUID.randomUUID().toString(); //"uuid1";
    private static final String UUID_2 = UUID.randomUUID().toString();//"uuid2";
    private static final String UUID_3 = UUID.randomUUID().toString();//"uuid3";
    private static final String NEW_UUID =UUID.randomUUID().toString(); //"new_uuid";

    private static final Resume RESUME1;
    private static final Resume RESUME2;
    private static final Resume RESUME3;
    private static final Resume NEW_RESUME;

    static {
        RESUME1 = ResumeTestData.createAndFillResume(UUID_1, "Григорий Кислин");
        RESUME2 = ResumeTestData.createAndFillResume(UUID_2, "Сидоров Антон");
        RESUME3 = ResumeTestData.createAndFillResume(UUID_3, "Мурзабеков Руслан");
        NEW_RESUME = ResumeTestData.createAndFillResume(NEW_UUID, "Коноплев Павел");
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
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(NEW_RESUME);
    }

    @Test
    public void save() {
        storage.save(NEW_RESUME);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(NEW_RESUME, storage.get(NEW_UUID)); //ранее был метод Assert.assertSame, при нем тест ObjectFileStorageTest не проходит
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
            storage.save(RESUME1);
    }


    @Test
    public void get() {
        Assert.assertEquals(RESUME1, storage.get(RESUME1.getUuid()));
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