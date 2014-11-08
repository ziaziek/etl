/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.ballsgame;

/**
 *For the purpose of this excercise, the symbols will be strings which then will be rendered as texts.
 * Normally, these would be colours or other objects or shapes
 * @author Przemo
 */
public class SymbolsHolder {
    protected int player;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    protected String symbol;
    
    public SymbolsHolder(){
        
    }
    
    public SymbolsHolder(int player, String symbol){
        this.player=player;
        this.symbol=symbol;
    }
    
    @Override
    public String toString(){
        return getSymbol();
    }
}
