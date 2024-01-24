package com.datantt.credito.domain.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class Util {

    public static <T> List<T> mapTo(List<Document> documents, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Document document : documents) {
            T obj = mapTo(document, clazz);
            result.add(obj);
        }
        return result;
    }

    public static <T> T mapTo(Document document, Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            for (String key : document.keySet()) {
                try {
                    Object value = document.get(key);
                    clazz.getDeclaredField(key).set(obj, value.toString());
                } catch (NoSuchFieldException ignored) {
                }
            }
            return obj;
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al convertir el documento a objeto de clase " + clazz.getSimpleName(), e);
        }
    }

}

