package ru.aberezhnoy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URLConnection;

public class UrlParser {
    private static UrlParser instance;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper mapper;

    private UrlParser() {
        this.httpClient = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(RequestConfig.DEFAULT)
                .build();
        this.mapper = new ObjectMapper();
    }

    public static UrlParser getInstance() {
        if (instance == null) {
            instance = new UrlParser();
        }

        return instance;
    }

    public String getImageUrl(String url) {
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
            NasaObject nasaObject = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
            return nasaObject.getUrl();
        } catch (IOException e) {
            throw new RuntimeException("Error");
        }
    }
}
