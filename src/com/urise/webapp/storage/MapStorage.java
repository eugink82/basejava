package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    /* Заменена реализации Resume[] getAll() на List<Resume> getSorted()*/
    @Override
    public List<Resume> getCopyList() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getResume(Object uuid) {
        return mapStorage.get((String) uuid);
    }

    @Override
    protected void updateResume(Resume resume, Object uuid) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    protected void saveResume(Resume resume, Object uuid) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        mapStorage.remove((String) uuid);
    }

    @Override
    protected boolean isExists(Object searchKey) {
        return mapStorage.containsKey((String) searchKey);
    }
}
