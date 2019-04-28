package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StorageStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.nio.file.*;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private StorageStrategy storageStrategy;

    public PathStorage(String dir, StorageStrategy storageStrategy) {
        Objects.requireNonNull(dir, "Директория не должна быть пустой");
        this.directory = Paths.get(dir);
        this.storageStrategy = storageStrategy;
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "не является директорией или" +
                    " нельзя записать данные");
        }
    }

    @Override
    protected List<Resume> getCopyList() {
        List<Resume> listResume = new ArrayList<>();
        getFilesList().forEach((i) -> listResume.add(getResume(i)));
        return listResume;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExists(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (Exception e) {
            throw new StorageException("Error to get file Resume", getFileName(path), e);
        }
    }


    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            storageStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Ошибка записи файла", resume.getUuid(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Ошибка удаления файла", getFileName(path));
        }
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Не могу создать файл " + path,
                    getFileName(path), e);
        }
        updateResume(resume, path);
    }



    @Override
    public void clear() {
        getFilesList().forEach(this::deleteResume);
    }


    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private String getFileName(Path path){
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList()  {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Ошибка чтения директории", e);
        }
    }
}
