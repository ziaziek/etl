/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.ballsgame;

import com.przmnwck.games.ballsengine.Engine;
import com.przmnwck.games.ballsengine.EngineEvent;
import com.przmnwck.games.ballsengine.EngineState;
import com.przmnwck.games.ballsengine.interfaces.IEngineListener;
import com.przmnwck.mavengubas.forms.BaseForm;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Przemo
 */
public class BallsFrame extends BaseForm implements IEngineListener {
    
    SymbolsHolder[] sh=null;
    GuiBoard board=null;
    SettingsPanel sPan = null;
    playersPanel playersPanel=null;
    JPanel gamePanel = null;
    
    public BallsFrame(SymbolsHolder[] symbols){
        setPreferredSize(new Dimension(600,500));
        sh=symbols;
        setLayout(new BorderLayout());
        buildGamePsnel();
        buildSettingsPanel();
        setResizable(false);
        add(gamePanel, BorderLayout.CENTER);
    }
    
    private void buildGamePsnel(){
        gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
        gamePanel.setOpaque(false);
    }
    
    private void buildPlayersPanel(){
        //list of players - currently only two
        //TODO: implement list of players creation
        LinkedList<String> pls = new LinkedList<String>();
        pls.add("Player 1");
        pls.add("Player 2");
        playersPanel = new playersPanel(pls);
        gamePanel.add(playersPanel);
    }
    
    private void buildBoard(Engine engine){
        board = new GuiBoard(sh);    
        board.setPreferredSize(new Dimension(300, 300));
            board.setBackground(Color.white);
            gamePanel.add(board);
            board.setBoard(engine.getBoard());
            board.setOpaque(false);
            engine.addListener(board);
            buildPlayersPanel();
            engine.addListener(playersPanel);
            this.validate();
    }
    
    private void buildSettingsPanel() {
            sPan = new SettingsPanel();
            sPan.setPreferredSize(new Dimension(300,150));
            sPan.setOpaque(false);
            add(sPan, BorderLayout.SOUTH);
            sPan.addListener(this);
    }

    public void stateChanged(EngineEvent evt) {    
        if (evt.getState() == EngineState.INIT) {
            if(board!=null){
                this.remove(board);
            }
            board = null;        
            if (evt.getEngine() != null) {
                evt.getEngine().addListener(this);
                buildBoard(evt.getEngine()); 
                if (evt.getEngine().getAutomaticPlayer() == 1) {                  
                    evt.getEngine().ballPlaced(evt.getEngine().getNumberOfPlayers(), new Point(-1,-1)); //run as the last player (without placing the ball) to make a circle to number 1
                }
                playersPanel.setCurrentPlayer(1);
            }
        } else {
            board.repaint();
            System.out.println("Current player evt = "+evt.getPlayer());
            if (evt.getState() != EngineState.WAIT) {
                board.currentPlayer = 1 + evt.getPlayer() % sh.length;
            } 
            
            if (evt.getState() == EngineState.GAMEOVER) {
                showEndMessage("Player " + evt.getPlayer() + " wins!");
            } else {
                if (evt.getState() == EngineState.DRAW) {
                    showEndMessage("Draw!");
                }
            }
        }
        
    }
    
    private void showEndMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
                this.removeAll();
    }
}
