package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    public Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int numElems = 0;

    @Override
    public int size() {
        return numElems;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, numElems, null);
        numElems = 0;
    }

    @Override
    public void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    public void saveResume(Resume resume, int index){
        if (numElems == STORAGE_LIMIT) {
            throw new StorageException("Хранилище заполнено, запись новых резюме невозможна", resume.getUuid());
        }
        saveElemToStorage(resume, index);
        numElems++;
    }

    @Override
    public Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public void deleteResume(int index) {
            deleteElemFromStorage(index);
            storage[numElems - 1] = null;
            numElems--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[numElems];
        System.arraycopy(storage, 0, resumes, 0, resumes.length);
        return resumes;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveElemToStorage(Resume resume, int index);

    protected abstract void deleteElemFromStorage(int index);

}
