package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton ourInstance = new TestSingleton();

    public static TestSingleton getInstance() {
        return ourInstance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        Singleton instance=Singleton.valueOf("INSTANCE");
        System.out.println(instance);
        Week week=Week.valueOf("Friday");
        System.out.println(instance.ordinal());
        for(SectionType type: SectionType.values()){
            System.out.println(type+" "+type.getTittle());
        }
    }

    public enum Singleton{
        INSTANCE
    }
    public enum Week{
        Monday,
        Friday
    }
}
