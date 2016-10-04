package ru.eu.flights.rs.resources.objects;


import ru.eu.flights.objects.Flight;
import ru.eu.flights.objects.Passenger;
import ru.eu.flights.objects.spr.Place;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "buyTicketParam")
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyTicketParam {

    private Passenger passenger;
    private Flight flight;
    private Place place;
    private String addInfo;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }
}
