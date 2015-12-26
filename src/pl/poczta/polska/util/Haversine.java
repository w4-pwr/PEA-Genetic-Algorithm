package pl.poczta.polska.util;

import pl.poczta.polska.model.City;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class Haversine {
    private static final double R = 6372.8; // In kilometers

    public static double calculate(City origin, City destination) {
        double latOrigin = origin.getX();
        double lngOrigin = origin.getY();

        double latDest = destination.getX();
        double lngDest = destination.getY();
        return calculate(latOrigin, lngOrigin, latDest, lngDest);
    }

    public static double calculate(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
