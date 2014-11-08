/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
 * Symbols have as many entries as there are players in the game
 */
package com.przemo.ballsgame;

import com.przmnwck.games.ballsengine.Board;
import com.przmnwck.games.ballsengine.EngineEvent;
import com.przmnwck.games.ballsengine.EngineState;
import com.przmnwck.games.ballsengine.interfaces.IEngineListener;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Przemo
 */
public class GuiBoard extends JPanel implements  MouseListener, MouseMotionListener, IEngineListener {

    private SymbolsHolder[] symbols=null;
    Rectangle borders=null;
    Board board=null;
    private Cursor crs=null;
    private boolean movePossible, moveAvailable=true;
    int currentPlayer=1;
    
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
    public GuiBoard(SymbolsHolder[] sh){
        symbols=sh;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    protected void drawBoard(Board b, Graphics g){
        Rectangle r = getBorders();
        int x = r.x;
        int y = r.y;
        g.drawRect(x,y , r.width, r.height);
        for(int rows=0; rows<b.getSize()-1; rows++){
            y=(int) (r.y+(rows+1)*r.getWidth()/b.getSize());
            g.drawLine(r.x, y, r.x + r.width, y);
        }
        for (int cols = 0; cols < b.getSize() - 1; cols++) {
            x =(int) (r.x+ (cols + 1) * r.getHeight() / b.getSize());
            g.drawLine(x, r.y, x, r.y+ r.height);
        }
        for(int i=0; i<b.getSize();i++){
            for(int j=0; j<b.getSize();j++){
                drawSymbol(g, b.getFields()[i][j], i, j);
            }
        }
        borders=r;
    }

    public Rectangle getBorders() {
        if (borders == null) {
            //margin is arbitrarily set to 5% each side
            double q = 0.05;
            Rectangle r = new Rectangle(0,0,Math.min(this.getWidth(), this.getHeight()), Math.min(this.getWidth(), this.getHeight()));
            r.grow((int) (-q * r.width), (int) (-q * r.height));
            borders=r;
        }
        return borders;
    }
    
    protected void drawSymbol(Graphics g,int player, int row, int col){
        if(symbols!=null){
            int ix = 0;
            while(ix<symbols.length && symbols[ix].getPlayer()!=player){
                ix++;
            }
            if(ix<symbols.length){
                Point p = getPointFromCoords(getBoard(), row, col);
                g.setFont(new Font("Arial", Font.PLAIN, (int) (0.72 * getBoardCellWidth(board))));
                g.drawString(symbols[ix].getSymbol(), (int) (p.x + 0.25*getBoardCellWidth(board)),(int) (p.y+ 0.75* getBoardCellHeight(board)));
            }
        }
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        drawBoard(board, g);
    }
    
    public void mouseClicked(MouseEvent e) {
        if(movePossible && moveAvailable){
            Point lastClick=e.getPoint();
            Point r = getCoordsFromPoint(board, lastClick.x, lastClick.y);
            board.placeBall(currentPlayer, r.x, r.y);
        } else {
            e.consume();
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        if(crs!=null){
            this.setCursor(crs);
        }
    }

    private int getBoardCellWidth(Board board){
        if(board!=null){
            return getBorders().width/board.getSize();
        } else {
            return 0;
        }
    }
    
    private int getBoardCellHeight(Board board){
        if(board!=null){
            return getBorders().height/board.getSize();
        } else {
            return 0;
        }
    }
    
    private Point getPointFromCoords(Board b, int row, int col) {
        Point p = new Point();
        if(getBorders()!=null && b!=null){
            p.y=1+getBorders().x+ getBoardCellWidth(board)*row;
            p.x=1+getBorders().y+ getBoardCellHeight(board)*col;
        }
        return p;
    }
    
    private Point getCoordsFromPoint(Board b, int x, int y){
        Point ret = null;//return point will be (row, column)
        if(b!=null && getBorders().contains(new Point(x,y))){
            ret = new Point();
            ret.y= (x - getBorders().x)/getBoardCellWidth(board);
            ret.x= (y - getBorders().y)/getBoardCellHeight(board);
        }
        return ret;
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        Point r = getCoordsFromPoint(board, e.getX(), e.getY());
        if(r!=null && r.x>-1 && r.y>-1 && getBoard().isMovePossible(r.x, r.y)){
          this.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
          movePossible=true;
        } else {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                movePossible=false;
        }
    }

    public void stateChanged(EngineEvent evt) {
        moveAvailable=evt.getState()!=EngineState.GAMEOVER && evt.getState()!=EngineState.WAIT;
        if(evt.getState()==EngineState.WAIT){
            System.out.println("Waiting... movePossible="+movePossible);
        } else {
            System.out.println("Event caught");
        }
        repaint();
        
    }

}
