package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.List;
import java.util.UUID;

public class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String FULLNAME_1="Ivanov Sergey";
    private static final String UUID_2 = "uuid2";
    private static final String FULLNAME_2="Sidorov Genadiy";
    private static final String UUID_3 = "uuid3";
    private static final String FULLNAME_3="Murzabekov Ravshan";
    private static final String NEW_UUID = "abrakadabra";
    private static final String NEW_FULLNAME="Konoplev Vladimir";

    private static final Resume RESUME1 = new Resume(UUID_1,FULLNAME_1);
    private static final Resume RESUME2 = new Resume(UUID_2,FULLNAME_2);
    private static final Resume RESUME3 = new Resume(UUID_3,FULLNAME_3);
    private static final Resume NEW_RESUME = new Resume(NEW_UUID,NEW_FULLNAME);

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
        Resume resume1 = new Resume(UUID_1,FULLNAME_1);
        storage.update(resume1);
        Assert.assertSame(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(NEW_RESUME);
    }

    @Test
    public void save() {
        // Check to increment size storage
       // int numElemsBeforeSave = storage.size();
        storage.save(NEW_RESUME);
//        Assert.assertEquals(storage.size(), numElemsBeforeSave + 1);
        Assert.assertEquals(4,storage.size());
        Assert.assertSame(NEW_RESUME, storage.get(NEW_RESUME.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME1);
    }



    @Test
    public void get() {
        Assert.assertEquals(storage.get(UUID_1), RESUME1);
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
//        Resume[] array=storage.getAll();
//        Assert.assertEquals(3,array.length);
//        Assert.assertEquals(RESUME1,array[0]);
//        Assert.assertEquals(RESUME2,array[1]);
//        Assert.assertEquals(RESUME3,array[2]);

        List<Resume> array=storage.getAllSorted();
        Assert.assertEquals(3, array.size());
        Assert.assertEquals(RESUME1,array.get(0));
        Assert.assertEquals(RESUME2,array.get(1));
        Assert.assertEquals(RESUME3,array.get(2));
    }


}