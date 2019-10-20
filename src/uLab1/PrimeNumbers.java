package uLab1;

public class PrimeNumbers {
    private boolean[] sieve;

    public PrimeNumbers(int last) {
        calculateTo(last);
    }

    public int[] getNumbers(){
        int size = numberOfPrimesNumbers();
        int[] numbers = new int[size];
        int filled = 0;
        for(int number=1; number<=sieve.length; number++){
            if(isPrime(number)){
                numbers[filled] = number;
                filled++;
            }
        }
        return numbers;
    }
    public void calculateTo(int last){
        allocateSieve(last);
        calculatePrimes(last);
    }

    private void calculatePrimes(int max){
        setNumber(1, false);
        for(int checking=2; checking < Math.ceil(Math.sqrt(max)); checking++){
            if(isPrime(checking)){
                for(int notPrime = 2*checking; notPrime <= max; notPrime += checking){
                    setNumber(notPrime, false);
                }
            }
        }
    }

    private void allocateSieve(int size){
        sieve = new boolean[size];
        for(int i=0; i<size; i++){
            sieve[i] = true;
        }

        /* It loops over copy.
        *  So, because boolean is primitive, it copy value.
        *  Because of this, we  can't modifier original value.   */
        //for(boolean isPrime: sieve) {
        //    isPrime = true;
        //}
    }

    private boolean isPrime(int number){
        return sieve[number - 1];
    }

    private void setNumber(int number, boolean isPrime){
        sieve[number - 1] = isPrime;
    }

    private int numberOfPrimesNumbers(){
        int number = 0;
        for(boolean isPrime: sieve){
            if(isPrime) number++;
        }
        return number;
    }
}
