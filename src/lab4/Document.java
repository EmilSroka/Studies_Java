package lab4;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {
    String title;
    Photo photo;
    List<Section> sections = new ArrayList<Section>();

    Document(String title){
        this.title = title;
    }

    Document setTitle(String title){
        this.title = title;
        return this;
    }

    Document setPhoto(String photoUrl){
        photo = new Photo(photoUrl);
        return this;
    }

    Section addSection(String sectionTitle){
        Section s = new Section(sectionTitle);
        sections.add(s);
        return s;
    }
    Document addSection(Section s){
        sections.add(s);
        return this;
    }

    void writeHTML(PrintStream out){
        out.println("<!doctype>");
        out.println("<html lang=\"pl\">");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println(String.format("<title>%s</title>", title));
        out.println("</head>");
        out.println("<body>");
        out.println(String.format("<h1>%s</h1>", title ));
        photo.writeHTML(out);
        for(Section section: sections){
            section.writeHTML(out);
        }
        out.println("</body>");
        out.println("</html>");
    }
}
