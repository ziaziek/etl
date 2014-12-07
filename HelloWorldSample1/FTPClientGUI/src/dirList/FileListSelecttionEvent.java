/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Przemo
 */
public class FileListSelecttionEvent extends ListSelectionEvent {

    FileListItem itemSource = null;

    public FileListItem getItemSource() {
        return itemSource;
    }
    
    protected FileListSelecttionEvent(Object source, int firstIndex, int lastIndex, boolean isAdjusting) {
        super(source, firstIndex, lastIndex, isAdjusting);
    }
    
    public FileListSelecttionEvent(FileListItem sourceItem){
        super(sourceItem, -1, -1, false);
        this.itemSource = sourceItem;
    }
    
}
