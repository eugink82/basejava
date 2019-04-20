package com.urise.webapp;

import java.io.*;

public class MainFile {

    public static void main(String[] args) {
//        String filePath = ".\\.gitignore";
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//        File dir = new File("./src/com/urise/webapp");
//        System.out.println(dir.isDirectory());
//        File[] files = dir.listFiles();
//        if (files != null) {
//            for (File f : files) {
//                if (f.isDirectory())
//                    System.out.println("Директория: " + f);
//                else
//                    System.out.println("Файл: " + f);
//            }
//        }
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            fis.read();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        System.out.println("-------------------------------");
        System.out.println("Recursive bypass file");

        File pathFile = new File(".\\src");
        try {
            printDirectoryAndFiles(pathFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    System.out.println("Directory: " + file.getName());
                    findFiles(f);
                }
            }
        } else {
            System.out.println("File: " + file.getName());
        }
    }

    private static void printDirectoryAndFiles(File dir) throws IOException{
        File[] files=dir.listFiles();

        if(files!=null){
            for(File file: files){
                if(file.isFile()){
                    System.out.println("File:"+file.getName());
                }
                else if(file.isDirectory()){
                    System.out.println("Directory:"+file.getName());
                    printDirectoryAndFiles(file);
                }
            }
        }
    }
}
