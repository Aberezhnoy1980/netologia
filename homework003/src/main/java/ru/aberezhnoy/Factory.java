package ru.aberezhnoy;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Factory {

    public static MyTelegramBot getMyTelegramBot() {
        return MyTelegramBot.getInstance();
    }

    public static UrlParser getUrlParser() {
        return UrlParser.getInstance();
    }
}
