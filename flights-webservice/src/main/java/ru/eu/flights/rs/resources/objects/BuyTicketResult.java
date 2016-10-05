package ru.eu.flights.rs.resources.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "buyTicketResult")
@XmlAccessorType(XmlAccessType.NONE)
public class BuyTicketResult {

    @XmlElement(name = "buyResult")
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
