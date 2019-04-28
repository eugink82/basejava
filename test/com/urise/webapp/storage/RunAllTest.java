package com.urise.webapp.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.xml.bind.annotation.adapters.XmlAdapter;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                ListStorageTest.class,
                MapStorageTest.class,
                MapResumeStorageTest.class,
                ObjectFileStorageTest.class,
                ObjectPathStorageTest.class,
                XmlPathStorageTest.class
        }
)
public class RunAllTest {
}
