package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> listStorage = new ArrayList<>();

    @Override
    public void clear() {
        listStorage.clear();
    }

    @Override
    public void updateResume(Resume resume, Integer searchKey) {
        listStorage.set((int) searchKey, resume);
    }

    @Override
    public void saveResume(Resume resume, Integer searchKey) {
        listStorage.add(resume);
    }


    @Override
    public Resume getResume(Integer searchKey) {
        return listStorage.get((int) searchKey);
    }

    @Override
    public void deleteResume(Integer searchKey) {
        listStorage.remove((int) searchKey);
    }

    /* Заменена реализации Resume[] getAll() на List<Resume> getSorted()*/
    @Override
    protected List<Resume> getCopyList() {
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
    protected boolean isExists(Integer searchKey) {
        return (int) searchKey >= 0;
    }
}
