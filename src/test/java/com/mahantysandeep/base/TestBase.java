package com.mahantysandeep.base;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * Created by smahanty on 3/31/17.
 */
public class TestBase {
    protected static final int WIREMOCK_PORT = 9090;
    protected static final int WIREMOCK_SSL_PORT = 443;
    protected static WireMockServer wireMockServer;
    protected static Gson gson;

    @BeforeSuite
    public void setUp() {
        wireMockServer = new WireMockServer(options().port(WIREMOCK_PORT).httpsPort(WIREMOCK_SSL_PORT));
        wireMockServer.start();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @AfterSuite
    public void tearDown() {
        wireMockServer.shutdown();
    }
}
