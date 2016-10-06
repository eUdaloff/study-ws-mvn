package ru.eu.flights.ws;

import ru.eu.flights.interfaces.impls.BuyImpl;
import ru.eu.flights.interfaces.impls.CheckImpl;
import ru.eu.flights.interfaces.impls.SearchImpl;
import ru.eu.flights.interfaces.sei.FlightSEI;
import ru.eu.flights.objects.Flight;
import ru.eu.flights.objects.Passenger;
import ru.eu.flights.objects.Reservation;
import ru.eu.flights.objects.spr.City;
import ru.eu.flights.objects.spr.Place;
import ru.eu.flights.ws.annotations.ExceptionMessage;
import ru.eu.flights.ws.exceptions.ArgumentException;
import ru.eu.flights.ws.proxy.CustomProxySelector;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.soap.Addressing;
import java.lang.reflect.Field;
import java.net.ProxySelector;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebService(serviceName = "FlightWebService", endpointInterface = "ru.eu.flights.interfaces.sei.FlightSEI")
//@BindingType(value = SOAPBinding.SOAP11HTTP_MTOM_BINDING)
//@HandlerChain(file = "FlightWS_handlers.xml")
@Addressing
public class FlightWS implements FlightSEI {

    private BuyImpl buyImpl = new BuyImpl();
    private SearchImpl searchImpl = new SearchImpl();
    private CheckImpl checkImpl = new CheckImpl();

    public FlightWS() {
        ProxySelector.setDefault(new CustomProxySelector());
    }

    @Override
    public boolean buyTicket(Passenger passenger, Flight flight, Place place, String addInfo) throws ArgumentException {
        delay(2000);
        checkObject(flight, Flight.class);
        checkObject(passenger, Passenger.class);
        checkObject(place, Place.class);
        return buyImpl.buyTicket(passenger, flight, place, addInfo);
    }

    @Override
    public Reservation checkReservationByCode(String code) throws ArgumentException {
        delay(2000);
        if (code == null || code.isEmpty())
            throw new ArgumentException("Code is empty");

        return checkImpl.checkReservationByCode(code);
    }

    public Reservation checkReservationByDate(long date) {
        delay(2000);
        return checkImpl.checkReservationByDate(date);
    }

    public Reservation checkReservationByDocNumber(String docNumber) {
        delay(2000);
        return checkImpl.checkReservationByDocNumber(docNumber);
    }

    public Reservation checkReservationByFamilyName(String fn) {
        delay(2000);
        return checkImpl.checkReservationByFamilyName(fn);
    }

    @Override
    public Set<Flight> searchFlights(Long date, City cityFrom, City cityTo, Integer placeCount) throws ArgumentException {
        delay(2000);
        if (date == null || date <= 0) {
            throw new ArgumentException("Неверная дата отправления");
        }
        checkObject(cityFrom, City.class);
        checkObject(cityTo, City.class);
        if (placeCount == 0 || placeCount < 0) {
            throw new ArgumentException("Неверное место");
        }
        return searchImpl.searchFlights(date, cityFrom.getId(), cityTo.getId(), placeCount);
    }

    @Override
    public Set<City> getAllCities() {
        return searchImpl.getAllCities();
    }

    private void checkObject(Object object, Class<?> c) throws ArgumentException {
        if (object == null && c.isAnnotationPresent(ExceptionMessage.class)) {
            throw new ArgumentException(c.getAnnotation(ExceptionMessage.class).message());
        }
        for (Field field : c.getDeclaredFields()) {
            if (field.isAnnotationPresent(XmlElement.class)) {
                try {
                    field.setAccessible(true);
                    if (field.getAnnotation(XmlElement.class).required()
                            && (field.get(object) == null || field.get(object).equals(""))) {
                        throw new ArgumentException(field.getAnnotation(ExceptionMessage.class).message());
                    }
                } catch (IllegalAccessException e) {
                    Logger.getLogger(FlightWS.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
