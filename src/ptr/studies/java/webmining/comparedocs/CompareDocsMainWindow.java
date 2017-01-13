package ptr.studies.java.webmining.comparedocs;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class CompareDocsMainWindow {

    private JFrame frame;



    /**
     * Create the application.
     */
    public CompareDocsMainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
