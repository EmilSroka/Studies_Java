package lab5;

import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {
    List<Node> args = new ArrayList<>();

    Prod(){}
    Prod(Node n1){
        args.add(n1);
    }
    Prod(double c){
        this(new Constant(c));
    }

    Prod(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }
    Prod(double c, Node n){
        this(new Constant(c), n);
    }

    Prod mul(Node node){
        args.add(node);
        return this;
    }

    Prod mul(double c){
        args.add(0, new Constant(c));
        return this;
    }

    @Override
    double evaluate() {
        double result =1;
        for(Node factor: args){
            result *= factor.evaluate();
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
            Node factor = args.get(i);
            if(i != 0){
                builder.append("*");
            }
            if(factor.sign == -1){
                builder.append("(");
            }
            builder.append(factor.toString());if(factor.sign == -1){
                builder.append(")");
            }
        }
        builder.append(after);

        return builder.toString();
    }

    Node diff(Variable var) {
        Sum derivative = new Sum();
        for(int i=0; i<args.size(); i++){
            boolean hasZero = false;
            Prod currentMultiplication = new Prod();
            for(int j=0;j<args.size();j++){
                Node node = args.get(j);

                if(node.isZero()) {
                    hasZero = true;
                }

                if(j==i) {
                    currentMultiplication.mul(node.diff(var));
                } else {
                    currentMultiplication.mul(node);
                }
            }
            if(!hasZero) {
                derivative.add(currentMultiplication);
            }
        }
        return derivative;
    }

    @Override
    boolean isZero() {
        for(Node node:args){
            if(node.isZero()) {
                return true;
            }
        }
        return false;
    }
}