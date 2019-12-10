package com.bsuir.lagunovskaya.clinic.client;

import com.bsuir.lagunovskaya.clinic.client.ui.frames.LoginFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ClinicClient {

    public static Properties properties = new Properties();

    public static void main(String[] args) {
        initProperties();
       new LoginFrame().setVisible(true);
    }

    private static void initProperties() {
        FileInputStream fis;

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);

        } catch (IOException e) {
            System.err.println("Error during initializing property file");
        }

    }
}
