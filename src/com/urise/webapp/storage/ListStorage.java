package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> listStorage = new ArrayList<>();

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
        listStorage.remove((int)searchKey);
    }

    /* Заменена реализации Resume[] getAll() на List<Resume> getAllSorted()*/
//    @Override
//    public Resume[] getAll() {
//        return listStorage.toArray(new Resume[size()]);
//    }

    @Override
    public List<Resume> getAllSorted() {
        return listStorage;
    }

    @Override
    public int size() {
        return listStorage.size();
    }

    public Object getSearchKey(String uuid) {
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
