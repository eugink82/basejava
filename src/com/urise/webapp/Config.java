package com.urise.webapp;

import java.io.*;
import java.util.*;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

public class Config {
    protected static final File PROPS = new File("config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    private Properties props = new Properties();
    private File storageDir;
    private File dbUrl;
    private File dbUser;
    private File dbPassword;
    private Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbUrl = new File(props.getProperty("db.url"));
            dbUser = new File(props.getProperty("db.user"));
            dbPassword = new File(props.getProperty("db.password"));
            storage=new SqlStorage(props.getProperty("db.url"),props.getProperty("db.user"),props.getProperty("db.password"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IllegalArgumentException("Некорректный конфигурационный файл " + PROPS.getAbsolutePath());
        }

    }

    public File getStorageDir() {
        return storageDir;
    }

    public File getDbUrl() {
        return dbUrl;
    }

    public File getDbUser() {
        return dbUser;
    }

    public File getDbPassword() {
        return dbPassword;
    }

    public Storage getStorage() {
        return storage;
    }
}
