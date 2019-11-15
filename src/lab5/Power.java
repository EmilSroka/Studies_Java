package lab5;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Power extends Node {
    double power;
    Node argument;

    Power(Node argument, double power){
        this.argument = argument;
        this.power = power;
    }

    @Override
    double evaluate() {
        double argumentVal = argument.evaluate();
        return Math.pow(argumentVal, power);
    }

    @Override
    int getArgumentsCount(){return 1;}

    @Override
    public String toString() {
        DecimalFormat powerFormat = new DecimalFormat("0.#####",new DecimalFormatSymbols(Locale.US));
        StringBuilder builder = new StringBuilder();

        int argumentSign = argument.getSign();
        int argumentCount = argument.getArgumentsCount();
        boolean useBracket = (argumentSign < 0 || argumentCount > 1) ? true : false;
        String signRepresentation = sign < 0 ? "-" : "";
        String argumentRepresentation = argument.toString();

        builder.append(signRepresentation);
        if(useBracket) {
            builder.append("(");
        }
        builder.append(argumentRepresentation);
        if(useBracket) {
            builder.append(")");
        }
        builder.append("^");
        builder.append(powerFormat.format(power));

        return builder.toString();
    }

}