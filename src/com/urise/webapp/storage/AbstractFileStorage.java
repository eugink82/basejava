package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.*;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Директория не должна быть пустой");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " не является директорией");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("В " + directory.getAbsolutePath() + " нельзя прочитать/записать данные");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getCopyList() {
        return null;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExists(File file) {
        return file.exists();
    }

    @Override
    protected Resume getResume(File file) {
        return null;
    }

    @Override
    protected void updateResume(Resume resume, File file) {

    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("I/O Error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    @Override
    protected void deleteResume(File file) {

    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }
}
