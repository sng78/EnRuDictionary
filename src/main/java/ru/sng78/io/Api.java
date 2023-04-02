package ru.sng78.io;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

import static ru.sng78.Main.*;

public class Api {
    protected static String GetJson(String yourEnglishWord) throws IOException {
        String query = String.format(URI, YOUR_API_KEY, yourEnglishWord);

        HttpGet request = new HttpGet(query);
        CloseableHttpClient client = HttpClients.createDefault();
        return client.execute(request, new BasicHttpClientResponseHandler());
    }
}
