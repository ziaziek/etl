/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;

/**
 *
 * @author Przemo
 */
public class DirFilesList extends JList implements MouseListener{
    
    private String currentFolderPath = null;

    public String getCurrentFolderPath() {
        return currentFolderPath;
    }
    
    public DirFilesList(){
        super.addMouseListener(this);
    }

    public void setInitialCurrentFolderPath(String p){
        if(currentFolderPath==null){
            currentFolderPath=p;
        }
    }
    
    protected void updateCurrentPath(FileListItem item){
        if(item.getType()==FileListItemTypes.DIRECTORY){
                if(item.getLabel().equals("..")){
                    //go up
                    currentFolderPath = currentFolderPath.substring(0, currentFolderPath.lastIndexOf("/")-1);
                } else {
                    //add another level
                   currentFolderPath+=item.getLabel()+"/";
                }
            }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(getSelectedValue() instanceof FileListItem){
            updateCurrentPath((FileListItem)getSelectedValue());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
