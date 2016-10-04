package ru.eu.flights.objects.spr;

import ru.eu.flights.objects.adapter.CharacterAdapter;
import ru.eu.flights.ws.annotations.ExceptionMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@ExceptionMessage(message = "Не указано место")
public class Place {

    private long id;

    @XmlJavaTypeAdapter(CharacterAdapter.class)
    private Character seatLetter;
    private int seatNumber;
    private FlightClass flightClass;
    private boolean busy;

    public Place() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Character getSeatLetter() {
        return seatLetter;
    }

    public void setSeatLetter(Character seatLetter) {
        this.seatLetter = seatLetter;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public FlightClass getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(FlightClass flightClass) {
        this.flightClass = flightClass;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", seatLetter=" + seatLetter +
                ", seatNumber=" + seatNumber +
                ", flightClass=" + flightClass +
                ", busy=" + busy +
                '}';
    }
}
