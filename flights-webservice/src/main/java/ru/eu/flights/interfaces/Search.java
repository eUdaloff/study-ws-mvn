package ru.eu.flights.interfaces;


import ru.eu.flights.objects.Flight;
import ru.eu.flights.objects.spr.City;

import java.util.Set;

public interface Search {

    Set<Flight> searchFlights(long date, Long cityIdFrom, Long cityIdTo, int placeCount);

    Set<City> getAllCities();

}
