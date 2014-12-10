/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

/**
 *
 * @author Przemo
 */
public class DirectoryList extends JComponent implements Serializable, MouseListener {
    
    private PropertyChangeSupport propertySupport = null;
    
    private final JScrollPane scroll ;
    
    private final JList lista;
    
    private String currentFolderPath = null;

    public String getCurrentFolderPath() {
        return currentFolderPath;
    }
    
    public DirectoryList() {
        lista = new JList();
        scroll = new JScrollPane(lista);
        lista.addMouseListener(this);
        lista.setCellRenderer(new DefaultDirectoryListRenderer());
        lista.setLayoutOrientation(JList.VERTICAL_WRAP);
        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
    }
    
    public void setListData(Object[] data){
        lista.setListData(data);
        this.validate();
    }
    
    public void InitialCurrentFolderPath(String p){
        if(currentFolderPath==null){
            currentFolderPath=p;
        }
    }
    
    public void setListRenderer(ListCellRenderer rend){
        lista.setCellRenderer(rend);
    }
    
    public ListModel getModel(){
        return lista.getModel();
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
        if(propertySupport==null){
            propertySupport = new PropertyChangeSupport(this);      
        }     
        propertySupport.addPropertyChangeListener(listener);  
    }
    
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (propertySupport!=null){
           propertySupport.removePropertyChangeListener(listener); 
        }    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(lista.getSelectedValue() instanceof FileListItem){
            updateCurrentPath((FileListItem)lista.getSelectedValue());
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
