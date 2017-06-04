/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author arsc
 */
public class MainWindow {

    JFrame guiFrame = new JFrame();
    
    private JTextArea console = new JTextArea();
    private JScrollPane consoleScroll = new JScrollPane(console);
    private DefaultCaret consoleCaret = (DefaultCaret)console.getCaret();
    
    //final private NumberAxis xAxis = new NumberAxis();
    //final private NumberAxis yAxis = new NumberAxis();        
    //private LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
    //private Scene graph = new Scene(lineChart, 500, 500);
    private final XYSeriesCollection  dataset = new XYSeriesCollection();
    private static final XYSeries minF = new XYSeries("Min Fitness", false);
    private static final XYSeries avgF = new XYSeries("Avg Fitness", false);
    JFreeChart lineChart = ChartFactory.createXYLineChart("Generations fitnesses", "generation", "fitness", dataset, PlotOrientation.VERTICAL, true, true, false);
    ChartPanel chartPanel = new ChartPanel(lineChart);
    StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
    
    private void setGraphStyle(){
        String fontName = "Lucida Sans";
        theme.setTitlePaint( Color.decode( "#4572a7" ) );
        theme.setExtraLargeFont( new Font(fontName,Font.PLAIN, 16) ); //title
        theme.setLargeFont( new Font(fontName,Font.BOLD, 15)); //axis-title
        theme.setRegularFont( new Font(fontName,Font.PLAIN, 11));
        theme.setRangeGridlinePaint( Color.decode("#C0C0C0"));
        theme.setPlotBackgroundPaint( Color.white );
        theme.setChartBackgroundPaint( Color.white );
        theme.setGridBandPaint( Color.red );
        theme.setAxisOffset( new RectangleInsets(0,0,0,0) );
        theme.setBarPainter(new StandardBarPainter());
        theme.setAxisLabelPaint( Color.decode("#666666")  );
        theme.apply( lineChart );
    }
    
    public MainWindow() throws IOException {
        
        //GridBagConstraints cons = new GridBagConstraints();
        //cons.fill = GridBagConstraints.HORIZONTAL;

        //maximialize window
        guiFrame.setExtendedState(guiFrame.getExtendedState() | JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);
        
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Tasks Scheduling");
        //guiFrame.setSize(400, 300);
        
        //position window in a center of the screen
        guiFrame.setLocationRelativeTo(null);
        
        //set layout
        //guiFrame.setLayout(new BorderLayout());
        //guiFrame.setLayout(new GridBagLayout());
        guiFrame.setLayout(new SpringLayout());
        
        //auto-scroll to bottom
        consoleCaret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        
        //Init XY dataset
        dataset.addSeries(minF);
        dataset.addSeries(avgF);
        
        //set graph style
        setGraphStyle();
  
        //////////////////////////Adding components
        //add graph to JFrame
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setMouseWheelEnabled(true);
        //cons.fill = GridBagConstraints.HORIZONTAL;
        //cons.gridx = 0;
        //cons.gridwidth = 2;
        //cons.gridy = 0;
        guiFrame.add(chartPanel);
        
        //add console to JFrame
        //cons.fill = GridBagConstraints.HORIZONTAL;
        //cons.gridx = 2;
        //cons.gridwidth = 1;
        //cons.gridy = 0;
        //cons.anchor = GridBagConstraints.PAGE_END;
        //consoleScroll.setSize(300, 300);
        //console.setSize(300, 300); === crash on osx
        
        //guiFrame.add(consoleScroll);
        //guiFrame.setLayout(new FlowLayout());
        guiFrame.add(consoleScroll);
        //guiFrame.add(statsPanel);
        
        //guiFrame.add(consoleScroll, FlowLayout.RIGHT);
        
        
        
        //redirect output to console
        redirectSystemStreams();
        
        guiFrame.setVisible(true);
    }

    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                console.append(text);
            }
        });
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                //updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                //updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                ///write(b, 0, b.length);
            }
        };

        //System.setOut(new PrintStream(out, true));
        //System.setErr(new PrintStream(out, true));
    }
    
    public static void addEntryToGraph(double generation, double minF_Fitness, double avgF_Fitness){
        minF.add(generation, minF_Fitness);
        avgF.add(generation, avgF_Fitness);
    }
    
}
