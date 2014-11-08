package com.przemo.ballsgame;

import com.przmnwck.mavengubas.FormsCaller;

/**
 * Hello world!
 */
public class App 
{
    public static SymbolsHolder[] sh = new SymbolsHolder[]{new SymbolsHolder(1, "O"), new SymbolsHolder(2, "X")};
    public static void main(String[] args) {

            BallsFrame bfr = new BallsFrame(sh);
            FormsCaller.callNewMainWindow("Balls", bfr);
    }
   
}
