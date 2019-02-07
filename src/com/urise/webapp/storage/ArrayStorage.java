package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int numElems = 0;

    public void clear() {
        for (int i = 0; i < numElems; i++) {
            storage[i] = null;
        }
        numElems = 0;
    }
    public void update(Resume r){

    }

    public void save(Resume r) {
        int i;
        if (numElems == 0) {
            storage[numElems++] = r;
        } else {
            for (i = 0; i < numElems; i++) {
                if (storage[i].toString().equals(r.toString())) {
                    break;
                }
            }
            if (i == numElems) {
                storage[numElems++] = r;
            }
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < numElems; i++) {
            if ((storage[i].toString()).equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < numElems; i++) {
            if ((storage[i].toString()).equals(uuid)) {
                for (int j = i; j < numElems; j++) {
                    storage[j] = storage[j + 1];
                }
                numElems--;
                return;
            }
        }
        System.out.println("Резюме "+uuid+" отсутствует в хранилище данных.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[numElems];
        for (int i = 0; i < numElems; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return numElems;
    }
}
