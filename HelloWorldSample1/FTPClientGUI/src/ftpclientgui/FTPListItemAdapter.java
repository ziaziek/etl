/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import dirList.FileListItem;
import dirList.FileListItemTypes;
import dirList.IFileListItemAdapter;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Przemo
 */
public class FTPListItemAdapter implements IFileListItemAdapter<FTPFile> {

    @Override
    public FileListItem getFileListItem(FTPFile adaptee) {
        FileListItemTypes t = null;
        if(adaptee.isDirectory()){
            t = FileListItemTypes.DIRECTORY;
        } else if(adaptee.isFile()){
            t = FileListItemTypes.FILE;
        }
        return new FileListItem(adaptee.getName(), t);
    }
    
}
