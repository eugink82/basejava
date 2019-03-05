package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return getResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    protected abstract int findIndex(String uuid);

    protected abstract Resume getResume(int index);

    protected abstract void updateResume(Resume resume, int index);
}
