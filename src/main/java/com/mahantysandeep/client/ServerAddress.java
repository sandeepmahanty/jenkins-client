package com.mahantysandeep.client;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by smahanty on 3/31/17.
 */
public class ServerAddress {
    private final String host;
    private final int port;
    volatile InetSocketAddress _address;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public ServerAddress() throws UnknownHostException, IllegalArgumentException {
        this(defaultHost(), defaultPort());
    }

    public ServerAddress(String host, int port) throws UnknownHostException, IllegalArgumentException {
        if (host == null) {
            host = defaultHost();
        }
        host = host.trim();
        if (host.length() == 0) {
            host = defaultHost();
        }

        int idx = host.indexOf(":");
        if (idx > 0) {
            if (port != defaultPort())
                throw new IllegalArgumentException("can't specify port in construct and via host");
            port = Integer.parseInt(host.substring(idx + 1));
            host = host.substring(0, idx).trim();
        }
        this.host = host;
        this.port = port;
        updateInetAddress();
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }

    private static String defaultHost() {
        return "127.0.0.1";
    }

    private static int defaultPort() {
        return 8080;
    }

    boolean updateInetAddress() throws UnknownHostException {
        InetSocketAddress oldAddress = _address;
        _address = new InetSocketAddress(InetAddress.getByName(host), port);
        return !_address.equals(oldAddress);
    }

    public boolean sameHost(String host) {
        int idx = host.indexOf(":");
        int port = defaultPort();
        if (idx > 0) {
            port = Integer.parseInt(host.substring(idx + 1));
            host = host.substring(0, idx);
        }

        return
                port == port &&
                        host.equalsIgnoreCase(host);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ServerAddress) {
            ServerAddress a = (ServerAddress) other;
            return
                    a.port == port &&
                            a.host.equals(host);
        }
        if (other instanceof InetSocketAddress) {
            return _address.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return host.hashCode() + port;
    }
}
