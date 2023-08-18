package ru.aberezhnoy;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException, TelegramApiException {

        new MyTelegramBot("Netology_java_bot", "6622138656:AAFNRTfDJxoSyGbJDZHXNy3et7yHn55if10");
    }
}
