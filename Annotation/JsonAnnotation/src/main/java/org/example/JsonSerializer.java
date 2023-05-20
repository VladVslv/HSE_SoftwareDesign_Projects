package org.example;

import org.example.Published;
import org.json.JSONObject;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class JsonSerializer<T> {
    private final Set<Field> publishedFields;

    public JsonSerializer(Class<T> serializedClass) {
        publishedFields = new HashSet<>();
        for(Field field: ReflectionUtils.getAllFields(serializedClass)){
            Published published = field.getAnnotation(Published.class);
            if (published!=null){
                publishedFields.add(field);
            }
        }
    }

    public JSONObject serialize(T o) throws IllegalAccessException {
        JSONObject result = new JSONObject();
        for (Field field: publishedFields){
            result.put(field.getName(),field.get(o));
        }
        return result;
    }
}