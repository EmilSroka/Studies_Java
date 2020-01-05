package lab7;

import java.util.Locale;

public class BoundingBox {
    double xMin = Double.NaN;
    double yMin = Double.NaN;
    double xMax = Double.NaN;
    double yMax = Double.NaN;

    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    void addPoint(double x, double y){
        if(isEmpty()){
            xMin = xMax = x;
            yMin = yMax = y;
        } else {
            if(xMin > x) xMin = x;
            if(xMax < x) xMax = x;
            if(yMin > y) yMin = y;
            if(yMax < y) yMax = y;
        }
    }

    /**
     * Sprawdza, czy BB zawiera punkt (x,y)
     * @param x
     * @param y
     * @return
     */
    boolean contains(double x, double y){
        return xMin <= x && xMax >= x && yMin <= y && yMax >= y;
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb){
        return contains(bb.xMin, bb.yMin) && contains(bb.xMax, bb.yMax);
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb){
        return (contains(bb.xMin, bb.yMin) && !contains(bb.xMax, bb.yMax))
            || (!contains(bb.xMin, bb.yMin) && contains(bb.xMax, bb.yMax));
    }

    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb){
        if(isEmpty()){
            this.xMax = bb.xMax;
            this.yMax = bb.yMax;
            this.xMin = bb.xMin;
            this.yMin = bb.yMin;
        } else {
            this.xMax = Math.max(this.xMax, bb.xMax);
            this.yMax = Math.max(this.yMax, bb.yMax);
            this.xMin = Math.min(this.xMin, bb.xMin);
            this.yMin = Math.min(this.yMin, bb.yMin);
        }
        return this;
    }

    /**
     * Sprawdza czy BB jest pusty
     * @return
     */
    boolean isEmpty(){
        return Double.isNaN(xMax);
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     * @return if !isEmpty() współrzędna x środka
     * else wyrzuca wyjątek IllegalAccessException
     */
    double getCenterX() throws IllegalAccessException {
        if(isEmpty()){
            throw new IllegalAccessException("BoundingBox empty");
        }

        return (xMax + xMin) / 2;
    }

    /**
     * Oblicza i zwraca współrzędną y środka
     * @return if !isEmpty() współrzędna y środka
     * else wyrzuca wyjątek IllegalAccessException
     */
    double getCenterY() throws IllegalAccessException {
        if(isEmpty()){
            throw new IllegalAccessException("BoundingBox empty");
        }

        return (yMax + yMin) / 2;
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bb
     * @param bb prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, użyty zostal wzor haversine
     */
    double distanceTo(BoundingBox bb){
        try {
            return Utilities.haversineFormula(this.getCenterX(), this.getCenterY(), bb.getCenterX(), bb.getCenterY());
        } catch (IllegalAccessException e){
            return Double.MAX_VALUE;
        }
    }

    @Override
    public String toString(){
        if(isEmpty()){
            return "[]";
        } else {
            return String.format("[(%f %f), (%f %f)]", this.xMin, this.yMin, this.xMax, this.yMax);
        }
    }

    public String getWKT(){
        return String.format(Locale.US,"LINESTRING(%f %f,%f %f,%f %f,%f %f,%f %f)",
                xMin, yMin,
                xMax, yMin,
                xMax, yMax,
                xMin, yMax,
                xMin, yMin);
    }
}
