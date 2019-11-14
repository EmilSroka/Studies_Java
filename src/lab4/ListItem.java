package lab4;

import java.io.PrintStream;

public class ListItem {
    String content;

    ListItem(String content){
        this.content = content;
    }

    ListItem(){
        this("");
    }

    public void setContent(String content){
        this.content = content;
    }

    public void writeHTML(PrintStream out){
        out.println(String.format("<li>%s</li>", content));
    }
}
