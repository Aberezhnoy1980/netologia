package ru.aberezhnoy.config;

public class ConfigFactory {

    public static AppConfig getAppConfig(String filename) {
        return AppConfig.getInstance(filename);
    }
}
