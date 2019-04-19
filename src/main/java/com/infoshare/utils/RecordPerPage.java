package com.infoshare.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RecordPerPage {

    public static Integer readProperties() throws FileNotFoundException {

        Integer recordPerPage;

        try {
            Properties properties = new Properties();
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource("settings.properties")
                    .openStream();
            properties.load(inputStream);
            recordPerPage = Integer.parseInt(properties.getProperty("records-per-page"));
        } catch (
                IOException e) {
            throw new FileNotFoundException();
        }
        return recordPerPage;
    }

}
