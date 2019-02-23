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
    private static final String NEW_UUID = "abrakadabra";

    private static final Resume RESUME1 = new Resume(UUID_1);
    private static final Resume RESUME2 = new Resume(UUID_2);
    private static final Resume RESUME3 = new Resume(UUID_3);
    private static final Resume NEW_RESUME = new Resume(NEW_UUID);

    protected AbstractArrayStorageTest(Storage storage) {
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
        Resume resume1=new Resume("uuid1");
        storage.update(RESUME1);
        Assert.assertSame(resume1,RESUME1);
        storage.update(RESUME2);
        storage.update(RESUME3);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist(){
        storage.update(NEW_RESUME);
    }

    @Test
    public void save() {
        // Check to increment size storage
        int numElemsBeforeSave = storage.size();
        storage.save(NEW_RESUME);
        Assert.assertEquals(storage.size(), numElemsBeforeSave + 1);
        Assert.assertSame(NEW_RESUME,storage.get(NEW_RESUME.getUuid()));
    }

    @Test
    public void saveNewResume(){
        storage.save(NEW_RESUME);
    }

    @Test(expected= ExistStorageException.class)
    public void saveExist(){
        Resume resume1=new Resume("uuid1");
        storage.save(resume1);
    }

    @Test(expected=StorageException.class)
    public void saveToOverFlowStorage(){
        int size=AbstractArrayStorage.STORAGE_LIMIT-storage.size();
        try {
            for (int i = 0; i < size; i++)
                storage.save(new Resume(UUID.randomUUID().toString()));
        } catch (StorageException e) {
            Assert.fail("Тест на переполнение провален!");
        }
        storage.save(new Resume(UUID.randomUUID().toString()));
    }

    @Test
    public void get() {
        Assert.assertEquals(storage.get(UUID_1),RESUME1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int numElemsBeforeSave = storage.size();
        storage.delete(UUID_1);
        Assert.assertEquals(storage.size(), numElemsBeforeSave - 1);
        Assert.assertSame(RESUME1,storage.get(UUID_1));
    }

    @Test(expected=NotExistStorageException.class)
    public void deleteNotExist(){
        storage.delete(NEW_UUID);
    }

    @Test
    public void getAll() {
        Resume[] array=new Resume[]{RESUME1,RESUME2,RESUME3};
        Assert.assertArrayEquals(array,storage.getAll());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }
}