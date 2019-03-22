package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.*;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("345", "Ivanov Sergey");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        //TODO: invoke r.toString via reflection
        Method methodToString = r.getClass().getDeclaredMethod("toString");
        String toStringValue = (String) methodToString.invoke(r);
        System.out.println(toStringValue);
        System.out.println(r);
    }
}
