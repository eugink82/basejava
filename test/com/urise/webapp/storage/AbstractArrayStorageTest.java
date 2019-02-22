package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String testToSaveUUID1 = "testToSave1";
    private static final String testToSaveUUID2 = "testToSave2";
    private static final int STORAGE_LIMIT = 10_000;

    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume testToSaveResume1 = new Resume(testToSaveUUID1);
    private static final Resume testToSaveResume2 = new Resume(testToSaveUUID2);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        storage.update(resume1);
        storage.update(resume2);
        storage.update(resume3);
    }

    @Test
    public void save() {

        //check to save exist resume to storage
       try {
           storage.save(testToSaveResume1);
       }
       catch(ExistStorageException e){
           Assert.fail("Проверка добавления существующего резюме: "+e.getMessage());
       }

        // Check to increment size storage
        int numElemsBeforeSave = storage.size();
        storage.save(testToSaveResume2);
        Assert.assertEquals(storage.size(), numElemsBeforeSave + 1);
        System.out.println("Проверка увеличение размера хранилища: Размер хранилища был увеличен с "+
                numElemsBeforeSave+" до "+storage.size());
        // Check to successful save resume to storage
        try{
            storage.get(testToSaveResume2.getUuid());
        }
        catch (NotExistStorageException e){
            Assert.fail("Резюме "+testToSaveResume2.getUuid()+" не удалось сохранить в хранилище!");
        }

        //check to overflow storage
        int size = storage.size();
        try {
            for (int i = 0; i < STORAGE_LIMIT - size; i++)
                storage.save(new Resume(UUID.randomUUID().toString()));
        } catch (StorageException e) {
            Assert.fail("Тест на переполнение провален!");
        }
        try {
            storage.save(new Resume(UUID.randomUUID().toString()));
        } catch (StorageException e) {
            System.out.println("Тест пройден!");
        }
    }

    @Test
    public void get() {
        storage.get(UUID_1);
    }

    @Test
    public void delete() {
        //check to save exist resume to storage
        try {
            storage.delete(UUID_1);
        }
        catch(NotExistStorageException e){
            Assert.fail(e.getMessage());
        }
        // Check to increment size storage
        int numElemsBeforeSave = storage.size();
        storage.delete(resume2.getUuid());
        Assert.assertEquals(storage.size(), numElemsBeforeSave - 1);
        System.out.println("Проверка уменьшения размера хранилища: Размер хранилища был уменьшен с "+
                numElemsBeforeSave+" до "+storage.size());

    }

    @Test
    public void getAll() {
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}