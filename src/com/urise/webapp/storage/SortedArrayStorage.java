package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, numElems, searchKey);
    }

    @Override
    protected void saveElemToStorage(Resume resume, int index) {
        int getIndex = Math.abs(index) - 1;
        System.arraycopy(storage, getIndex, storage, getIndex + 1, numElems);
        storage[getIndex] = resume;
        incrementSizeStorage();
    }

    @Override
    protected void deleteElemFromStorage(int index) {

        if (index != numElems - 1) {
            System.arraycopy(storage, index + 1, storage, index, (numElems - (index + 1)));
        } else {
            System.arraycopy(storage, 0, storage, 0, (numElems - 1));
        }
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
