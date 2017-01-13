package ptr.studies.java.webmining.comparedocs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class CompareDocsMainWindow {

    private JFrame frmComparedocsApplication;
    private ArrayList<String> zoologyLinks;
    private ArrayList<String> musicLinks;
    private ArrayList<String> itLinks;

    /**
     * Create the application.
     */
    public CompareDocsMainWindow() {
        initializeLinks();
        initialize();
    }

    private void initializeLinks() {
        zoologyLinks = new ArrayList<>();
        zoologyLinks.add("https://en.wikipedia.org/wiki/Elephant");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Giraffe");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Gorilla");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Bear");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Tiger");
        
        musicLinks = new ArrayList<>();
        musicLinks.add("https://en.wikipedia.org/wiki/Wolfgang_Amadeus_Mozart");
        musicLinks.add("https://en.wikipedia.org/wiki/Johann_Sebastian_Bach");
        musicLinks.add("https://en.wikipedia.org/wiki/Antonio_Vivaldi");
        musicLinks.add("https://en.wikipedia.org/wiki/Ludwig_van_Beethoven");
        musicLinks.add("https://en.wikipedia.org/wiki/Fr%C3%A9d%C3%A9ric_Chopin");
        
        itLinks = new ArrayList<>();
        itLinks.add("https://en.wikipedia.org/wiki/Computer");
        itLinks.add("https://en.wikipedia.org/wiki/Central_processing_unit");
        itLinks.add("https://en.wikipedia.org/wiki/Operating_system");
        itLinks.add("https://en.wikipedia.org/wiki/Operating_system");
        itLinks.add("https://en.wikipedia.org/wiki/Computer_programming");
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmComparedocsApplication = new JFrame();
        frmComparedocsApplication.setTitle("CompareDocs Application");
        frmComparedocsApplication.setBounds(100, 100, 600, 300);
        frmComparedocsApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainPanel = new JPanel();
        frmComparedocsApplication.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(null);
        
        JPanel linksPanel = new JPanel();
        linksPanel.setBounds(12, 13, 560, 128);
        mainPanel.add(linksPanel);
        linksPanel.setLayout(null);
        
        JLabel zoologyLabel = new JLabel("<html><b>Zoology documents:</b></html>");
        zoologyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        zoologyLabel.setBounds(0, 0, 150, 14);
        linksPanel.add(zoologyLabel);
        
        JLabel musicLabel = new JLabel("<html><b>Classical music documents:</b></html>");
        musicLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        musicLabel.setBounds(200, 0, 150, 14);
        linksPanel.add(musicLabel);
        
        JLabel itLabel = new JLabel("<html><b>IT documents:</b></html>");
        itLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        itLabel.setBounds(429, 0, 98, 14);
        linksPanel.add(itLabel);
        
        JLabel zooLink1 = new JLabel("<html><a href=\"" + zoologyLinks.get(0) + "\">Elephant</a></html>");
        zooLink1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        zooLink1.setBounds(10, 20, 150, 14);
        zooLink1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zooLink1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(zoologyLinks.get(0)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(zooLink1);
        
        JLabel zooLink2 = new JLabel("<html><a href=\"" + zoologyLinks.get(1) + "\">Giraffe</a></html>");
        zooLink2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        zooLink2.setBounds(10, 40, 150, 14);
        zooLink2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zooLink2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(zoologyLinks.get(1)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(zooLink2);
        
        JLabel zooLink3 = new JLabel("<html><a href=\"" + zoologyLinks.get(2) + "\">Gorilla</a></html>");
        zooLink3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        zooLink3.setBounds(10, 60, 150, 14);
        zooLink3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zooLink3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(zoologyLinks.get(2)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(zooLink3);
        
        JLabel zooLink4 = new JLabel("<html><a href=\"" + zoologyLinks.get(3) + "\">Bear</a></html>");
        zooLink4.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        zooLink4.setBounds(10, 80, 150, 14);
        zooLink4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zooLink4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(zoologyLinks.get(3)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(zooLink4);
        
        JLabel zooLink5 = new JLabel("<html><a href=\"" + zoologyLinks.get(4) + "\">Tiger</a></html>");
        zooLink5.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        zooLink5.setBounds(10, 100, 150, 14);
        zooLink5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zooLink5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(zoologyLinks.get(4)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(zooLink5);
        
        JLabel musicLink1 = new JLabel("<html><a href=\"" + musicLinks.get(0) + "\">Mozart</a></html>");
        musicLink1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        musicLink1.setBounds(210, 20, 150, 14);
        musicLink1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        musicLink1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(musicLinks.get(0)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(musicLink1);
        
        JLabel musicLink2 = new JLabel("<html><a href=\"" + musicLinks.get(1) + "\">Bach</a></html>");
        musicLink2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        musicLink2.setBounds(210, 40, 150, 14);
        musicLink2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        musicLink2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(musicLinks.get(1)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(musicLink2);
        
        JLabel musicLink3 = new JLabel("<html><a href=\"" + musicLinks.get(2) + "\">Vivaldi</a></html>");
        musicLink3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        musicLink3.setBounds(210, 60, 150, 14);
        musicLink3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        musicLink3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(musicLinks.get(2)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(musicLink3);
        
        JLabel musicLink4 = new JLabel("<html><a href=\"" + musicLinks.get(3) + "\">Beethoven</a></html>");
        musicLink4.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        musicLink4.setBounds(210, 80, 150, 14);
        musicLink4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        musicLink4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(musicLinks.get(3)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(musicLink4);
        
        JLabel musicLink5 = new JLabel("<html><a href=\"" + musicLinks.get(4) + "\">Chopin</a></html>");
        musicLink5.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        musicLink5.setBounds(210, 100, 150, 14);
        musicLink5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        musicLink5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(musicLinks.get(4)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(musicLink5);
        
        
        JLabel itLink1 = new JLabel("<html><a href=\"" + itLinks.get(0) + "\">Computer</a></html>");
        itLink1.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        itLink1.setBounds(410, 20, 150, 14);
        itLink1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        itLink1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(itLinks.get(0)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(itLink1);
        
        JLabel itLink2 = new JLabel("<html><a href=\"" + itLinks.get(1) + "\">CPU</a></html>");
        itLink2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        itLink2.setBounds(410, 40, 150, 14);
        itLink2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        itLink2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(itLinks.get(1)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(itLink2);
        
        JLabel itLink3 = new JLabel("<html><a href=\"" + itLinks.get(2) + "\">Operating system</a></html>");
        itLink3.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        itLink3.setBounds(410, 60, 150, 14);
        itLink3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        itLink3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(itLinks.get(2)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(itLink3);
        
        JLabel itLink4 = new JLabel("<html><a href=\"" + itLinks.get(3) + "\">Programming</a></html>");
        itLink4.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        itLink4.setBounds(410, 80, 150, 14);
        itLink4.setCursor(new Cursor(Cursor.HAND_CURSOR));
        itLink4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(itLinks.get(3)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(itLink4);
        
        JLabel itLink5 = new JLabel("<html><a href=\"" + itLinks.get(4) + "\">Algorithm</a></html>");
        itLink5.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        itLink5.setBounds(410, 100, 150, 14);
        itLink5.setCursor(new Cursor(Cursor.HAND_CURSOR));
        itLink5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(itLinks.get(4)));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
            }
        });
        linksPanel.add(itLink5);
        
        frmComparedocsApplication.setVisible(true);
    }
    
}
