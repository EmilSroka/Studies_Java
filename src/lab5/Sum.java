package lab5;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {

    List<Node> args = new ArrayList<Node>();

    Sum(){};

    Sum(Node addend1, Node addend2){
        args.add(addend1);
        args.add(addend2);
    }

    Sum add(Node addend){
        args.add(addend);
        return this;
    }

    Sum add(double c){
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c,n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate() {
        double result =0;
        for(Node addend: args){
            result += addend.evaluate();
        }
        return sign*result;
    }

    int getArgumentsCount(){return args.size();}

    public String toString(){
        StringBuilder builder =  new StringBuilder();
        String before="", after="";
        if(sign < 0){
            before = "-(";
            after = ")";
        }

        builder.append(before);
        for(int i=0; i < args.size(); i++){
            Node addend = args.get(i);
            if(i != 0 && addend.getSign() == 1){
                builder.append(" + ");
            } else {
                builder.append(" ");
            }
            builder.append(addend.toString());
        }
        builder.append(after);

        return builder.toString();
    }


}
