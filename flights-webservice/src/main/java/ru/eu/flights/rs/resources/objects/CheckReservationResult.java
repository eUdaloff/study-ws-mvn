package ru.eu.flights.rs.resources.objects;

import ru.eu.flights.objects.Reservation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "checkReservationResult")
@XmlAccessorType(XmlAccessType.NONE)
public class CheckReservationResult {

    @XmlElement(name = "reservation")
    private Reservation reservation;

    public CheckReservationResult() {
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
