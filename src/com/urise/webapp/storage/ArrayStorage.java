package com.urise.webapp.storage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int numElems = 0;

    void clear() {
        for (int i = 0; i < numElems; i++) {
            storage[i] = null;
        }
        numElems = 0;
    }

    void save(Resume r) {
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

    Resume get(String uuid) {
        for (int i = 0; i < numElems; i++) {
            if ((storage[i].toString()).equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < numElems; i++) {
            if ((storage[i].toString()).equals(uuid)) {
                for (int j = i; j < numElems; j++) {
                    storage[j] = storage[j + 1];
                }
                numElems--;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[numElems];
        for (int i = 0; i < numElems; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        return numElems;
    }
}
