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
    public void updateResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public void saveResume(Resume resume, int index) {
        storage.add(resume);
    }


    @Override
    public Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    public void deleteResume(int index){
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

    public int findIndex(String uuid) {
        return storage.indexOf(uuid);
    }

}
