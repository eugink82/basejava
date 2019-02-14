package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Резюме " + resume.getUuid() + " невозможно обновить, оно отсутствует в хранилище данных.");
        }
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (numElems >= STORAGE_LIMIT) {
            System.out.println("Хранилище заполнено, запись новых резюме невозможна");
        }
        else if (index <0) {
            int getIndex=Math.abs(index)-1;
            for(int i=numElems;i>getIndex;i--)
                storage[i]=storage[i-1];
            storage[getIndex] = resume;
            numElems++;

        }
        else {
            System.out.println("Резюме " + uuid + " уже есть в хранилище в хранилище данных.");
        }
    }

    @Override
    public void delete(String uuid) {
        int index=findIndex(uuid);
        if(index>=0) {
            for (int i = index; i < numElems; i++)
                storage[i] =storage[i+1];
            numElems--;
        }
        else {
            System.out.println("Резюме " + uuid + " не удалось удалить, так как оно отсутствует в хранилище данных.");
        }
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[numElems];
        System.arraycopy(storage, 0, resumes, 0, resumes.length);
        return resumes;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey=new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage,0,numElems,searchKey);
    }
}
