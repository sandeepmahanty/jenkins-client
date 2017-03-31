package com.mahantysandeep.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mahantysandeep.model.Home;
import com.mahantysandeep.model.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by smahanty on 3/31/17.
 */
public class JenkinsClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsClient.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String API_SUFFIX = "/api/json?pretty=true";
    private final String VIEW_PREFIX = "/view/";
    private JenkinsConfiguration options;

    private HTTPClient httpClient;
    private ServerAddress address;

    private JenkinsClient(ServerAddress address) {
        this.address = address;
        httpClient = new HTTPClient();
    }

    public JenkinsConfiguration getOptions() {
        return options;
    }

    public void setOptions(JenkinsConfiguration options) {
        this.options = options;
    }

    /**
     * Get the primary view of jenkins home page.
     *
     * @return <code>View</code> containing details of primary view.
     */
    public View getPrimaryView() {
        return getJenkinsHome().getPrimaryView();
    }

    /**
     * Get all the available views of jenkins home page.
     *
     * @return List of all views.
     */
    public List<View> getAllViews() {
        Home home = getJenkinsHome();
        return home.getViews();
    }

    public View getView(String viewName) {
        View view = null;
        try {
            String response = httpClient.get(address.getHost() + ":" + address.getPort() + VIEW_PREFIX + viewName + API_SUFFIX, getActiveProtocol());
            LOGGER.info("Got json : " + response);
            view = GSON.fromJson(response, View.class);
        } catch (IOException ex) {
            LOGGER.error("failed to get home page at " + address.toString() + " due to " + ex.getMessage());
        }

        return view;
    }

    /**
     * Get a model representing the jenkins home page.
     *
     * @return <code>Home</code>
     */
    public Home getJenkinsHome() {
        Home home = null;
        try {
            String response = httpClient.get(address.getHost() + ":" + address.getPort() + API_SUFFIX, getActiveProtocol());
            LOGGER.info("Got json : " + response);
            home = GSON.fromJson(response, Home.class);
        } catch (IOException ex) {
            LOGGER.error("failed to get home page at " + address.toString() + " due to " + ex.getMessage());
        }
        return home;
    }

    /**
     * Creates jenkins client with default host and port.
     * defaults: <br/>
     * host = localhost <br/>
     * port = 8080 <br/>
     *
     * @throws UnknownHostException
     */
    public JenkinsClient() throws UnknownHostException {
        this(new ServerAddress());
    }

    /**
     * Creates jenkins client with the given host and port.
     *
     * @param host
     * @param port
     * @throws UnknownHostException
     */
    public JenkinsClient(String host, int port) throws UnknownHostException {
        this(new ServerAddress(host, port));
    }

    private HTTPClient.Protocol getActiveProtocol() {
        if (options == null) {
            return HTTPClient.Protocol.HTTP;
        }
        return options.getProtocol();
    }
}
