package ru.eu.flights.interfaces;

import ru.eu.flights.objects.Reservation;

public interface Check {

    Reservation checkReservationByCode(String code);

    Reservation checkReservationByDate(long date);

    Reservation checkReservationByDocNumber(String docNumber);

    Reservation checkReservationByFamilyName(String fn);

}
