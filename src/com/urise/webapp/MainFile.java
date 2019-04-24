package com.urise.webapp;

import java.io.*;

public class MainFile {

    public static void main(String[] args) {


        System.out.println("-------------------------------");
        System.out.println("Recursive bypass file");

        File pathFile = new File(".\\src");
        try {
            printDirectoriesFiles(pathFile, "");
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

    private static void printDirectoriesFiles(File dir, String against) throws IOException {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(against + " File:" + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(against + "Directory:" + file.getName());
                    against = against.concat(" ");
                    printDirectoriesFiles(file, against);
                }
            }
        }
    }
}
