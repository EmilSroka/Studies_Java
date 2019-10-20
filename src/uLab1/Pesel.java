package uLab1;

public class Pesel {
    static private int[] weights = {9, 7, 3, 1, 9, 7, 3, 1, 9, 7};

    static public boolean check(String pesel){
        int controlNumber = 0;
        for(int index=0; index<10; index++){
            int number = getDigit(index, pesel);
            controlNumber += number * weights[index];
        }
        return controlNumber % 10 == getDigit(10, pesel);
    }

    static private int getDigit(int position, String text){
        return Character.getNumericValue(text.charAt(position));
    }
}
