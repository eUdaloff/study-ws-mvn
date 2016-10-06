package ru.eu.flights.client;

import org.glassfish.jersey.jackson.JacksonFeature;

import ru.eu.flights.object.ExtCity;
import ru.eu.webservices.generated.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;


import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FlightRS_Client {

    private static FlightRS_Client instance;
    private Client client;
    private static final String BASE_URL = "http://localhost:8080/flights-ws/rest";

    private FlightRS_Client() {
        client = ClientBuilder.newClient().register(JacksonFeature.class);
    }

    public static FlightRS_Client getInstance() {
        if (instance == null)
            instance = new FlightRS_Client();
        return instance;
    }

    public List<ExtCity> getAllCities() {
        System.out.println("Requesting cities...");
        CityList cityList = client.target(BASE_URL).path("flights/cities")
                .request(MediaType.APPLICATION_JSON).get(CityList.class);
        List<ExtCity> cities = new ArrayList();
        for (City c : cityList.getCity()) {
            ExtCity extCity = new ExtCity();
            extCity.setId(c.getId());
            extCity.setCode(c.getCode());
            extCity.setCountry(c.getCountry());
            extCity.setDesc(c.getDesc());
            extCity.setName(c.getName());
            cities.add(extCity);
        }
        Collections.sort(cities, new Comparator<City>() {
            public int compare(City c1, City c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return cities;
    }

    public List<Flight> searchFlights(Long flightDate, Long cityIdFrom, Long cityIdTo) {
        System.out.println("Requesting flights...");
        FlightList fl = client.target(BASE_URL).path("flights/searchFlights").queryParam("flightDate", flightDate)
                .queryParam("cityIdFrom", cityIdFrom).queryParam("cityIdTo", cityIdTo)
                .request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .get(FlightList.class);
        return fl.getFlights();
    }

    public boolean buyTicket(Passenger passenger, Flight flight, Place place, String addInfo) {
        System.out.println("Requesting buy ticket operation...");
        BuyTicketParam p = new BuyTicketParam();
        p.setPassenger(passenger);
        p.setFlight(flight);
        p.setPlace(place);
        p.setAddInfo(addInfo);
        BuyTicketResult result = client.target(BASE_URL).path("flights/buyTicket")
                .request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(p, MediaType.APPLICATION_JSON), BuyTicketResult.class);
        return result.isBuyResult();
    }

    public Reservation checkReservationByCode(String code) {
        CheckReservationResult r = client.target(BASE_URL).path("flights/checkReservationByCode").queryParam("code", code)
                .request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .get(CheckReservationResult.class);
        return r.getReservation();
    }

    public void close() {
        client.close();
    }

}
