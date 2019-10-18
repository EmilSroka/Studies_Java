package lab1;

import java.util.Scanner;

public class P610A {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        if( isOdd(input) ){
            System.out.print(0);
        } else {
            //          B
            //     +---------+
            //  A  |         |  A
            //     +---------+
            //          B
            int halfOfPerimeter = input / 2; // A + B
            int numberOfCombinations = (halfOfPerimeter-1) / 2; // number of sums where: C + D = halfOfPerimeter and C \= D
            System.out.print(numberOfCombinations);
        }
    }

    private static boolean isOdd(int number){
        return number % 2 == 1;
    }

}
