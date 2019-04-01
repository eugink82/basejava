package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

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
    protected Resume getResume(Resume resume) {
        return resume;
    }

    @Override
    protected void updateResume(Resume r, Resume resume) {
        mapResumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected void saveResume(Resume r, Resume resume) {
        mapResumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteResume(Resume resume) {
        mapResumeStorage.remove(resume.getUuid());
    }

    @Override
    protected boolean isExists(Resume resume) {
        return resume != null;
    }
}
