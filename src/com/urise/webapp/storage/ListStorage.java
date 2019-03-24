package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public void updateResume(Resume resume, Object searchKey) {
        listStorage.set((int) searchKey, resume);
    }

    @Override
    public void saveResume(Resume resume, Object searchKey) {
        listStorage.add(resume);
    }


    @Override
    public Resume getResume(Object searchKey) {
        return listStorage.get((int) searchKey);
    }

    @Override
    public void deleteResume(Object searchKey) {
        listStorage.remove((int) searchKey);
    }

    /* Заменена реализации Resume[] getAll() на List<Resume> getSorted()*/
    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(listStorage);
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < listStorage.size(); i++) {
            if (uuid.equals(listStorage.get(i).getUuid())) {
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
