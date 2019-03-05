package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String NEW_UUID = "abracadabra";

    private static final Resume RESUME1 = new Resume(UUID_1);
    private static final Resume RESUME2 = new Resume(UUID_2);
    private static final Resume RESUME3 = new Resume(UUID_3);
    private static final Resume NEW_RESUME = new Resume(NEW_UUID);
    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME1);
        collection.add(RESUME2);
        collection.add(RESUME3);

        for (Resume r : collection) {
            System.out.println(r);
            if (r.getUuid().equals(UUID_1)) {
                //       collection.remove(r);
            }
        }
        System.out.println(collection.toString());
        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            System.out.println(r);
            if (r.getUuid().equals(UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection.toString());
        Map<String, Resume> map=new HashMap<>();
        map.put(UUID_1, RESUME1);
        map.put(UUID_2, RESUME2);
        map.put(UUID_3, RESUME3);
    }
}
