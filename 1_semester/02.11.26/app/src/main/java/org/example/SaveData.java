package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SaveData {

    public static void save(Data data, String jsonPath, String logPath)
            throws IllegalAccessException {

        List<String> jsonEntries = new ArrayList<>();
        StringBuilder log = new StringBuilder();
        Random random = new Random();

        for (Field field : Data.class.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            int value = field.getInt(data);

            if (field.isAnnotationPresent(Ok.class)) {
                jsonEntries.add("  \"" + name + "\": " + value);

            } else if (field.isAnnotationPresent(Ugly.class)) {
                int max = field.getAnnotation(Ugly.class).value();
                int randomValue = random.nextInt(max) + 1;
                jsonEntries.add("  \"" + name + "\": " + randomValue);

            } else {
                log.append("Field '").append(name)
                        .append("' = ").append(value).append("\n");
            }
        }

        writeFile(jsonPath, "{\n" + String.join(",\n", jsonEntries) + "\n}");

        if (log.length() > 0) {
            writeFile(logPath, log.toString());
        }
    }

    private static void writeFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Ошибка записи " + path + ": " + e.getMessage());
        }
    }
}
