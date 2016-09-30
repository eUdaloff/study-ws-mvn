package ru.eu.webservices;

import ru.eu.webservices.generated.City;
import ru.eu.webservices.generated.CityList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Hello world!
 */
public class TestRSClient {

    public static void main(String[] args) {
        new TestRSClient().run();
    }

    public void run() {
        Client client = ClientBuilder.newClient();

        CityList response = client.target("http://localhost:8080/flights-ws/rest/flights/cities")
                .request(MediaType.APPLICATION_JSON).get(CityList.class);

        for (City c : response.getCity()) {
            System.out.println(c.getName());
        }

        client.close();
    }
}
