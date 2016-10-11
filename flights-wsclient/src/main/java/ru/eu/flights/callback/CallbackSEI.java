package ru.eu.flights.callback;

import ru.eu.flights.client.generated.City;
import ru.eu.flights.client.generated.Flight;
import ru.eu.flights.client.generated.Reservation;
import ru.eu.flights.object.ExtCity;

import javax.annotation.Resource;
import javax.jws.*;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebService
@Addressing
@HandlerChain(file = "handler_chain.xml")
public class CallbackSEI {

    public static final String REPLY_TO_ADDRESS = "http://localhost:2016/flightsClientCallback";

    @Resource
    private WebServiceContext context;

    @Oneway
    @WebMethod
    @Action(input = "http://flights.eu.ru/ws/FlightWS/buyTicketResponse")
    @RequestWrapper(localName = "buyTicketResponse", targetNamespace = "http://flights.eu.ru/ws", className = "ru.eu.flights.client.generated.BuyTicketResponse")
    public void buyTicketCallback(@WebParam(name = "return") boolean result) {
        notifyListeners(result, ListenerType.BUY_TICKET);
    }

    @Oneway
    @WebMethod
    @Action(input = "http://flights.eu.ru/ws/FlightWS/getAllCitiesResponse")
    @RequestWrapper(localName = "getAllCitiesResponse", targetNamespace = "http://flights.eu.ru/ws", className = "ru.eu.flights.client.generated.GetAllCitiesResponse")
    public void getAllCitiesCallback(@WebParam(name = "return") List<City> list) {
        List<City> cities = new ArrayList();
        for (City c : list) {
            ExtCity extCity = new ExtCity();
            extCity.setId(c.getId());
            extCity.setCode(c.getCode());
            extCity.setCountry(c.getCountry());
            extCity.setDesc(c.getDesc());
            extCity.setName(c.getName());
            cities.add(extCity);
        }
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City c1, City c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        notifyListeners(cities, ListenerType.ALL_CITIES);
    }

    @Oneway
    @WebMethod
    @Action(input = "http://flights.eu.ru/ws/FlightWS/searchFlightsResponse")
    @RequestWrapper(localName = "searchFlightsResponse", targetNamespace = "http://flights.eu.ru/ws", className = "ru.eu.flights.client.generated.SearchFlightsResponse")
    public void searchFlightsCallback(@WebParam(name = "return") List<Flight> list) {
        notifyListeners(list, ListenerType.SEARCH_FLIGHT);
    }

    @Oneway
    @WebMethod
    @Action(input = "http://flights.eu.ru/ws/FlightWS/checkReservationByCodeResponse")
    @RequestWrapper(localName = "checkReservationByCodeResponse", targetNamespace = "http://flights.eu.ru/ws", className = "ru.eu.flights.client.generated.CheckReservationByCodeResponse")
    public void checkReservationByCodesCallback(@WebParam(name = "return") Reservation reservation) {
        notifyListeners(reservation, ListenerType.CHECK_RESERVATION);
    }

    private void notifyListeners(Object o, ListenerType lt) {
        for (WsResultListener listener : Receptionist.getInstance().getListeners(lt)) {
            listener.notify(o, lt);
        }
    }
}
