package com.mahantysandeep.client;


import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by smahanty on 3/31/17.
 */
public class HTTPClient {

    public String get(String resourceUrl, Protocol protocol) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (resourceUrl == null || resourceUrl.trim().length() == 0) {
            throw new MalformedURLException("Invalid url specified. Please check url again!");
        }
        return makeGet(resourceUrl, protocol);
    }

    public String get(String resourceUrl) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (resourceUrl == null || resourceUrl.trim().length() == 0) {
            throw new MalformedURLException("Invalid url specified. Please check url again!");
        }
        return makeGet(resourceUrl, Protocol.HTTP);
    }

    private String makeGet(String resourceUrl, Protocol protocol) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        System.out.println("~~~~~~ ResourceURL : " + resourceUrl);
        URL url = new URL(resourceUrl);
        if (protocol.getValue().equals(Protocol.HTTPS.getValue())) {
            HttpsURLConnection httpsURLConnection = getHttpsURLConnection(url);
            httpsURLConnection.setRequestMethod("GET");
            return readResponse(httpsURLConnection.getInputStream());
        } else {
            HttpURLConnection httpURLConnection = getHttpURLConnection(url);
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

    private HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        return httpURLConnection;
    }

    private HttpsURLConnection getHttpsURLConnection(URL url) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs,
                                           String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs,
                                           String authType) {
            }

        }};
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        return httpsURLConnection;
    }
}
