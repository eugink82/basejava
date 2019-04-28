package com.urise.webapp.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.model.Sections;


import java.io.*;

public class JsonParser {
   private static Gson GSON= new GsonBuilder()
           .registerTypeAdapter(Sections.class, new JsonSectionAdapter())
           .create();

   public static <T> void write(T object, Writer writer){
       GSON.toJson(object,writer);
   }

   public static <T> T read(Reader reader, Class<T> clazz){
       return GSON.fromJson(reader, clazz);
   }

}
