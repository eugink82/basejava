package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int numElems = 0;

    public void clear() {
        for (int i = 0; i < numElems; i++) {
            storage[i] = null;
        }
        numElems = 0;
    }

    public void update(Resume r, String newUuid) {
        String uuid = r.toString();
        int findNumElem = doExistsResume(uuid);
        if (findNumElem >= 0) {
            r.setUuid(newUuid);
            storage[findNumElem] = r;
        } else
            System.out.println("Резюме " + uuid + " невозможно обновить, оно отсутствует в хранилище данных.");
    }

    public void save(Resume r) {
        int i;
        String uuid = r.toString();
        int findNumElem = -1;
        if (numElems == 0) {
            storage[numElems++] = r;
        } else {
//            for (i = 0; i < numElems; i++) {
//                if (storage[i].toString().equals(uuid)) {
//                    break;
//                }
//            }
            findNumElem = doExistsResume(uuid);
//            if (i == numElems && numElems < 10000) {
//                storage[numElems++] = r;
//            }
            if (findNumElem == -1 && numElems < 10000) {
                storage[numElems++] = r;
            } else
                System.out.println("Резюме " + uuid + " уже есть в хранилище в хранилище данных.");
        }
    }

    public Resume get(String uuid) {
//        for (int i = 0; i < numElems; i++) {
//            if ((storage[i].toString()).equals(uuid)) {
//                return storage[i];
//            }
//        }
        int findNumElem = -1;
        if ((findNumElem = doExistsResume(uuid)) >= 0) {
            return storage[findNumElem];
        } else {
            System.out.println("Резюме " + uuid + " не возможно получить, его нет в хранилище в хранилище данных.");
        }
        return null;
    }

    public void delete(String uuid) {
//        for (int i = 0; i < numElems; i++) {
//            if ((storage[i].toString()).equals(uuid)) {
//                for (int j = i; j < numElems; j++) {
//                    storage[j] = storage[j + 1];
//                }
        int findNumElem = doExistsResume(uuid);
        if (findNumElem >= 0) {
            storage[findNumElem] = storage[numElems - 1];
            storage[numElems - 1] = null;
            numElems--;
        } else
            System.out.println("Резюме " + uuid + " не удалось удалить, так как оно отсутствует в хранилище данных.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[numElems];
        for (int i = 0; i < numElems; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    public int size() {
        return numElems;
    }

    private int doExistsResume(String uuid) {

        for (int i = 0; i < numElems; i++) {
            if ((storage[i].toString()).equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
