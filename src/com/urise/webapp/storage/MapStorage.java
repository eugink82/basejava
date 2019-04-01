package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {

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
    protected Resume getResume(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected void updateResume(Resume resume, String uuid) {
        mapStorage.put(uuid, resume);
    }

    @Override
    protected void saveResume(Resume resume, String uuid) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(String uuid) {
        mapStorage.remove(uuid);
    }

    @Override
    protected boolean isExists(String searchKey) {
        return mapStorage.containsKey(searchKey);
    }
}
