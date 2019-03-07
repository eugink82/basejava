package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
//        int index = findIndex(uuid);
//        if (index >= 0) {
//            return getResume(index);
//        } else {
//            throw new NotExistStorageException(uuid);
//        }
        Object searchKey = findIndex(uuid);
        if (!isExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(searchKey);
    }

    public void update(Resume resume) {
//        int index = findIndex(resume.getUuid());
//        if (index >= 0) {
//            updateResume(resume, index);
//        } else {
//            throw new NotExistStorageException(resume.getUuid());
//        }
        Object searchKey = findIndex(resume.getUuid());
        if (!isExists(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        updateResume(resume, searchKey);
    }

    public void save(Resume resume) {
//        String uuid = resume.getUuid();
//        int index = findIndex(uuid);
//        if (index < 0) {
//            saveResume(resume,index);
//        } else {
//            throw new ExistStorageException(resume.getUuid());
//        }
        Object searchKey = findIndex(resume.getUuid());
        if (isExists(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume, searchKey);

    }

    public void delete(String uuid) {
//        int index = findIndex(uuid);
//        if (index >= 0) {
//           deleteResume(index);
//        } else {
//            throw new NotExistStorageException(uuid);
//        }
        Object searchKey = findIndex(uuid);
        if (!isExists(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        deleteResume(searchKey);
    }


    protected abstract Object findIndex(String uuid);

    protected abstract boolean isExists(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void updateResume(Resume resume, Object index);

    protected abstract void saveResume(Resume resume, Object index);

    protected abstract void deleteResume(Object index);

}
