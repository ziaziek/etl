/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Przemo
 */
public class DefaultDirectoryListRenderer implements ListCellRenderer<FileListItem> {

    
    
    
    @Override
    public Component getListCellRendererComponent(JList<? extends FileListItem> list, FileListItem value, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon icon = null;
        if(value.getType()== FileListItemTypes.DIRECTORY){
            icon = new ImageIcon(DefaultDirectoryListRenderer.class.getResource("folder_icon16.png"));
        } else {
            icon = new ImageIcon(DefaultDirectoryListRenderer.class.getResource("file_icon16.png"));
        }
        return new FileListItem(value.getLabel(), icon);
    }
    
}
