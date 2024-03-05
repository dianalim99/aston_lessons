package ru.aston.course.lessons.util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {

    private PropertiesUtil() {
    }
    private static final Properties PROPERTIES = new Properties();

    private static final String PROPERTIES_FILE = "db.properties";

    static {
        loadProperties();
    }
    public static Properties getProperties() {
        return PROPERTIES;
    }

    public static Properties get() {
        return PROPERTIES;
    }
    public static void loadProperties() {
        try (java.io.InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}