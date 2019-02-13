package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int numElems = 0;

    public int size() {
        return numElems;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Резюме " + uuid + " не возможно получить, его нет в хранилище в хранилище данных.");
            return null;
        }
    }

    protected abstract int findIndex(String uuid);
}
