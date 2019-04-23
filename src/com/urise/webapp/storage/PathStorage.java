package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.nio.file.*;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private FileOrPathSerialization fileOrPathSerialization;

    public PathStorage(String dir, FileOrPathSerialization fileOrPathSerialization) {
        this.directory = Paths.get(dir);
        this.fileOrPathSerialization=fileOrPathSerialization;
        Objects.requireNonNull(directory, "Директория не должна быть пустой");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "не является директорией или" +
                    " нельзя записать данные");
        }
    }

    @Override
    protected List<Resume> getCopyList() {
        List<Resume> listResume = new ArrayList<>();
        try {
            Files.list(directory).forEach((i) -> listResume.add(getResume(i)));
        } catch (IOException e) {
            throw new StorageException("Ошибка чтения директории", null);
        }
        return listResume;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory + "\\" + uuid);
    }

    @Override
    protected boolean isExists(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Resume getResume(Path path) {
        try {
            return fileOrPathSerialization.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (Exception e) {
            throw new StorageException("Error to get file Resume", path.getFileName().toString(), e);
        }
    }


    @Override
    protected void updateResume(Resume resume, Path path) {
        try {
            fileOrPathSerialization.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Ошибка записи файла", resume.getUuid(), e);
        }
    }

    @Override
    protected void deleteResume(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Ошибка удаления файла", path.getFileName().toString());
        }
    }

    @Override
    protected void saveResume(Resume resume, Path path) {
        Path newPath = null;
        try {
            newPath = Files.createFile(path);
            fileOrPathSerialization.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(newPath)));
        } catch (IOException e) {
            throw new StorageException("Не могу создать файл " + path.toAbsolutePath().toString(),
                    path.getFileName().toString(), e);
        }
        updateResume(resume, newPath);
    }

    //protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    //protected abstract Resume doRead(InputStream is) throws IOException;


    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Ошибка чтения директории", null);
        }
    }
}
