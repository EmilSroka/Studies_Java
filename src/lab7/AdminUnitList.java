package lab7;

import lab6.CSVReader;

import java.io.PrintStream;
import java.util.*;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    List<AdminUnit> roots = new ArrayList<>();
    Map<Long,AdminUnit> idToAdminUnit = new HashMap<>();

    /**
     * Czyta rekordy pliku i dodaje do listy
     * @param fileName nazwa pliku
     */
    public AdminUnitList read(String fileName){
        CSVReader reader = new CSVReader(fileName, ",", true);
        Map<AdminUnit,Long> adminUnitToParentId = new HashMap<>();
        idToAdminUnit.put(-1L, null);

        while(reader.next()){
            long id = reader.getInt("id");
            long parent;
            try {
                parent = reader.getInt("parent");
            } catch (NumberFormatException e){
                parent = -1; // no parent
            }

            String name = reader.get("name");
            int adminLevel;
            try {
                adminLevel = reader.getInt("admin_level");
            } catch (NumberFormatException e){
                adminLevel = -1;
            }
            double population;
            try {
                population = reader.getDouble("population");
            } catch (NumberFormatException e){
                population = Double.NaN;
            }
            double area;
            try {
                area = reader.getDouble("area");
            } catch (NumberFormatException e) {
                area = Double.NaN;
            }
            double density;
            try {
                density = reader.getDouble("density");
            } catch (NumberFormatException e) {
                density = Double.NaN;
            }
            BoundingBox place = new BoundingBox();
            try {
                double x1, x2, x3, x4, y1, y2, y3, y4;
                x1 = reader.getDouble("x1"); y1 = reader.getDouble("y1");
                x2 = reader.getDouble("x2"); y2 = reader.getDouble("y2");
                x3 = reader.getDouble("x3"); y3 = reader.getDouble("y3");
                x4 = reader.getDouble("x4"); y4 = reader.getDouble("y4");
                place.addPoint(x1, y1);
                place.addPoint(x2, y2);
                place.addPoint(x3, y3);
                place.addPoint(x4, y4);
            } catch (NumberFormatException e) {

            }

            AdminUnit newUnit = new AdminUnit(name, adminLevel, population, area, density, place);
            idToAdminUnit.put(id, newUnit);
            adminUnitToParentId.put(newUnit, parent);
            units.add(newUnit);
            if(newUnit.adminLevel == 4) roots.add(newUnit);
        }
        for(AdminUnit unit : units){
            long parentID = adminUnitToParentId.get(unit);
            AdminUnit parent = idToAdminUnit.get(parentID);
            unit.parent = parent;
            if(parent != null) parent.children.add(unit);
        }
        return this;
    }

    /**
     * Wypisuje co najwyżej limit elementów począwszy od elementu o indeksie offset
     * @param out - strumień wyjsciowy
     * @param offset - od którego elementu rozpocząć wypisywanie
     * @param limit - ile (maksymalnie) elementów wypisać
     */
    void list(PrintStream out, int offset, int limit ){
        for(int i=offset; i < units.size() && i < offset + limit; i++){
            out.println(units.get(i).toString());
        }
    }
    /**
     * Wypisuje zawartość korzystając z AdminUnit.toString()
     * @param out
     */
    void list(PrintStream out){
        list(out, 0, units.size());
    }
    /**
     * Zwraca nową listę zawierającą te obiekty AdminUnit, których nazwa pasuje do wzorca
     * @param pattern - wzorzec dla nazwy
     * @param regex - jeśli regex=true, użyj finkcji String matches(); jeśli false użyj funkcji contains()
     * @return podzbiór elementów, których nazwy spełniają kryterium wyboru
     */
    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList matches = new AdminUnitList();
        for(AdminUnit unit : units){
            boolean match = false;

            if(regex && unit.name.matches(pattern)) {
                match = true;
            } else if(!regex && unit.name.contains(pattern)){
                match = true;
            }

            if(match){
                matches.units.add(unit);
            }
        }
        return matches;
    }
    /**
     * Oblicza dla każdego obiektu AdminUnit, z brakującymi danymi population oraz density
     * Przyjmie estymację density = parent.density obliczy population = area*density
     */
    public AdminUnitList fixMissingValues(){
        for(AdminUnit unit : units){
            unit.fixMissingValues();
        }
        return this;
    }
    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * @param target - jednostka, której sąsiedzi mają być wyznaczeni
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbors(AdminUnit target){
        return getNeighbors(target, 15);
    }
    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * @param target - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxDistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbors(AdminUnit target, double maxDistance){
        AdminUnitList neighbors = new AdminUnitList();
        for(AdminUnit unit : units) {
            if(target.isNeighbour(unit, maxDistance)){
                neighbors.units.add(unit);
            }
        }
        return neighbors;
    }
    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * @param target - jednostka, której sąsiedzi mają być wyznaczeni
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList findNeighbors(AdminUnit target){
        return findNeighbors(target, 15);
    }
    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * @param target - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxDistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList findNeighbors(AdminUnit target, double maxDistance){
        AdminUnitList neighbors = new AdminUnitList();
        List <AdminUnit> mayOverlaps = new ArrayList<>(roots);
        List <AdminUnit> overlapsSearchingElement = new ArrayList<>();
        List <AdminUnit> toCheck = new ArrayList<>();

        while(mayOverlaps.size() != 0){
            for(AdminUnit unit : mayOverlaps){
                if(target.overlaps(unit, maxDistance)){
                    overlapsSearchingElement.add(unit);
                }
            }

            mayOverlaps = new ArrayList<>();
            for(AdminUnit unit: overlapsSearchingElement){
                if(unit.adminLevel == target.adminLevel){
                    toCheck.add(unit);
                } else {
                    for (AdminUnit child : unit.children) {
                        mayOverlaps.add(child);
                    }
                }
            }
            overlapsSearchingElement = new ArrayList<>();
        }

        for(AdminUnit unit : toCheck){
            if(target.isNeighbour(unit, maxDistance)){
                neighbors.units.add(unit);
            }
        }

        return neighbors;
    }

}
