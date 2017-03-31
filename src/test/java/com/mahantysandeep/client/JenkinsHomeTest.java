package com.mahantysandeep.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.mahantysandeep.base.TestBase;
import com.mahantysandeep.model.Home;
import com.mahantysandeep.model.View;
import com.mahantysandeep.util.ResourceUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by smahanty on 3/31/17.
 */
public class JenkinsHomeTest extends TestBase {

    private Home expectedHomePage;
    private JenkinsClient jenkinsClient;

    @BeforeClass
    public void init() throws UnknownHostException {
        wireMockServer.addStubMapping(
                WireMock.get(
                        WireMock.urlEqualTo("/api/json?pretty=true"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withBodyFile("home.json"))
                        .build());
        String homeJson = ResourceUtil.getTestResource("__files/home.json");
        expectedHomePage = gson.fromJson(homeJson, Home.class);
        jenkinsClient = new JenkinsClient("localhost", WIREMOCK_PORT);
    }

    @Test
    public void checkCorrectPrimaryView() {
        try {
            View actualView = jenkinsClient.getPrimaryView();
            View expectedView = expectedHomePage.getPrimaryView();
            Assert.assertEquals(actualView, expectedView, "Expected " + expectedView + " but got " + actualView);
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test
    public void checkCorrectViewCount() {
        try {
            List<View> actualViews = jenkinsClient.getAllViews();
            List<View> expectedViews = expectedHomePage.getViews();
            Assert.assertEquals(actualViews.size(), expectedViews.size(), "Expected " + expectedViews.size() + " but got " + actualViews.size());
        } catch (Exception ex) {
            Assert.fail();
        }
    }

    @Test(dependsOnMethods = "checkCorrectViewCount")
    public void checkCorrectViews() {
        try {
            List<View> actualViews = jenkinsClient.getAllViews();
            List<View> expectedViews = expectedHomePage.getViews();
            boolean areAllViewsEqual = true;
            for (int i = 0; i < actualViews.size(); i++) {
                if (!actualViews.contains(expectedViews.get(i))) {
                    areAllViewsEqual = false;
                    break;
                }
            }
            Assert.assertTrue(areAllViewsEqual, "Expected all views to be same but found not all views are same");
        } catch (Exception ex) {
            Assert.fail();
        }
    }
}
