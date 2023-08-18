package ru.aberezhnoy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Utils {

    public static String getUrl(String nasaUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();

        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(nasaUrl))) {
            NasaObject answer = mapper.readValue(response.getEntity().getContent(), NasaObject.class);
            return answer.getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
