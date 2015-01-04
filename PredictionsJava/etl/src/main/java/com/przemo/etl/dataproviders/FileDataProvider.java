/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.dataproviders;

import com.przemo.etl.interfaces.IDataProvider;
import java.io.File;

/**
 *
 * @author Przemo
 */
public abstract class FileDataProvider implements IDataProvider{

    File file;
    
    public FileDataProvider(String filename){
        file = new File(filename);
    }
    
}
