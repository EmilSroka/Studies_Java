package uLab1;

import java.util.Scanner;

public class PeselProgram {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String pesel = scanner.next();
        if(Pesel.check(pesel)){
            System.out.print("Poprawny pesel");
        } else {
            System.out.print("Niepoprawny pesel");
        }
    }
}
