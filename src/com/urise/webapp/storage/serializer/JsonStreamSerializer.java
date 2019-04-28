package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.JsonParser;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StorageStrategy{
//    private XmlParser xmlParser;
//
//    public JsonStreamSerializer() {
//        xmlParser=new XmlParser(Resume.class,Company.class,Company.Position.class,Link.class,CompanySection.class,
//                ListSection.class,SimpleTextSection.class);
//    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try(Writer writer=new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            JsonParser.write(resume,writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader reader=new InputStreamReader(is, StandardCharsets.UTF_8)){
            return JsonParser.read(reader,Resume.class);
        }
    }
}
