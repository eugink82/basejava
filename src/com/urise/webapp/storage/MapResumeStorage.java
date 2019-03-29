package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    private Map<String, Resume> mapResumeStorage = new LinkedHashMap<>();

    @Override
    public void clear() {
        mapResumeStorage.clear();
    }

    /* Заменена реализации Resume[] getAll() на List<Resume> getSorted()*/
    @Override
    public List<Resume> getCopyList() {
        return new ArrayList<>(mapResumeStorage.values());
    }

    @Override
    public int size() {
        return mapResumeStorage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapResumeStorage.get(uuid);
    }

    @Override
    protected Resume getResume(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void updateResume(Resume r, Object resume) {
        mapResumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, Object resume) {
        mapResumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteResume(Object resume) {
        mapResumeStorage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected boolean isExists(Object resume) {
        return resume != null;
    }
}
