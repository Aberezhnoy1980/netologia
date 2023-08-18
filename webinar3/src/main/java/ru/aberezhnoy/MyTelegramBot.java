package ru.aberezhnoy;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingBot {
    private final String URL = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";
    private final String BOT_NAME;
    private final String BOT_TOKEN;


    public MyTelegramBot(String BOT_NAME, String BOT_TOKEN) throws TelegramApiException {
        this.BOT_NAME = BOT_NAME;
        this.BOT_TOKEN = BOT_TOKEN;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String[] request = update.getMessage().getText().split("\\s");
            String action = request[0];
            long chatId = update.getMessage().getChatId();

            switch (action) {
                case "/help":
                    sendMessage("Привет! Я бот NASA. Введи /start, что бы получить картинку дня", chatId);
                    break;
                case "/image":
                case "/start":
                    String url = Utils.getUrl(URL);
                    sendMessage(url, chatId);
                    break;
                case "/date":
                    url = Utils.getUrl(URL + "&date=" + request[1]);
                    sendMessage(url, chatId);
                    break;
                default:
                    sendMessage("Неизвестная команда", chatId);
                    break;
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
            System.out.println("Не смог отправить сообщение");
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
