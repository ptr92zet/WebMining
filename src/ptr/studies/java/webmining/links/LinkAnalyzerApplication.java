package ptr.studies.java.webmining.links;

import java.awt.EventQueue;

public class LinkAnalyzerApplication {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LinkAnalyzerMainWindow window = new LinkAnalyzerMainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
