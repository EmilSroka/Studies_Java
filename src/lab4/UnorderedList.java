package lab4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {
    List<ListItem> items = new ArrayList<ListItem>();

    public void addItem(ListItem item){
        items.add(item);
    }

    public void addItem(String text){
        ListItem item = new ListItem(text);
        addItem(item);
    }

    public void writeHTML(PrintStream out){
        out.println("<ul>");
        for(ListItem item: items){
            item.writeHTML(out);
        }
        out.println("</ul>");
    }
}
