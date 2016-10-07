package ru.eu.flights.client;


import ru.eu.flights.callback.CallbackSEI;
import ru.eu.flights.client.generated.*;
import ru.eu.flights.handler.AddressingHandler;
import ru.eu.flights.handler.ClientHandler;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;

public class FlightWSClient {

    private static FlightWSClient instance;

    private FlightWebService flightService;
    private FlightWS port;
    private CallbackSEI callbackSEI = new CallbackSEI();

    private FlightWSClient() {
        flightService = new FlightWebService();
        port = flightService.getFlightWSPort();
        BindingProvider provider = (BindingProvider) port;
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new ClientHandler());
        provider.getBinding().setHandlerChain(handlerChain);
        provider.getRequestContext().put(AddressingHandler.REPLY_TO, CallbackSEI.REPLY_TO_ADDRESS);
        System.out.println("Publishing client callback service on " + CallbackSEI.REPLY_TO_ADDRESS + "...");
        Endpoint.publish(CallbackSEI.REPLY_TO_ADDRESS, callbackSEI);
    }

    public static FlightWSClient getInstance() {
        if (instance == null)
            instance = new FlightWSClient();
        return instance;
    }


    public void getAllCities() {
        System.out.println("Requesting cities...");
        port.getAllCities();
    }

    public void searchFlights(long date, City from, City to, int placeCount) throws InvalidArgumentMN {
        System.out.println("Requesting search flights...");
        port.searchFlights(date, from, to, placeCount);
    }

    public void buyTicket(Flight flight, Place place, Passenger passenger, String addInfo) throws InvalidArgumentMN {
        System.out.println("Requesting buy ticket...");
        port.buyTicket(passenger, flight, place, addInfo);
    }

    public void checkReservationByCode(String code) throws InvalidArgumentMN {
        System.out.println("Requesting check reservation code...");
        port.checkReservationByCode(code);
    }

}
