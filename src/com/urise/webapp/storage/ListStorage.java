package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
//    public void update(Resume resume) {
//        int index = findIndex(resume.getUuid());
//        if (index >= 0) {
//            storage.set(index, resume);
//        } else {
//            throw new NotExistStorageException(resume.getUuid());
//        }
//    }

    public void updateResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            storage.add(resume);
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    //  public Resume get(String uuid) {
//        int index = findIndex(uuid);
//        if (index >= 0) {
//            return storage.get(index);
//        } else {
//            throw new NotExistStorageException(uuid);
//        }

    //  }
    public Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage.remove(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    public int findIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return storage.indexOf(uuid);
    }

//    private void ensureCapacityInternal(int minCapacity){
//       int calcCapacity=calculateCapacity(storage,minCapacity)-storage.length;
//       if(calcCapacity==DEFAULT_CAPACITY) {
//           storage = Arrays.copyOf(storage, DEFAULT_CAPACITY);
//       }
//       else if(calcCapacity!=DEFAULT_CAPACITY && calcCapacity>0){
//           int oldCapacity=storage.length;
//           if(oldCapacity==0)
//               oldCapacity=DEFAULT_CAPACITY;
//           int newCapacity=(oldCapacity*3)/2+1;
//           storage= Arrays.copyOf(storage,DEFAULT_CAPACITY);
//       }
//    }
//
//    private static int calculateCapacity(Resume[] storage, int minCapacity){
//       if(storage==DEFAULTCAPACITY_EMPTY_ELEMENTDATA){
//           return DEFAULT_CAPACITY;
//       }
//       return minCapacity;
//    }
}
