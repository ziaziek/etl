/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Przemo
 */
public class DefaultFilesListRenderer implements IListFileRenderer{
    
    protected final int DEFAULT_NUMBER_OF_ROWS = 10;
    
    protected final int DEFAULT_NUMBER_OF_COLUMNS = 2;
    
    private int rows = DEFAULT_NUMBER_OF_ROWS;

    protected int currentX=0;
    protected int currentY=0;
    
    GridLayout gl = null;
    
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        gl.setRows(rows);
        if(rPan!=null){
            rPan.revalidate();
        }
    }
    
    protected JPanel rPan = null;
    
    public DefaultFilesListRenderer(JPanel renderedPanel){
        this.rPan=renderedPanel;
        this.rows  = DEFAULT_NUMBER_OF_ROWS;
        this.gl = new GridLayout(rows, DEFAULT_NUMBER_OF_COLUMNS);
        rPan.setLayout(gl);
    }
    
    private void addItems(String[] names, String resourceURL, int availableRows, JComponent[][] matrixToFill){
        int colIndex=0; int rowIndex=0;
        for(String d: names){
            //Have we got a free slot for a new item? If so, add one, otherwise skip to the next row
            while (colIndex < matrixToFill[0].length && matrixToFill[rowIndex][colIndex] != null) {
                rowIndex++;
                if (rowIndex >= availableRows) {
                    rowIndex = 0;
                    colIndex++;
                }
            }
            if(colIndex < matrixToFill[0].length ){
                matrixToFill[rowIndex][colIndex] = new FileListItem(d, new ImageIcon(resourceURL));   
            }         
        }
    }
    @Override
    public void render(String[] dirs, String[] files){
        rPan.removeAll();
        int availableRows = (dirs.length+files.length)/gl.getColumns() + 1;    
        //number of columns is set manually, number of rows is automatic based on the number of items added.
        //Therefore, we create a matrix of (dirs+files)/gl.getColumns + 1
        JComponent[][] cMatrix = new JComponent[availableRows][gl.getColumns()];
        addItems(dirs, "/resources/folder_icon.png", availableRows, cMatrix);
        addItems(files, "/resources/file_icon.png", availableRows, cMatrix);
        //add items following the order in the matrix
        for(int i=0; i<availableRows; i++){
            for(int j=0; j<gl.getColumns(); j++){
                if(cMatrix[i][j]!=null){
                  rPan.add(cMatrix[i][j]);  
                }        
            }
        }      
        rPan.repaint();
    }

    @Override
    public void setRenderedControl(JPanel ctrl) {
        this.rPan=ctrl;
    }
    
}
