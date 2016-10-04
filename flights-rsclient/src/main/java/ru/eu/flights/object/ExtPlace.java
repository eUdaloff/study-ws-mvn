package ru.eu.flights.object;


import ru.eu.webservices.generated.Place;

public class ExtPlace extends Place {
    @Override
    public String toString() {
        return getSeatLetter() + String.valueOf(getSeatNumber());
    }
}
