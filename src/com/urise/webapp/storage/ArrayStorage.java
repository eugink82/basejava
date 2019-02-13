package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.*;
/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
        int index = findIndex(uuid);
        if (numElems >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено, запись новых резюме невозможна");
        }
        else if (index == -1) {
            storage[numElems++] = resume;
        }
        else {
            System.out.println("Резюме " + uuid + " уже есть в хранилище в хранилище данных.");
        }
    }



    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[numElems - 1];
            storage[numElems - 1] = null;
            numElems--;
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

    protected int findIndex(String uuid) {

        for (int i = 0; i < numElems; i++) {
            if ((storage[i].getUuid()).equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
