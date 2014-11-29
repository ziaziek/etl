/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Przemo
 */
public class FileListItem extends JLabel{
    
    private String label;

    public String getLabel() {
        return label;
    }
    
    public FileListItem(String label, ImageIcon image){
        this.setText(label);
        this.setIcon(image);
        setPreferredSize(new Dimension(90, 25));
    }
    
}
