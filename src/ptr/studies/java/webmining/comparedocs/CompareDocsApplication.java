package ptr.studies.java.webmining.comparedocs;

import java.awt.EventQueue;

public class CompareDocsApplication {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CompareDocsMainWindow window = new CompareDocsMainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
