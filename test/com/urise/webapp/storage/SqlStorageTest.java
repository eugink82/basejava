package com.urise.webapp.storage;


import com.urise.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres"));
    }
}