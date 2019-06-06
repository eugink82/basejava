package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Sections;
import com.urise.webapp.model.SimpleTextSection;
import org.junit.Assert;
import org.junit.Test;
import sun.swing.SwingUtilities2;

import java.util.UUID;

import static org.junit.Assert.*;

public class JsonParserTest {
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final Resume R1 = ResumeTestData.createAndFillResume(UUID_1, "Григорий Кислин");
    @Test
    public void testResume() {
        String json=JsonParser.write(R1);
        System.out.println(json);
        Resume resume=JsonParser.read(json,Resume.class);
        Assert.assertEquals(R1,resume);
    }

    @Test
    public void write() {
        Sections section1=new SimpleTextSection("Objective1");
        String json=JsonParser.write(section1, Sections.class);
        System.out.println(json);
        Sections section=JsonParser.read(json,Sections.class);
        Assert.assertEquals(section1,section);
    }
}