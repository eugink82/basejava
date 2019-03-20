package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> mapStorage = new LinkedHashMap<String, Resume>();

    @Override
    public void clear() {
        mapStorage.clear();
    }

    /* Заменена реализации Resume[] getAll() на List<Resume> getAllSorted()*/
//    @Override
//    public Resume[] getAll() {
//        return mapStorage.values().toArray(new Resume[size()]);
//    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] resumes=mapStorage.values().toArray(new Resume[size()]);
        return Arrays.asList(resumes);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    protected Object getSearchKey(String uuid) {
                return uuid;
    }

    @Override
    protected Resume getResume(Object uuid) {
        return mapStorage.get((String)uuid);
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
        mapStorage.remove(uuid);
    }

    @Override
    protected boolean isExists(Object searchKey) {
        return mapStorage.containsKey(searchKey);
    }
}
