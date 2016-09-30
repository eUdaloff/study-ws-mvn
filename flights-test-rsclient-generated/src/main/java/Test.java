import ru.eu.webservices.generated.City;
import ru.eu.webservices.generated.FlightsWS_REST;

public class Test {

    public static void main(String[] args) {
        for(City c :FlightsWS_REST.flights().cities().getAsCityList().getCity()) {
            System.out.println(c.getName());
        }
    }
}
