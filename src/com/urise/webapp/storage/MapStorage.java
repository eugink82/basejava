package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected Object findIndex(String uuid) {
        for (Resume resume : mapStorage.values()) {
            if (uuid.equals(resume.getUuid())) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    protected Resume getResume(Object uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected void updateResume(Resume resume, Object uuid) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    protected void saveResume(Resume resume, Object uuid) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    protected void deleteResume(Object uuid) {
        mapStorage.remove(uuid);
    }

    @Override
    protected boolean isExists(Object searchKey) {
        return searchKey != null;
    }
}
