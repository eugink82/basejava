package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    public void update(Resume resume) {
        Object searchKey = getExistedSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    public void save(Resume resume) {
        Object searchKey = getNotExistedSearchKey(resume);
        saveResume(resume, searchKey);

    }

    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        deleteResume(searchKey);
    }

    private Object getNotExistedSearchKey(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (isExists(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }


    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExists(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void updateResume(Resume resume, Object index);

    protected abstract void saveResume(Resume resume, Object index);

    protected abstract void deleteResume(Object index);

}
