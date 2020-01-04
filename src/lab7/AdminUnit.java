package lab7;

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

    AdminUnit(String name, int adminLevel, double population, double area, double density){
        this.name = name;
        this.adminLevel = adminLevel;
        this.population = population;
        this.area = area;
        this.density = density;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Nazwa: "); builder.append(name);
        builder.append(" Typ jednostki: "); builder.append(adminLevel);
        builder.append(" Powierzchnia: "); builder.append(Double.isNaN(area) ? "BRAK DANYCH" : area);
        builder.append(" Populacja: "); builder.append(Double.isNaN(population) ? "BRAK DANYCH" : population);
        builder.append(" Gestosc: "); builder.append(Double.isNaN(density) ? "BRAK DANYCH" : density);
        builder.append(" Ilosc skladowych: "); builder.append(children.size());
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
}
