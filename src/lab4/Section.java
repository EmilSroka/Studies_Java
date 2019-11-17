package lab4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    List<Paragraph> paragraps = new ArrayList<Paragraph>();

    Section(String title){
        this.title = title;
    }

    Section setTitle(String title){
        this.title = title;
        return this;
    }
    Section addParagraph(String paragraphText){
        Paragraph p = new Paragraph(paragraphText);
        paragraps.add(p);
        return this;
    }
    Section addParagraph(Paragraph p){
        paragraps.add(p);
        return this;
    }
    void writeHTML(PrintStream out){
        out.print("<section>\n");
        for(Paragraph paragraph : paragraps){
            paragraph.writeHTML(out);
        }
        out.print("</section>\n");
    }

}
