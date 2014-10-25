/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kataprimenumbers;

/**
 *
 * @author Przemo
 */
public class Primes {
    

    
    /**
     * Checks if the given number is a prime number
     * @param n number to check against
     * @return true, if n is a prime number
     */
    public static boolean isPrime(int n) {
        int limit = (int) (Math.sqrt(n) + 1);
        for (int i = 2; i < limit; i++) {
            if (n != i && n % i == 0) {
                return false;
            }
        }
        return true;
    }

    
    public static int nextPrime(int i) {
        int r = i;
        int additive = 1;
        r += additive;
        while (!Primes.isPrime(r)) {
            r += additive;
        }
        return r;
    }
    
    /**
     * Finds the greatest prime number less than the given maximum value
     * @param maxValue
     * @return 
     */
    public static int previousPrime(int maxValue){
        int r =maxValue-1;
        while(r>1 && !Primes.isPrime(r)){
            r--;
        }
        return r;
    }
}
