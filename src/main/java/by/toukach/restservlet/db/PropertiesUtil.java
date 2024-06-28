package by.toukach.restservlet.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inFile = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inFile);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    //    private static void loadProperties() {
//        try (InputStream inputStream = PropertiesUtil.class.getClassLoader()
//                .getResourceAsStream("application.properties")) {
//            PROPERTIES.load(inputStream);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }


    private PropertiesUtil() {

    }
}
