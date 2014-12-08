/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.swing.JList;

/**
 *
 * @author Przemo
 */
public class DirectoryList extends JList implements Serializable, MouseListener {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    
    private String sampleProperty;
    
    private final PropertyChangeSupport propertySupport;
    
    private String currentFolderPath = null;

    public String getCurrentFolderPath() {
        return currentFolderPath;
    }
    
    public DirectoryList() {
        super.addMouseListener(this);
        propertySupport = new PropertyChangeSupport(this); 
    }
    
    public String getSampleProperty() {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
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
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        //propertySupport.addPropertyChangeListener(listener);
    }
    
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        //propertySupport.removePropertyChangeListener(listener);
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
