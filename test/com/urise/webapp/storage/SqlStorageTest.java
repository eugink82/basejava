package com.urise.webapp.storage;


import com.urise.webapp.util.SqlHelper;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper("jdbc:postgresql://localhost:5432/resumes", "postgres", "postgres")));
    }
}