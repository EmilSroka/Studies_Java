package lab7;

public class Main {
    public static void main(String[] args){
        AdminUnitList list = new AdminUnitList();
        list.read("admin-units.csv");
        list.fixMissingValues();

        list.sortInPlaceByArea().list(System.out);

        /*
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(list)
                .where(a->a.area>1000)
                .or(a->a.name.startsWith("Sz"))
                .sort((a,b)->Double.compare(a.area,b.area))
                .limit(100);
        query.execute().list(System.out);
*/
        //list.filter(a->a.name.startsWith("Ż")).sortInPlaceByArea().list(System.out);

        //list.filter(a->a.name.startsWith("K")).sortInPlaceByName().list(System.out);

        //list.filter(a->a.adminLevel == 6 && a.parent.name.equals("województwo małopolskie")).sortInPlaceByName().list(System.out);
    }
}
