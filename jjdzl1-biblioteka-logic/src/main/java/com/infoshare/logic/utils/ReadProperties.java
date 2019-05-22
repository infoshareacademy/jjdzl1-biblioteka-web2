package com.infoshare.logic.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    public static String readPropertie(String key) throws FileNotFoundException {

        String propertie = null;

        try {
            Properties properties = new Properties();
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("settings.properties")
                    .openStream();
            properties.load(inputStream);
            propertie = properties.getProperty(key);
        } catch (
                IOException e) {
            throw new FileNotFoundException();
        }
        return propertie;
    }
}
