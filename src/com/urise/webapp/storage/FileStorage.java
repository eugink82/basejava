package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.*;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private FilePathSerialization filePathSerialization;

    public FileStorage(File directory, FilePathSerialization filePathSerialization) {
        Objects.requireNonNull(directory, "Директория не должна быть пустой");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " не является директорией");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("В " + directory.getAbsolutePath() + " нельзя прочитать/записать данные");
        }
        this.directory = directory;
        this.filePathSerialization = filePathSerialization;
    }


    @Override
    protected List<Resume> getCopyList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Ошибка чтения директории", null);
        }
        List<Resume> listResume = new ArrayList<>(files.length);
        for (File f : files) {
            listResume.add(getResume(f));
        }
        return listResume;
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
        try {
            return filePathSerialization.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (Exception e) {
            throw new StorageException("Error to get file Resume", file.getName(), e);
        }
    }


    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            filePathSerialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Ошибка записи файла", resume.getUuid(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("Ошибка удаления файла", file.getName());
        }
    }

    @Override
    protected void saveResume(Resume resume, File file) {
        try {
            file.createNewFile();
            filePathSerialization.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Не могу создать файл " + file.getAbsolutePath(), file.getName(), e);
        }
        updateResume(resume, file);
    }


    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Ошибка чтения директории", null);
        }
        return list.length;
    }
}
