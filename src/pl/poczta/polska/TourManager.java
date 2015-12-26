package pl.poczta.polska;

import pl.poczta.polska.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class TourManager {

    // Holds our cities
    private static List<City> destinationCities = new ArrayList<>();

    public static void setCities(List<City> cities) {
        destinationCities = cities;
    }

    // Get a city
    public static City getCity(int index) {
        return destinationCities.get(index);
    }

    // Get the number of destination cities
    public static int numberOfCities() {
        return destinationCities.size();
    }
}