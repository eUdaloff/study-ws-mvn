package ru.eu.flights.objects.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CharacterAdapter extends XmlAdapter<String, Character> {

    /*
    * XML => Java
    * Given an XML string, use it to build an instance of the unmappable class.
    */
    @Override
    public Character unmarshal(String v) throws Exception {
        return v.charAt(0);
    }

    /*
    * Java => XML
    * Given the unmappable Java object, return the desired XML representation.
    */
    @Override
    public String marshal(Character v) throws Exception {
        return new String(new char[]{v});
    }

}
