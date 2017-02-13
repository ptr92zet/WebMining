package ptr.studies.java.webmining.bot;

import java.awt.EventQueue;

public class NetBotApplication {
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NetBotMainWindow window = new NetBotMainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
