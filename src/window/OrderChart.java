package window;

import algorithm.METADATA;
import algorithm.TimeNode;
import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;


public class OrderChart {
    public static final int BREAK_ID = 666;
    
    public static DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    
    private final CustomBarRenderer render = new CustomBarRenderer();
    private final ArrayList<Paint> colors = new ArrayList<>();
    
    public OrderChart() {
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.PINK);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.DARK_GRAY);
        colors.add(Color.LIGHT_GRAY);
    }
    
    public static void buildDataset(ArrayList<ArrayList<TimeNode>> order){
        /*
        int lastStartTime = 0;
        int whichOneOnMachine = 0;
        for(int m = 0; m < order.size(); ++m){
            for(int i = 0; i < order.get(m).size(); ++i){
                TimeNode tm = order.get(m).get(i);
                if(lastStartTime < tm.startTime){ //adding break
                    //add break;
                    addDatasetEntry(tm.startTime - lastStartTime, BREAK_ID, whichOneOnMachine, m);
                    
                    whichOneOnMachine += 1;
                }
                //add task
                addDatasetEntry(METADATA.getTask(tm.taskID).duration, METADATA.getTask(tm.taskID).jobID, whichOneOnMachine, m);
                    
                whichOneOnMachine += 1;
                lastStartTime = tm.startTime + METADATA.getTask(tm.taskID).duration;
            }
            whichOneOnMachine = 0;
            lastStartTime = 0;
        }*/
    }
    
    private static void addDatasetEntry(int duration, int jobID, int whichOneOnMachine, int machineID){
        dataset.addValue(new DoubleWithID(duration, jobID), Integer.toString(whichOneOnMachine) , "Machine " + machineID);
    }
    
    public JFreeChart buildChart(){
        
        final JFreeChart chart = ChartFactory.createStackedBarChart("Tasks Order", "", "Time", dataset, PlotOrientation.HORIZONTAL, true, true, false);
        
        SubCategoryAxis domainAxis = new SubCategoryAxis("Machine");
        domainAxis.setCategoryMargin(0.05);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainAxis(domainAxis);
        plot.setRenderer(buildRenderer());
        plot.setFixedLegendItems(createLegendItems());
        
        chart.setAntiAlias(true);
        
        return chart;
    }
    
    private Paint getPaint(int whichOne){
        if(whichOne > colors.size()){
            Random rand = new Random();
            return new Color(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }else{
            return colors.get(whichOne);
        }
    }
  
    private CustomBarRenderer buildRenderer(){
        
        render.setSeriesPaint(BREAK_ID, new Color(0,0,0,0));
        
        //for(int i = 0; i < METADATA.JOBS_COUNT; ++i){
        //    render.setSeriesPaint(i, getPaint(i));
       // }

        render.setItemMargin(0.0);

        render.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.HORIZONTAL));
        
        return render;
    }

    private LegendItemCollection createLegendItems() {
        LegendItemCollection result = new LegendItemCollection();
        
        //for(int i = 0; i < METADATA.JOBS_COUNT; ++ i){
        //    result.add(new LegendItem("Job " + i, render.getSeriesPaint(i)));
        //}

        return result;
    }

}