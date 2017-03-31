package com.mahantysandeep.client;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by smahanty on 3/31/17.
 */
public class HTTPClient {

    enum Protocol {
        HTTP("http://"),
        HTTPS("https://");
        String value;

        public String getValue() {
            return value;
        }

        Protocol(String protocol) {
            this.value = protocol;
        }
    }

    public String get(String resourceUrl, Protocol protocol) throws IOException {
        if (resourceUrl == null || resourceUrl.trim().length() == 0) {
            throw new MalformedURLException("Invalid url specified. Please check url again!");
        }
        return makeGet(resourceUrl, protocol);
    }

    public String get(String resourceUrl) throws IOException {
        if (resourceUrl == null || resourceUrl.trim().length() == 0) {
            throw new MalformedURLException("Invalid url specified. Please check url again!");
        }
        return makeGet(resourceUrl, Protocol.HTTP);
    }

    private String makeGet(String resourceUrl, Protocol protocol) throws IOException {
        URL url = new URL(protocol.getValue() + resourceUrl);
        if (protocol.getValue().equals(Protocol.HTTPS.getValue())) {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            return readResponse(httpsURLConnection.getInputStream());
        } else {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            return readResponse(httpURLConnection.getInputStream());
        }
    }

    private String readResponse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String content = "";
        StringBuilder sb = new StringBuilder();

        while ((content = reader.readLine()) != null) {
            sb.append(content);
        }
        return sb.toString();
    }
}
