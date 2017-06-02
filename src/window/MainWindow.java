/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author arsc
 */
public class MainWindow {

    
    private JTextArea console = new JTextArea();
    private JScrollPane consoleScroll = new JScrollPane(console);
    private DefaultCaret consoleCaret = (DefaultCaret)console.getCaret();
    
    public MainWindow() throws IOException {
        JFrame guiFrame = new JFrame();
        
        //maximialize window
        guiFrame.setExtendedState(guiFrame.getExtendedState() | JFrame.MAXIMIZED_VERT | JFrame.MAXIMIZED_HORIZ);
        
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Tasks Scheduling");
        guiFrame.setSize(400, 300);
        
        //position window in a center of the screen
        guiFrame.setLocationRelativeTo(null);
        
        //set layout
        guiFrame.setLayout(new BorderLayout());
        
        //auto-scroll to bottom
        consoleCaret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        
        //add console to JFrame
        guiFrame.add(consoleScroll, BorderLayout.CENTER);
        
        
        
        
        //redirect output to console
        redirectSystemStreams();
        
        //close app when closing window
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //make sure the JFrame is visible
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
    
}
