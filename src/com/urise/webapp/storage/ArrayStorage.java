package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {

        for (int i = 0; i < numElems; i++) {
            if ((storage[i].getUuid()).equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveElemToStorage(Resume resume, int index) {
        storage[numElems] = resume;
        incrementSizeStorage();
        ;
    }

    @Override
    protected void deleteElemFromStorage(int index) {
        storage[index] = storage[numElems - 1];
        storage[numElems - 1] = null;
        decrementSizeStorage();
    }

    @Override
    protected void incrementSizeStorage() {
        numElems++;
    }

    @Override
    protected void decrementSizeStorage() {
        numElems--;
    }
}
