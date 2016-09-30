package ru.eu.flights.rs;

import ru.eu.flights.rs.resources.FlightRS;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
        super();

        try {
            Class<?> myClass = Class.forName("org.eclipse.persistence.Version");
            Method myMethod = myClass.getMethod("getVersion");
            String version = myMethod.invoke(null).toString();
            System.out.println("org.eclipse.persistence.Version is " + version);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();
        resources.add(FlightRS.class);
        return resources;
    }
}