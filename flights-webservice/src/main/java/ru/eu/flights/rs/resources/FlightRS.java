package ru.eu.flights.rs.resources;

import ru.eu.flights.interfaces.impls.BuyImpl;
import ru.eu.flights.interfaces.impls.CheckImpl;
import ru.eu.flights.interfaces.impls.SearchImpl;
import ru.eu.flights.objects.Flight;
import ru.eu.flights.objects.spr.City;
import ru.eu.flights.rs.resources.objects.*;
import ru.eu.flights.proxy.CustomProxySelector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.ProxySelector;
import java.util.Set;

@Path("flights")
@Produces(MediaType.APPLICATION_JSON)
public class FlightRS {

    private SearchImpl search = new SearchImpl();
    private CheckImpl checkImpl = new CheckImpl();
    private BuyImpl buyImpl = new BuyImpl();

    public FlightRS() {
        ProxySelector.setDefault(new CustomProxySelector());
    }

    @GET
    @Path("cities")
    public CityList getAllCities() {
        Set<City> cities = search.getAllCities();
        CityList responseList = new CityList();
        responseList.setCities(cities);
        return responseList;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("searchFlights")
    public FlightList searchFlights(@QueryParam("flightDate") Long flightDate,
                                    @QueryParam("cityIdFrom") Long cityIdFrom,
                                    @QueryParam("cityIdTo") Long cityIdTo) {
        Set<Flight> s = search.searchFlights(flightDate, cityIdFrom, cityIdTo, 1);
        FlightList fl = new FlightList();
        fl.setFlights(s);
        return fl;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("buyTicket")
    public BuyTicketResult buyTicket(BuyTicketParam p) {
        BuyTicketResult b = new BuyTicketResult();
        b.setResult(buyImpl.buyTicket(p.getPassenger(), p.getFlight(), p.getPlace(), p.getAddInfo()));
        return b;
    }

    @GET
    @Path("checkReservationByCode")
    public CheckReservationResult checkReservationByCode(@QueryParam("code") String code) {
        CheckReservationResult r = new CheckReservationResult();
        r.setReservation(checkImpl.checkReservationByCode(code));
        return r;
    }

}
