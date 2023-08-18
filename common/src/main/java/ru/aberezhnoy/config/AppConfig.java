package ru.aberezhnoy.config;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private final String API_KEY;
    private final String URL;
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private static AppConfig instance;

    private AppConfig(String filename) {
        Properties properties = initializeProperties(filename);
        this.API_KEY = properties.getProperty("api_key");
        this.URL = properties.getProperty("url");
        this.BOT_NAME = properties.getProperty("bot_name");
        this.BOT_TOKEN = properties.getProperty("bot_token");
    }

    public static AppConfig getInstance(String filename) {
        if (instance == null) {
            instance = new AppConfig(filename);
        }
        return instance;
    }

    private Properties initializeProperties(String filename) {
        final Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(filename));
        } catch (IOException e) {
            throw new RuntimeException("Property file can't be read");
        }

        return properties;
    }

    public String getURL() {
        return URL;
    }

    public String getBotName() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    public String getApiKey() {
        return API_KEY;
    }
}
