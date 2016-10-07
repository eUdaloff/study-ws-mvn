package ru.eu.flights.callback;

public interface WsResultListener {
    void notify(Object o, ListenerType listenerType);
}
