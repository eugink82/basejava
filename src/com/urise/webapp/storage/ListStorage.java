package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void updateResume(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    public void saveResume(Resume resume, Object index) {
        storage.add(resume);
    }


    @Override
    public Resume getResume(Object index) {
        return storage.get((int) index);
    }

    @Override
    public void deleteResume(Object index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    public Object findIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExists(Object searchKey) {
        return (int) searchKey >= 0;
    }
}
