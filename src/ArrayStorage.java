/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int numElems=0;
    void clear() {
      for(int i=0;i<numElems;i++)
          storage[i]=null;
      numElems=0;
    }

    void save(Resume r) {
      int i;
      if(numElems==0) {
          storage[numElems++] = r;
      }
      else {
          for (i = 0; i < numElems; i++) {
             if(storage[i].toString().equals(r.toString()))
               break;
          }
          if(i==numElems) {
              storage[numElems++] = r;
          }
      }
    }

    Resume get(String uuid) {
      Resume getElem=null;
      for(int i=0;i<numElems;i++)
          if((storage[i].toString()).equals(uuid))
            getElem=storage[i];
      return getElem;
    }

    void delete(String uuid) {
      for(int i=0;i<numElems; i++){
          if((storage[i].toString()).equals(uuid)){
             for(int j=i;j<numElems;j++){
                 storage[j]=storage[j+1];
             }
             numElems--;
          }
      }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
       Resume[] array=new Resume[numElems];
        for(int i=0;i<numElems;i++)
            array[i]=storage[i];
        return array;
    }

    int size() {
        return numElems;
    }
}
