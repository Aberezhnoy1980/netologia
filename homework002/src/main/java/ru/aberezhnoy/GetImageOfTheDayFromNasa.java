package ru.aberezhnoy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.aberezhnoy.config.ConfigFactory;

import java.io.FileOutputStream;
import java.io.IOException;

public class GetImageOfTheDayFromNasa {
    public static void main(String... args) {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.DEFAULT)
                .build();

        HttpGet request = new HttpGet(ConfigFactory.getAppConfig("/application.properties").getURL());

        ObjectMapper mapper = new ObjectMapper();

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            NasaObject answer = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
            httpClient.execute(new HttpGet(getUrl(answer)))
                    .getEntity()
                    .writeTo(new FileOutputStream("homework002/images/" + getFilename(answer)));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't parse response from server");
        }

    }

    private static String getFilename(NasaObject answer) {
        String[] answerValues = getUrl(answer).split("/");
        return answerValues[answerValues.length - 1];
    }

    private static String getUrl(NasaObject answer) {
        return answer.getUrl();
    }
}
