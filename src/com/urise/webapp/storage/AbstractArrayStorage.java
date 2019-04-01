package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    public Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void updateResume(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    public void saveResume(Resume resume, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Хранилище заполнено, запись новых резюме невозможна", resume.getUuid());
        }
        saveElemToStorage(resume, index);
        size++;
    }

    @Override
    public Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    public void deleteResume(Integer index) {
        deleteElemFromStorage(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected List<Resume> getCopyList() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, resumes.length);
        return Arrays.asList(resumes);
    }

    @Override
    protected boolean isExists(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void saveElemToStorage(Resume resume, int index);

    protected abstract void deleteElemFromStorage(int index);

}
