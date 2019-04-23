package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import java.io.*;
import java.util.logging.*;

public class ObjectStreamPathStorage implements FileOrPathSerialization {
 private static final Logger LOG=Logger.getLogger(ObjectStreamPathStorage.class.getName());

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
      LOG.info("Cериализация с "+ObjectStreamPathStorage.class.getSimpleName());
      try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
      try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Ошибка чтения Резюме",null,e);
        }
    }
}
