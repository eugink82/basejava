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
        File[] files = directory.listFiles();
        List<Resume> listResume = new ArrayList<>();
        if(files!=null) {
            for (File f : files) {
                listResume.add(doRead(f));
            }
        }
        return new ArrayList<>(listResume);
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
          return doRead(file);
      } catch (Exception e){
          throw new StorageException("Error to get file Resume",file.getName(),e);
      }
    }


    @Override
    protected void updateResume(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("I/O Error", file.getName(), e);
        }
    }

    @Override
    protected void deleteResume(File file) {
       try {
           file.delete();
       }
       catch(Exception e) {
           throw new StorageException("Error to delete file Resume", file.getName(),e);
       }
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

    protected abstract Resume doRead(File file);



    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if(files!=null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        File[] files=directory.listFiles();
        int count=0;
        if(files!=null){
            for(File file: files){
                if(file.isFile()){
                    count++;
                }
            }
        }
        return count;
    }
}
