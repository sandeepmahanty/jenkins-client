package com.mahantysandeep.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.mahantysandeep.base.TestBase;
import com.mahantysandeep.model.View;
import com.mahantysandeep.util.ResourceUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.UnknownHostException;

/**
 * Created by smahanty on 3/31/17.
 */
public class JenkinsViewTest extends TestBase {
    private View expectedView;
    private JenkinsClient jenkinsClient;
    private static final String VIEW = "all";

    @BeforeClass
    public void init() throws UnknownHostException {
        wireMockServer.addStubMapping(
                WireMock.get(
                        WireMock.urlEqualTo("/view/" + VIEW + "/api/json?pretty=true"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withBodyFile("view.json"))
                        .build());
        String viewJson = ResourceUtil.getTestResource("__files/view.json");
        expectedView = gson.fromJson(viewJson, View.class);
        jenkinsClient = new JenkinsClient("localhost", WIREMOCK_PORT);
    }

    @Test
    public void checkCorrectPrimaryView() {
        try {
            View actualView = jenkinsClient.getView(VIEW);
            Assert.assertEquals(actualView, expectedView, "Expected " + expectedView + " but got " + actualView);
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}
