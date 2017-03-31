package com.mahantysandeep.client;

import com.mahantysandeep.client.ServerAddress;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by smahanty on 3/31/17.
 */
public class ServerAddressTest {

    @Test
    public void incorrectServerAddressTest() {
        try {
            ServerAddress serverAddress = new ServerAddress("localhost:8080", 8090);
        } catch (IllegalArgumentException illegalArgumentException) {
            Assert.assertTrue(true);
        } catch (Exception ex) {
            Assert.assertTrue(false);
        }
    }
}
