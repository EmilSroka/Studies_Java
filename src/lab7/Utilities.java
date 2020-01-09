package lab7;

public class Utilities {
    static double earthRadius = 6371;

    public static double haversineFormula(double y1, double x1, double y2, double x2){
        double lat = Math.toRadians(x2 - x1);
        double lon = Math.toRadians(y2 - y1);
        x1 = Math.toRadians(x1); x2 = Math.toRadians(x2);
        double h = Math.pow(Math.sin( lat/2 ),2) +
                   Math.pow(Math.sin( lon/2 ),2) * Math.cos(x1) * Math.cos(x2);

        return 2 * earthRadius * Math.asin( Math.sqrt(h));
    }
}
