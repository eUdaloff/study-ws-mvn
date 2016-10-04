package ru.eu.flights.rs.resources.objects;

import ru.eu.flights.objects.Flight;
import ru.eu.flights.objects.spr.City;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "flightList")
@XmlAccessorType(XmlAccessType.NONE)
public class FlightList {

    @XmlElement(name = "flights")
    private Set<Flight> flights;

    public FlightList() {
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }
}
