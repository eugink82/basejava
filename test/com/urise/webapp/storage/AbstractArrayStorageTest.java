package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest extends AbstractStorageTest {


    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveToOverFlowStorage() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++)
                storage.save(new Resume(UUID.randomUUID().toString(), ""));
        } catch (StorageException e) {
            Assert.fail("Тест на переполнение провален!");
        }
        storage.save(new Resume(UUID.randomUUID().toString(), ""));
    }

}