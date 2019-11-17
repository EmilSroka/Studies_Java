package lab4;

import java.io.PrintStream;

public class Photo {
    String url;

    Photo(String url){
        this.url = url;
    }

    void writeHTML(PrintStream out){
        out.printf(format());
    }

    String writeHTML(){
        return format();
    }

    private String format(){
        return String.format("<img src=\"%s\" alt=\"Smiley face\" height=\"42\" width=\"42\"/>\n", url);
    }
}
