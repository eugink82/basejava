package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

import java.util.logging.*;

public abstract class AbstractStorage<SK> implements Storage {
    //protected final Logger log=Logger.getLogger(getClass().getName());
    private static final Logger LOG=Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public Resume get(String uuid) {
        LOG.info("Get "+uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update "+resume);
        SK searchKey = getExistedSearchKey(resume.getUuid());
        updateResume(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save "+resume);
        SK searchKey = getNotExistedSearchKey(resume);
        saveResume(resume, searchKey);

    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete "+uuid);
        SK searchKey = getExistedSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getCopyList();
        Collections.sort(list);
        return list;
    }

    private SK getNotExistedSearchKey(Resume resume) {
        SK searchKey = getSearchKey(resume.getUuid());
        if (isExists(searchKey)) {
            LOG.warning("Резюме с uuid=" + resume.getUuid() + " уже существует");
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExists(searchKey)) {
            LOG.warning("Резюме с uuid=" + uuid + " не существует");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }


    protected abstract List<Resume> getCopyList();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExists(SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract void updateResume(Resume resume, SK searchKey);

    protected abstract void saveResume(Resume resume, SK searchKey);

    protected abstract void deleteResume(SK index);

}
