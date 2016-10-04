package ru.eu.flights.ws.proxy;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class CustomProxySelector extends ProxySelector {

    @Override
    public List<Proxy> select(URI uri) {
        List<Proxy> list = new ArrayList();
        if (uri.getScheme().toUpperCase().equals("HTTP")) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 3128));
            list.add(proxy);
        }
        System.out.println("Proxy URL list: " + list.toString());
        return list;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        System.err.println("Connection to " + uri + " failed.");
    }
}
