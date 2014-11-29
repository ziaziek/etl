/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Przemo
 */
public class DefaultFilesListRenderer implements IListFileRenderer{
    
    protected final int DEFAULT_NUMBER_OF_ROWS = 10;
    
    protected final int DEFAULT_NUMBER_OF_COLUMNS = 2;
    
    private int rows = DEFAULT_NUMBER_OF_ROWS;

    private int currentX=0;
    private int currentY=0;
    
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    JPanel rPan = null;
    
    public DefaultFilesListRenderer(JPanel renderedPanel){
        this.rPan=renderedPanel;
        rPan.setLayout(new FlowLayout());
    }
    
    
    private int renderItem(FileListItem item, int numberInRow){
        item.setLocation(currentX, currentY);
        rPan.add(item);
        currentY+=item.getPreferredSize().getHeight()+5;
        return checkLocationConstraints(numberInRow);
    }
    
    private int checkLocationConstraints(int numberInRow){
        if(numberInRow>rows){
            currentY=0;
            currentX=100; //move to another column, start from the top
            return 0;
        }
        return numberInRow;
    }
    
    @Override
    public void render(String[] dirs, String[] files){
        rPan.removeAll();
        int nrow=0;
        for(String d: dirs){
            nrow = renderItem(new FileListItem(d, new ImageIcon("/resources/folder_icon.png")), ++nrow);
        }
        for(String f: files){          
            nrow = renderItem(new FileListItem(f, new ImageIcon("/resources/file_icon.png")), ++nrow);            
        }
        rPan.repaint();
    }

    @Override
    public void setRenderedControl(JPanel ctrl) {
        this.rPan=ctrl;
    }
    
}
