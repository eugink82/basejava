package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey=new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage,0,numElems,searchKey);
    }

    @Override
    protected void saveElemToStorage(Resume resume) {
        int getIndex=Math.abs(index)-1;
        for(int i=numElems;i>getIndex;i--)
            storage[i]=storage[i-1];
        storage[getIndex] = resume;
        numElems++;
    }

    @Override
    protected void deleteElemFromStorage(int index) {
        for (int i = index; i < numElems; i++)
            storage[i] =storage[i+1];
        numElems--;
    }
}
