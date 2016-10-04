package ru.eu.flights.rs.resources.objects;


import ru.eu.flights.objects.spr.City;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "searchParam")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchFlightParam {

    private Long date;
    private City from;
    private City to;

    public SearchFlightParam() {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public City getFrom() {
        return from;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    public City getTo() {
        return to;
    }

    public void setTo(City to) {
        this.to = to;
    }
}
