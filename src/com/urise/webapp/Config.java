package com.urise.webapp;

import java.io.*;
import java.util.*;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

public class Config {
    private static final File PROPS = new File(getHomeDir(),"config\\resumes.properties");
   // private static final File PROPS = new File("C:\\basejava\\config\\resumes.properties");

    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage=new SqlStorage(props.getProperty("db.url"),props.getProperty("db.user"),props.getProperty("db.password"));
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Некорректный конфигурационный файл " + PROPS.getAbsolutePath());
        }

    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    private static File getHomeDir(){
        String prop=System.getProperty("homeDir");
        File homeDir=new File(prop==null ? "." : prop);
        if(!homeDir.isDirectory()){
            throw new IllegalStateException(homeDir+" не является директорией");
        }
        return homeDir;
    }
}
