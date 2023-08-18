package ru.aberezhnoy.config;

public class Factory {

    public static AppConfig getAppConfig(String filename) {
        return AppConfig.getInstance(filename);
    }
}
