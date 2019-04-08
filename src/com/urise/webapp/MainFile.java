package com.urise.webapp;

import java.io.*;

public class MainFile {
    public static void findFiles(File file,PrintStream output) throws IOException{
        if(file.isDirectory()){
            File[] files=file.listFiles();
            if(files!=null) {
                for (int i=0;i<files.length;i++) {
                    findFiles(files[i], output);
                }
            }
        } else {
            output.println("\t" + file.getCanonicalPath());
        }
    }
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
        File pathFile=new File(".\\src");
        try {
            findFiles(pathFile,System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
