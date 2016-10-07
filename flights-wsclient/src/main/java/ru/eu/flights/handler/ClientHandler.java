package ru.eu.flights.handler;


import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class ClientHandler extends AddressingHandler {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
        System.out.println("Client handler is working..." + (isOutbound ? "Outbound" : "Inbound") + " message");
        if (isOutbound) {
            try {
                SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();
                context.put(AddressingHandler.MESSAGE_ID, getMessageId(header));
                context.setScope(AddressingHandler.MESSAGE_ID, MessageContext.Scope.APPLICATION);
                setReplyTo(header, (String) context.get(AddressingHandler.REPLY_TO));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return true;
    }
}
