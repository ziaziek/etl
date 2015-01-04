
package com.przemo.etl.transformations;

import com.google.common.base.Function;
import com.google.common.collect.Table;

/**
 *
 * @author Przemo
 */
public class ColumnsAddTransformation extends ColumnTransformation {
    
    /**
     * Define additive transformation. The resulting column value type will be Double. The name of the result column is named 'Result'.
     * If the values in a row are not numbers, the row will be blanked for the result.
     * @param columns 
     */
    public ColumnsAddTransformation(final Object[] columns) {
        this.transformation = new Function() {

            @Override
            public Object apply(Object f) {
                if (f instanceof Table) {
                    Table tin = (Table) f;
                    for (Object row : tin.rowKeySet()) {
                        if (row instanceof Integer && areColumnsNumbers(tin, (Integer)row, columns)) {
                            Double v = 0.0;
                            for (Object c : columns) {
                                v += ((Number) (tin.get(row, c))).doubleValue();
                            }
                            tin.put(row, "Result", v);
                        }
                    }
                }
                return f;
            }
        };
    }
    
    private boolean areColumnsNumbers(Table data, Integer row, Object[] columns) {
        boolean r = true;
            for (Object o : columns) {
                r = r && data.get(row, o) instanceof Number;
            }
        return r;
    }
    
    @Override
    public void setTransformation( Function t){
        //don't allow to do anything. The transformationb function is defined.
    }
}
