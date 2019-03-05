package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
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
//    public void update(Resume resume) {
//        int index = findIndex(resume.getUuid());
//        if (index >= 0) {
//            storage[index] = resume;
//        } else {
//           throw new NotExistStorageException(resume.getUuid());
//        }
//    }
    public void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (numElems >= STORAGE_LIMIT) {
            throw new StorageException("Хранилище заполнено, запись новых резюме невозможна", resume.getUuid());
        } else if (index < 0) {
            saveElemToStorage(resume, index);
            numElems++;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }


//    @Override
//    public Resume get(String uuid) {
//        int index = findIndex(uuid);
//        if (index >= 0) {
//            return storage[index];
//        } else {
//            throw new NotExistStorageException(uuid);
//        }
//      //  int index=isExistsElement(uuid);
//     //   return storage[index];
//    }

    @Override
    public Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteElemFromStorage(index);
            storage[numElems - 1] = null;
            numElems--;
        } else {
            throw new NotExistStorageException(uuid);
        }
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
