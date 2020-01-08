package lab7;

import lab2.Matrix;

import java.util.ArrayList;
import java.util.List;

public class AdminUnit {
    String name;
    int adminLevel;
    double population;
    double area;
    double density;
    AdminUnit parent;
    List<AdminUnit> children = new ArrayList<>();
    BoundingBox bbox = new BoundingBox();

    AdminUnit(String name, int adminLevel, double population, double area, double density, BoundingBox bbox){
        this.name = name;
        this.adminLevel = adminLevel;
        this.population = population;
        this.area = area;
        this.density = density;
        this.bbox = bbox;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Nazwa: "); builder.append(name);
        builder.append(", Typ jednostki: "); builder.append(adminLevel);
        builder.append(", Powierzchnia: "); builder.append(Double.isNaN(area) ? "BRAK DANYCH" : area);
        builder.append(", Populacja: "); builder.append(Double.isNaN(population) ? "BRAK DANYCH" : population);
        builder.append(", Gestosc: "); builder.append(Double.isNaN(density) ? "BRAK DANYCH" : density);
        builder.append("\nIlosc skladowych: "); builder.append(children.size());
        builder.append(", Bbox: "); builder.append(bbox.toString());
        return builder.toString();
    }

    /**
     * Oblicza brakujące dane population oraz density
     * Przyjmie estymację density = parent.density obliczy population = area*density
     */
    public void fixMissingValues(){
        if(this.parent != null) {
            fixMissingValues(this.parent);
        }
    }

    private void fixMissingValues(AdminUnit parent){
        if(! Double.isNaN(parent.density)){
            this.density = Double.isNaN(this.density) ? parent.density : this.density;
            this.population = Double.isNaN(this.population) ? this.area * this.density : this.population;
        } else if(parent.parent != null){
            fixMissingValues(parent.parent);
        }
    }

    boolean isNeighbour(AdminUnit unit, double distance){
        if(unit == this || unit.adminLevel != this.adminLevel) {
            return false;
        }

        if(this.adminLevel < 8){
            return this.bbox.intersects(unit.bbox);
        } else {
            return inRange(distance, unit);
        }
    }

    boolean overlaps(AdminUnit unit, double distance) {
        if(this.adminLevel < 8){
            return unit.bbox.intersects(this.bbox) || unit.bbox.contains(this.bbox);
        } else {
            double width = Utilities.haversineFormula(unit.bbox.xMin, unit.bbox.yMin, unit.bbox.xMax, unit.bbox.yMin);
            double height = Utilities.haversineFormula(unit.bbox.xMin, unit.bbox.yMin, unit.bbox.xMin, unit.bbox.yMax);
            double offset = Math.max(width, height) * 0.51;
            return inRange(distance + offset, unit);
        }
    }

    private boolean inRange(double distance, AdminUnit unit){
        try {
            double unitX = unit.bbox.getCenterX();
            double unitY = unit.bbox.getCenterY();
            double targetX = this.bbox.getCenterX();
            double targetY = this.bbox.getCenterY();
            if(Utilities.haversineFormula(unitX, unitY, targetX, targetY) < distance) {
                return true;
            } else {
                return false;
            }
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
