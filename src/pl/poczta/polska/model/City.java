package pl.poczta.polska.model;

/**
 * Created by Evelan-E6540 on 26.12.2015.
 */
public class City {
    double x;
    double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(City city) {
        double xDistance = Math.abs(x - city.getX());
        double yDistance = Math.abs(y - city.getY());
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
