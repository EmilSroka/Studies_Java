package lab4;

import java.io.PrintStream;

public class Paragraph {
    String content;

    Paragraph(){
        this("");
    }

    Paragraph(String content){
        setContent(content);
    }

    public Paragraph setContent(String content){
        this.content = content;
        return this;
    }

    public void writeHTML(PrintStream out){
        out.println(String.format("<p>%s</p>", content));
    }
}
