package ru.aberezhnoy;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.aberezhnoy.config.AppConfig;
import ru.aberezhnoy.config.ConfigFactory;

public class MyTelegramBot extends TelegramLongPollingBot {

    private static MyTelegramBot instance;
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final String URL;

    private MyTelegramBot() {
        AppConfig botConfig = ConfigFactory.getAppConfig("/application.properties");
        this.BOT_NAME = botConfig.getBotName();
        this.BOT_TOKEN = botConfig.getBotToken();
        this.URL = botConfig.getURL();

//        try {
//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(this);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        registerBot();
    }

    public static MyTelegramBot getInstance() {
        if (instance == null) {
            instance = new MyTelegramBot();
        }

        return instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String action = update.getMessage().getText().split("\\s")[0];
            long chatId = update.getMessage().getChatId();

            switch (action) {
                case "/help":
                    sendMessage("Привет, я бот NASA! Я высылаю ссылки на картинки по запросу. " +
                            "Напоминаю, что картинки на сайте NASA обновляются раз в сутки", chatId);
                    break;
                case "/image":
                case "/start":
                    sendMessage(ru.aberezhnoy.Factory.getUrlParser().getImageUrl(URL), chatId);
                    break;
                default:
                    sendMessage("Я не понимаю команду :(", chatId);
            }
        }
    }

    private void sendMessage(String msg, long chatId) {
        SendMessage message = new SendMessage();
        message.setText(msg);
        message.setChatId(chatId);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Не смог отправить сообщение");
        }
    }

    public void registerBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
