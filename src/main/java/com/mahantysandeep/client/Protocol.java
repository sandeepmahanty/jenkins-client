package com.mahantysandeep.client;

/**
 * Created by smahanty on 4/1/17.
 */
public enum Protocol {
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