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
public class KataPrimeNumbers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int start = 2;
        for(int i=start; i<Integer.MAX_VALUE; i++){
            if(Primes.isPrime(i)){
                System.out.println(i+",");
            }
        }
    }
    
}
