package ru.eu.flights.object;


import ru.eu.webservices.generated.City;

public class ExtCity extends City {

    @Override
    public String toString() {
        return getName();
    }
}
