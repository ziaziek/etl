/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.ballsgame;

import com.przmnwck.games.ballsengine.EngineEvent;
import com.przmnwck.games.ballsengine.EngineState;
import com.przmnwck.games.ballsengine.interfaces.IEngineListener;
import java.awt.Component;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Przemo
 */
public class playersPanel extends javax.swing.JPanel implements IEngineListener{
    
    
    private int numberOfPlayers=0;
    /**
     * Creates new form playersPanel
     * @param players
     */
    public playersPanel(List<String> players) {
        initComponents();
        numberOfPlayers=players.size();
        buildListOfPlayers(players);
    }

    
    private void buildListOfPlayers(List<String> playersList) {
        panPlayers.setLayout(new BoxLayout(panPlayers, BoxLayout.Y_AXIS));
        for (String ps : playersList) {
            JRadioButton rad = new JRadioButton(ps);
            rad.setSelected(false);
            buttonGroup1.add(rad);
            panPlayers.add(rad);
        }
    }
    
    public void setCurrentPlayer(final int p){
        SwingUtilities.invokeLater(new Runnable(){

            public void run() {
                int ix=1;
        for(Component cmp: panPlayers.getComponents()){
            if(cmp instanceof JRadioButton){
                //((JRadioButton)cmp).setSelected(false);
                if(p==ix){
                    ((JRadioButton)cmp).setSelected(true);
                    System.out.println("Settinng selected button for playet no."+p);
                    return;
                }
                ix++;
            }
        }
            }
            
        });
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        panPlayers = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);

        javax.swing.GroupLayout panPlayersLayout = new javax.swing.GroupLayout(panPlayers);
        panPlayers.setLayout(panPlayersLayout);
        panPlayersLayout.setHorizontalGroup(
            panPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        panPlayersLayout.setVerticalGroup(
            panPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panPlayers);

        jLabel1.setText("Current Player");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panPlayers;
    // End of variables declaration//GEN-END:variables

    public void stateChanged(EngineEvent evt) {
        if(evt.getState()==EngineState.CONTINUE){
            setCurrentPlayer(1 + evt.getPlayer() % numberOfPlayers);
        } else {
            if(evt.getState()==EngineState.WAIT){
                //the engine is working on response. We've been notified of that. Set the current player to the engine player
                setCurrentPlayer(evt.getPlayer());
            }
        }
        
    }
}