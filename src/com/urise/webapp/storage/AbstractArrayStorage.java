package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage{
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int numElems = 0;
    protected int index;

    public int size() {
        return numElems;
    }

    public void clear() {
        Arrays.fill(storage,0,numElems,null);
        numElems = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Резюме " + resume.getUuid() + " невозможно обновить, оно отсутствует в хранилище данных.");
        }
    }

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        index = findIndex(uuid);
        if (numElems >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено, запись новых резюме невозможна");
        }
        else if (index <0) {
            saveElemToStorage(resume);
        }
        else {
            System.out.println("Резюме " + uuid + " уже есть в хранилище в хранилище данных.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Резюме " + uuid + " не возможно получить, его нет в хранилище в хранилище данных.");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteElemFromStorage(index);
        } else {
            System.out.println("Резюме " + uuid + " не удалось удалить, так как оно отсутствует в хранилище данных.");
        }
    }

     /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[numElems];
        System.arraycopy(storage, 0, resumes, 0, resumes.length);
        return resumes;
    }

    protected abstract int findIndex(String uuid);
    protected abstract void saveElemToStorage(Resume resume);
    protected abstract void deleteElemFromStorage(int index);
}
