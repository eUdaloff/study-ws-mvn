package ru.eu.flights.rs.resources;

import ru.eu.flights.interfaces.impls.SearchImpl;
import ru.eu.flights.objects.spr.City;
import ru.eu.flights.rs.resources.objects.CityList;
import ru.eu.flights.ws.proxy.CustomProxySelector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.ProxySelector;
import java.util.Set;

@Path("flights")
public class FlightRS {

    private SearchImpl search = new SearchImpl();

//    public FlightRS() {
//        ProxySelector.setDefault(new CustomProxySelector());
//    }

    @GET
    @Path("cities")
    @Produces(MediaType.APPLICATION_JSON)
    public CityList getAllCities() {
        Set<City> cities = search.getAllCities();
        CityList responseList = new CityList();
        responseList.setCities(cities);
        return responseList;
    }
}
