package ptr.studies.java;

import java.awt.EventQueue;

public class WebMiningApplication {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WebMiningMainWindow window = new WebMiningMainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
