package ru.aberezhnoy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        ObjectMapper mapper = new ObjectMapper();

        // get image info
        CloseableHttpResponse response = httpClient.execute(new HttpGet(url));

//        final Scanner sc = new Scanner(response.getEntity().getContent());
//        System.out.println(sc.nextLine());

        NasaObject answer = mapper.readValue(response.getEntity().getContent(), NasaObject.class);

        // download image and save
//        String fileName = "Image" + LocalDate.now() + ".jpg";
        String[] answerSplit = answer.getUrl().split("/");
        String fileName = answerSplit[answerSplit.length - 1];
//        CloseableHttpResponse image = httpClient.execute(new HttpGet(answer.getUrl()));
//        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//        image.getEntity().writeTo(fileOutputStream);

        httpClient.execute(new HttpGet(answer
                        .getUrl()))
                .getEntity()
                .writeTo(new FileOutputStream("webinar2/images/" + fileName));
    }
}