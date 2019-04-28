package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;

import java.io.*;

public interface StorageStrategy {
    void doWrite(Resume resume, OutputStream os) throws IOException;
    Resume doRead(InputStream is) throws IOException;
}
