/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.*;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author arsc
 */
public class MainWindow {
    //window
    static JFrame guiFrame = new JFrame();
    //console output
    static private JTextArea console = new JTextArea();
    static private JScrollPane consoleScroll = new JScrollPane(console, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    static private DefaultCaret consoleCaret = (DefaultCaret)console.getCaret();
    //graph
    static private final XYSeriesCollection  dataset = new XYSeriesCollection();
    static private final XYSeries minF = new XYSeries("Min Generation Fitness", false);
    static private final XYSeries avgF = new XYSeries("Avg Generation Fitness", false);
    static JFreeChart lineChart = ChartFactory.createXYLineChart("Generations fitnesses", "generation", "fitness", dataset, PlotOrientation.VERTICAL, true, true, false);
    static ChartPanel chartPanel = new ChartPanel(lineChart);
    //stats
    static JPanel stats = new JPanel();
    static JLabel generationsNumberLabel = new JLabel("Current Generation: ");
    static JTextField generationsNumberField = new JTextField(6);
    static JLabel minimumFitnessLabel = new JLabel("       Minimum Fitness: ");
    static JTextField minimumFitnessField = new JTextField(14);
    static JLabel minimumMakepsanLabel = new JLabel("       Makespan: ");
    static JTextField minimumMakepsanField = new JTextField(6);
    static int hitsCounter = 0;
    static int makespan = Integer.MAX_VALUE;
    static double minTree = Double.MAX_VALUE;
    //styles
    static StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();
    static String fontName = "Lucida Sans";
    //saveing graph
    static JPanel save = new JPanel();
    static JButton saveButton = new JButton("Save generation chart.");
    static JButton openBestPNG = new JButton("Show best graph.");
    static JButton openOrderButton = new JButton("Show order.");
    //saving dialog
    protected static final String EXTENSION = ".png";
    protected static final String EXTENSION_NAME = "png";
    protected static final String FILTER_NAME = ".png files";
    //graph window
    static String bestPath = "";
    
    static public long startTime;
    static public long endTime;

    
    private void setGraphStyle(){
        
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
        //set maximum window size
        guiFrame.setMinimumSize(new Dimension(1500, 900));
        
        //maximialize window
        guiFrame.setExtendedState(guiFrame.getExtendedState() | JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);
        
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Tasks Scheduling");
        
        //position window in a center of the screen
        guiFrame.setLocationRelativeTo(null);
        
        //set layout
        guiFrame.setLayout(new GridBagLayout());
        
        //add listeners for resizing and moving
        guiFrame.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                update();
            }
            
            @Override
            public void componentMoved(ComponentEvent e){
                update();
            }
        });
        //add listener for save button
        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //choose file
                final JFileChooser fc = new JFileChooser();
                //setting up filter
                fc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(FILTER_NAME, EXTENSION_NAME);
                fc.setFileFilter(filter);
                //get status from window
                int status = fc.showSaveDialog(guiFrame);
             
                if(status == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    try {
                        String filename = file.getCanonicalPath();
                        if(!filename.endsWith(EXTENSION)){
                            file = new File(filename + EXTENSION);
                        }
                        ChartUtilities.saveChartAsPNG(file, lineChart, chartPanel.getSize().width, chartPanel.getSize().height);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        //add listener for open button
        openBestPNG.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File(bestPath);
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Cant open PNG file.");
                }
            }
        });
        //show order graph
        openOrderButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFrame orderFrame = new JFrame();
                orderFrame.setMinimumSize(new Dimension(1500, 900));
                orderFrame.setExtendedState(orderFrame.getExtendedState() | JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);
                orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                orderFrame.setTitle("Order graph");
                orderFrame.setLocationRelativeTo(null);
                
                //NumberAxis domainAxis = new NumberAxis(categoryAxisLabel);
                //domainAxis.setAutoRangeIncludesZero(false);

                //ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

                //XYBarRenderer renderer = new ClusteredXYBarRenderer();

                //XYPlot plot = new XYPlot(dataset, domainAxis, valueAxis, renderer);
                //plot.setOrientation(PlotOrientation.VERTICAL);

                //JFreeChart chart = new JFreeChart("asdasds", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
                
                orderFrame.setVisible(true);
            }
        });
        
        
        //////////////////////////Sets components
        //auto-scroll to bottom
        consoleCaret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        
        //Init XY dataset
        dataset.addSeries(minF);
        dataset.addSeries(avgF);
        
        //enable mouse zooming on graph
        chartPanel.setMouseWheelEnabled(true);
        
        //set graph style
        setGraphStyle();
        
        //redirect output to console
        redirectSystemStreams();
        
        //build stats panel
        stats.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc0 = new GridBagConstraints();
        
        generationsNumberLabel.setFont(new Font(fontName,Font.PLAIN, 16));
        minimumFitnessLabel.setFont(new Font(fontName,Font.PLAIN, 16));
        minimumMakepsanLabel.setFont(new Font(fontName,Font.PLAIN, 16));
        
        generationsNumberField.setFont(new Font(fontName,Font.PLAIN, 16));
        minimumFitnessField.setFont(new Font(fontName,Font.PLAIN, 16));
        minimumMakepsanField.setFont(new Font(fontName,Font.PLAIN, 16));
                
        stats.add(generationsNumberLabel, gbc0);
        stats.add(generationsNumberField, gbc0);
        stats.add(minimumFitnessLabel, gbc0);
        stats.add(minimumFitnessField, gbc0);
        stats.add(minimumMakepsanLabel, gbc0);
        stats.add(minimumMakepsanField, gbc0);
        
        
        //Button
        save.setLayout(new GridBagLayout());
        
        saveButton.setFont(new Font(fontName,Font.PLAIN, 16));
        openBestPNG.setFont(new Font(fontName,Font.PLAIN, 16));
        openOrderButton.setFont(new Font(fontName, Font.PLAIN, 16));
        
        //disable button
        openBestPNG.setEnabled(false);
        
        save.add(saveButton, gbc0);
        save.add(openBestPNG, gbc0);
        save.add(openOrderButton, gbc0);
        
        //////////////////////////Adding components
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.anchor = FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        guiFrame.add(chartPanel, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.anchor = FIRST_LINE_END;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        guiFrame.add(consoleScroll, gbc);
        
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.anchor = SOUTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        guiFrame.add(stats, gbc);
        
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.anchor = SOUTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        guiFrame.add(save, gbc);
        
        //show window
        guiFrame.setVisible(true);
        
        //update size of every object in panel
        update();
    }
    
    
    private static CategoryDataset createDataset() {
  String series1 = "First";
  String series2 = "Second";
  String series3 = "Third";

  String category1 = "Category 1";
  String category2 = "Category 2";
  String category3 = "Category 3";
  String category4 = "Category 4";
  String category5 = "Category 5";

  DefaultCategoryDataset dataset = new DefaultCategoryDataset();

  dataset.addValue(1.0D, series1, category1);
  dataset.addValue(4.0D, series1, category2);
  dataset.addValue(3.0D, series1, category3);
  dataset.addValue(5.0D, series1, category4);
  dataset.addValue(5.0D, series1, category5);

  dataset.addValue(5.0D, series2, category1);
  dataset.addValue(7.0D, series2, category2);
  dataset.addValue(6.0D, series2, category3);
  dataset.addValue(8.0D, series2, category4);
  dataset.addValue(4.0D, series2, category5);

  dataset.addValue(4.0D, series3, category1);
  dataset.addValue(3.0D, series3, category2);
  dataset.addValue(2.0D, series3, category3);
  dataset.addValue(3.0D, series3, category4);
  dataset.addValue(6.0D, series3, category5);

  return dataset;
}
    
    private void update(){
        Insets insets = guiFrame.getInsets();
        int left = insets.left;
        int right = insets.right;
        int top = insets.top;
        int bottom = insets.bottom;

        int width = guiFrame.getSize().width - left - right;
        int height = guiFrame.getSize().height - top - bottom;

        chartPanel.setPreferredSize(new Dimension((int) (width * 0.7), (int) (height * 0.95)));
        consoleScroll.setPreferredSize(new Dimension((int) (width * 0.3), (int) (height * 0.95)));
        stats.setPreferredSize(new Dimension((int) (width * 0.7), (int) (height * 0.05)));
        save.setPreferredSize(new Dimension((int)(width * 0.3), (int)(height * 0.05)));      
    }

    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(() -> {
            console.append(text);
        });
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
    
    public static void addEntryToGraph(double generation, double minF_Fitness, double avgF_Fitness){
        minF.add(generation, minF_Fitness);
        avgF.add(generation, avgF_Fitness);
    }

    public static void updateGenerationNumber(int number){
        generationsNumberField.setText(Integer.toString(number));
    }
    
    public static void updateMinimumFitness(double fitness){
        minimumFitnessField.setText(Double.toString(fitness));
    }
    
    public static void updateMinimumMakespan(int span){
        if(span < makespan){
            makespan = span;
        }
        minimumMakepsanField.setText(Integer.toString(makespan));
    }
    
    public static void updateBestPath(String path){
        bestPath = path;
        openBestPNG.setEnabled(true);
    }
    
}
