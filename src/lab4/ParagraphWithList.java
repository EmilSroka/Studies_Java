package lab4;

import java.io.PrintStream;

public class ParagraphWithList extends Paragraph {
    UnorderedList ul = new UnorderedList();

    ParagraphWithList(){
        super();
    }

    ParagraphWithList(String content){
        super(content);
    }

    ParagraphWithList(UnorderedList list){
        ul = list;
    }

    ParagraphWithList(String content, UnorderedList list){
        super(content);
        ul = list;
    }

    public void setUnorderedList(UnorderedList list){
        ul = list;
    }

    public void writeHTML(PrintStream out){
        super.writeHTML(out);
        ul.writeHTML(out);
    }

    public ParagraphWithList addListItem(String content) {
        ul.addItem(content);
        return this;
    }

    public ParagraphWithList setContent(String content){
        return (ParagraphWithList)(super.setContent(content));
    }
}
