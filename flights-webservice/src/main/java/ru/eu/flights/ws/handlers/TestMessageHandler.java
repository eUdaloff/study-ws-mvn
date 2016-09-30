package ru.eu.flights.ws.handlers;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;
import java.util.UUID;

public class TestMessageHandler implements SOAPHandler<SOAPMessageContext> {

    public Set<QName> getHeaders() {
        return null;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        System.out.println("TestMessageHandler is working!");
        boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outbound) {
            QName qname0 = new QName("http://handlers.ws.flights.eu.ru", "uuid", "fl");
            QName qname1 = new QName("value");
            try {
                SOAPMessage msg = context.getMessage();
                SOAPHeaderElement headerElement = msg.getSOAPHeader().addHeaderElement(qname0);
                headerElement.addAttribute(qname1, UUID.randomUUID().toString());
                msg.saveChanges();
            } catch (SOAPException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    public void close(MessageContext context) {

    }
}
