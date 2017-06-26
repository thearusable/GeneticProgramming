/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Paint;
import org.jfree.chart.renderer.category.StackedBarRenderer;

/**
 *
 * @author arsc
 */
public class CustomBarRenderer extends StackedBarRenderer {
    
    @Override
    public Paint getItemPaint(int row, int column) {
        DoubleWithID val = (DoubleWithID) OrderChart.dataset.getValue(row, column);

        return this.getSeriesPaint(val.getID());
    }
    
}
