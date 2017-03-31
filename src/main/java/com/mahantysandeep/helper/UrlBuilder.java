package com.mahantysandeep.helper;

import com.mahantysandeep.client.HTTPClient;
import com.mahantysandeep.client.JenkinsConfiguration;
import com.mahantysandeep.client.Protocol;
import com.mahantysandeep.client.ServerAddress;

import java.net.MalformedURLException;

/**
 * Created by smahanty on 3/31/17.
 */
public class UrlBuilder {

    private String _host;
    private int _port;
    private Protocol _protocol;

    public void set_host(String _host) {
        this._host = _host;
    }

    public void set_port(int _port) {
        this._port = _port;
    }

    public void set_protocol(Protocol _protocol) {
        this._protocol = _protocol;
    }

    public String create() throws MalformedURLException {
        StringBuilder builderUrl = new StringBuilder();
        if (_protocol == null || _protocol.getValue().equals(Protocol.HTTP.getValue())) {
            if (_host != null && _host.length() > 0) {
                builderUrl.append(Protocol.HTTP.getValue());
                builderUrl.append(_host);
                builderUrl.append(":");
                builderUrl.append(String.valueOf(_port));
                System.out.println("~~~~~~~~~~~~~Constructed url : " + builderUrl);
            } else {
                throw new MalformedURLException();
            }
        }
        if (_protocol != null && _protocol.getValue().equals(Protocol.HTTPS.getValue())) {
            if (_host != null && _host.length() > 0) {
                builderUrl.append(_protocol.getValue());
                builderUrl.append(_host);
                builderUrl.append(":");
                builderUrl.append(String.valueOf(443));
                System.out.println("~~~~~~~~~~~~~Constructed url : " + builderUrl);
            } else {
                throw new MalformedURLException();
            }
        }
        return builderUrl.toString();
    }

    public UrlBuilder(ServerAddress address, JenkinsConfiguration options) {
        this._host = address.getHost();
        this._port = address.getPort();
        if (options != null) {
            this._protocol = options.getProtocol();
        }
    }
}
