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
    
    public static boolean isPrime(int n){
        int limit = (int) (Math.sqrt(n)+1);
        for(int i = 2; i<limit; i++){
            if(n%i==0){
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
}
