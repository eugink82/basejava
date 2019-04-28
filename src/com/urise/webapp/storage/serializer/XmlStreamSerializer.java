package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StorageStrategy{
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser=new XmlParser(Resume.class,Company.class,Company.Position.class,Link.class,CompanySection.class,
                ListSection.class,SimpleTextSection.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try(Writer w=new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            xmlParser.marshall(resume,w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try(Reader r=new InputStreamReader(is, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(r);
        }
    }
}
