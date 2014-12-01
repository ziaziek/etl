/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dirList;

/**
 *
 * @author Przemo
 * @param <T>
 */
public interface IFileListItemAdapter<T> {
    
    FileListItem getFileListItem(T adaptee);
}
