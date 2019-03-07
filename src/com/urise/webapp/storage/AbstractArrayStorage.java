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
    public void updateResume(Resume resume, Object index) {
        storage[(int) index] = resume;
    }

    @Override
    public void saveResume(Resume resume, Object index) {
        if (numElems == STORAGE_LIMIT) {
            throw new StorageException("Хранилище заполнено, запись новых резюме невозможна", resume.getUuid());
        }
        saveElemToStorage(resume, (int) index);
        numElems++;
    }

    @Override
    public Resume getResume(Object index) {
        return storage[(int) index];
    }

    @Override
    public void deleteResume(Object index) {
        deleteElemFromStorage((int) index);
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

    @Override
    protected boolean isExists(Object searchKey) {
        if ((int) searchKey >= 0) {
            return true;
        }
        return false;
    }

    //  protected abstract int findIndex(String uuid);

    protected abstract void saveElemToStorage(Resume resume, int index);

    protected abstract void deleteElemFromStorage(int index);

}
