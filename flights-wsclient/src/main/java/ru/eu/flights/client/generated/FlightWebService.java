
package ru.eu.flights.client.generated;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "FlightWebService", targetNamespace = "http://ws.flights.eu.ru/", wsdlLocation = "http://tvrit10:8080/flights_war_exploded/FlightWebService?wsdl")
public class FlightWebService
    extends Service
{

    private final static URL FLIGHTWEBSERVICE_WSDL_LOCATION;
    private final static WebServiceException FLIGHTWEBSERVICE_EXCEPTION;
    private final static QName FLIGHTWEBSERVICE_QNAME = new QName("http://ws.flights.eu.ru/", "FlightWebService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://tvrit10:8080/flights_war_exploded/FlightWebService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FLIGHTWEBSERVICE_WSDL_LOCATION = url;
        FLIGHTWEBSERVICE_EXCEPTION = e;
    }

    public FlightWebService() {
        super(__getWsdlLocation(), FLIGHTWEBSERVICE_QNAME);
    }

    public FlightWebService(WebServiceFeature... features) {
        super(__getWsdlLocation(), FLIGHTWEBSERVICE_QNAME, features);
    }

    public FlightWebService(URL wsdlLocation) {
        super(wsdlLocation, FLIGHTWEBSERVICE_QNAME);
    }

    public FlightWebService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FLIGHTWEBSERVICE_QNAME, features);
    }

    public FlightWebService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FlightWebService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns FlightWS
     */
    @WebEndpoint(name = "FlightWSPort")
    public FlightWS getFlightWSPort() {
        return super.getPort(new QName("http://ws.flights.eu.ru/", "FlightWSPort"), FlightWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FlightWS
     */
    @WebEndpoint(name = "FlightWSPort")
    public FlightWS getFlightWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.flights.eu.ru/", "FlightWSPort"), FlightWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FLIGHTWEBSERVICE_EXCEPTION!= null) {
            throw FLIGHTWEBSERVICE_EXCEPTION;
        }
        return FLIGHTWEBSERVICE_WSDL_LOCATION;
    }

}
