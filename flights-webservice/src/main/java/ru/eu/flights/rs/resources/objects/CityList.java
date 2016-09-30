package ru.eu.flights.rs.resources.objects;

import ru.eu.flights.objects.spr.City;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "citylist")
@XmlAccessorType(XmlAccessType.NONE)
public class CityList {

    @XmlElement(name = "city")
    private Set<City> cities;

    public CityList() {
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }
}
