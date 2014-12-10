/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashSet;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Przemo
 */
public class DirectoryList extends JComponent implements Serializable, ListSelectionListener {
    
    private PropertyChangeSupport propertySupport = null;
    
    private HashSet<ListSelectionListener> selectionListeners;
    
    private final JScrollPane scroll ;
    
    private final JList lista;
    
    private String currentFolderPath = null;

    public String getCurrentFolderPath() {
        return currentFolderPath;
    }
    
    public DirectoryList() {
        selectionListeners= new HashSet<>();
        lista = new JList();
        scroll = new JScrollPane(lista);
        lista.addListSelectionListener(this);
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
    
    public void addSelectionListener(ListSelectionListener listener){
        selectionListeners.add(listener);
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
    public void valueChanged(ListSelectionEvent e) {
        if(lista.getSelectedValue() instanceof FileListItem){
            updateCurrentPath((FileListItem)lista.getSelectedValue());
            for(ListSelectionListener l: selectionListeners){
                l.valueChanged(new ListSelectionEvent(this, e.getFirstIndex(), e.getLastIndex(), e.getValueIsAdjusting()));
            }
        }
    }
    
}
