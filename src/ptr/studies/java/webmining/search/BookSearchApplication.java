package ptr.studies.java.webmining.search;

import java.awt.EventQueue;

public class BookSearchApplication {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BookSearchMainWindow window = new BookSearchMainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
