package lab7;

import lab6.CSVReader;

import java.io.PrintStream;
import java.util.*;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
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
            double x1, x2, x3, x4, y1, y2, y3, y4;
            try {
                x1 = reader.getDouble("x1"); y1 = reader.getDouble("y1");
                x2 = reader.getDouble("x2"); y2 = reader.getDouble("y2");
                x3 = reader.getDouble("x3"); y3 = reader.getDouble("y3");
                x4 = reader.getDouble("x4"); y4 = reader.getDouble("y4");
            } catch (NumberFormatException e) {
                x1 = Double.NaN; y1 = Double.NaN;
                x2 = Double.NaN; y2 = Double.NaN;
                x3 = Double.NaN; y3 = Double.NaN;
                x4 = Double.NaN; y4 = Double.NaN;
            }


            AdminUnit newUnit = new AdminUnit(name, adminLevel, population, area, density);
            idToAdminUnit.put(id, newUnit);
            units.add(newUnit);
            adminUnitToParentId.put(newUnit, parent);
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

}
