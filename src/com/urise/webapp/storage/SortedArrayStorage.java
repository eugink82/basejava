package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, numElems, searchKey);
    }

    @Override
    protected void saveElemToStorage(Resume resume, int index) {
        index = Math.abs(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, numElems - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteElemFromStorage(int index) {
        if (index != numElems - 1) {
            System.arraycopy(storage, index + 1, storage, index, (numElems - index) - 1);
        }
    }
}
