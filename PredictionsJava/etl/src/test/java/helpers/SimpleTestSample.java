package helpers;




import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Przemo
 */
public class SimpleTestSample {
    
    
    public static Table getTable(int randomRows) {
        Table<Integer, String, Object> t = HashBasedTable.create();
        t.put(0, "A1", 1);
        t.put(1, "A1", 3);
        t.row(0).put("A2", 5);
        t.put(0, "A3", 12);
        Random r = new Random();
        for (int i = 2; i < randomRows; i++) {
            t.put(i, "A1", r.nextInt(500));
            t.put(i, "A2", r.nextInt(255));
        }
        return t;
    }
        
        public static Table getTable(){
            return getTable(10000);
        
    }

}
