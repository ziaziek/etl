/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Przemo
 */
public class FileListItem extends JLabel {
    
    private final String label;

    private ImageIcon icon = null;

    private FileListItemTypes type;

    public FileListItemTypes getType() {
        return type;
    }
    
    @Override
    public ImageIcon getIcon() {
        return icon;
    }
    
    public String getLabel() {
        return label;
    }
    
    public FileListItem(String label, FileListItemTypes itemType){
        this.label=label;
        this.type = itemType;
    }
    
    //constructor visible from within the package. Can be used by the renderer.
    FileListItem(String label, ImageIcon image){
        super(label, image, JLabel.CENTER);
        this.label=label;
        this.icon = image;
        setPreferredSize(new Dimension(180,100));
    }
    
}
