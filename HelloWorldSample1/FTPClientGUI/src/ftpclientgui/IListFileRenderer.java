/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import javax.swing.JPanel;

/**
 *
 * @author Przemo
 */
public interface IListFileRenderer {
    
    void render(String[] directories, String[] files);
    
    void setRenderedControl(JPanel ctrl);
    
}
