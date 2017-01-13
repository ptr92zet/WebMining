package ptr.studies.java.webmining.wordsorter;

import java.awt.EventQueue;

public class WordSorterApplication {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WordSorterMainWindow window = new WordSorterMainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
