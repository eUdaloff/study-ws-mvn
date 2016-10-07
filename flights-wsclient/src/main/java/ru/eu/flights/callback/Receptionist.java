package ru.eu.flights.callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receptionist {

    private static Receptionist instance;
    private Map<ListenerType, List<WsResultListener>> mapListeners = new HashMap<>();

    private Receptionist() {
    }

    public static Receptionist getInstance() {
        if (instance == null)
            instance = new Receptionist();
        return instance;
    }

    public void addListener(WsResultListener l, ListenerType lt) {
        List<WsResultListener> listeners = mapListeners.get(lt);
        if (listeners == null) {
            listeners = new ArrayList<>();
            mapListeners.put(lt, listeners);
        }
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public List<WsResultListener> getListeners(ListenerType lt) {
        return mapListeners.get(lt);
    }
}
