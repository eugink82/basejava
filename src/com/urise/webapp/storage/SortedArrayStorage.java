package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
  /*
    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
    */
    //Comparator сравнения по uuid
    private static final Comparator<Resume> RESUME_COMPARATOR= new Comparator<Resume>() {
        @Override
        public int compare(Resume r1, Resume r2) {
            return r1.getUuid().compareTo(r2.getUuid());
        }
    };

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid,"not user");
        return Arrays.binarySearch(storage, 0, numElems, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void saveElemToStorage(Resume resume, int index) {
        index = Math.abs(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, numElems - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteElemFromStorage(int index) {
        if (index != numElems - 1) {
            System.arraycopy(storage, index + 1, storage, index, (numElems - index) - 1);
        }
    }
}
