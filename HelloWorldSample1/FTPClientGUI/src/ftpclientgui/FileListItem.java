/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Przemo
 */
public class FileListItem extends JComponent {
    
    private String label;

    public String getLabel() {
        return label;
    }

    public final void setLabel(String label) {
        this.label = label;
        this.ctrlLabel.setText(label);
    }
    
    private final JLabel ctrlLabel;
    
    public FileListItem(String label, ImageIcon image){
        this.ctrlLabel = new JLabel(label, image, JLabel.HORIZONTAL);
        buildControl();
        setPreferredSize(new Dimension(90, 25));
    }
    
    private void buildControl(){
        this.setLayout(new BorderLayout());
        this.add(ctrlLabel, BorderLayout.CENTER);
    }
}
