package uLab1;


import java.util.Scanner;

public class PrimeNumbersProgram {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj liczbe: ");
        int last = scanner.nextInt();

        PrimeNumbers pn = new PrimeNumbers(last);
        int[] primeNumbers = pn.getNumbers();
        for(int primeNumber: primeNumbers){
            System.out.printf("%d ", primeNumber);
        }
    }
}
