package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> mapStorage = new HashMap<String, Resume>();

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
    protected Object getSearchKey(String uuid) {
//        for (Resume resume : mapStorage.values()) {
//            if (uuid.equals(resume.getUuid())) {
//                return uuid;
//            }
//        }
        for(String key: mapStorage.keySet()){
            if(uuid.equals(key)){
                return key;
            }
        }
        return null;
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
        return searchKey != null;
    }
}
