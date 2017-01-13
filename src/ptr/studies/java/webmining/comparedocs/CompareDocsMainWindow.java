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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ptr.studies.java.webmining.html.HtmlDownloader;
import ptr.studies.java.webmining.html.HtmlParser;

import javax.swing.JProgressBar;

public class CompareDocsMainWindow {

    private JFrame frmComparedocsApplication;
    private ArrayList<String> zoologyLinks;
    private ArrayList<String> musicLinks;
    private ArrayList<String> itLinks;
    
    private final String HTML_FILE_PREFIX = System.getProperty("user.home") + "\\Desktop\\webmining_docs\\";
    private final String HTML_FILE_SUFFIX = ".html";
    private ArrayList<String> zoologyHtmlFiles;
    private ArrayList<String> musicHtmlFiles;
    private ArrayList<String> itHtmlFiles;
    
    private int downloadProgressCount;
    private int compareProgressCount;
    private int documentsCount;

    private HashMap<String, ArrayList<String>> words;
    
    /**
     * Create the application.
     */
    public CompareDocsMainWindow() {
        initializeLinksAndFiles();
        initialize();
    }

    private void initializeLinksAndFiles() {
        zoologyLinks = new ArrayList<>();
        zoologyLinks.add("https://en.wikipedia.org/wiki/Elephant");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Giraffe");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Gorilla");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Bear");
        zoologyLinks.add("https://en.wikipedia.org/wiki/Tiger");
        zoologyHtmlFiles = new ArrayList<>();
        for (String link : zoologyLinks) {
            int index = link.lastIndexOf("/") + 1;
            String fileName = link.substring(index);
            zoologyHtmlFiles.add(HTML_FILE_PREFIX + fileName + HTML_FILE_SUFFIX);
        }
        
        musicLinks = new ArrayList<>();
        musicLinks.add("https://en.wikipedia.org/wiki/Wolfgang_Amadeus_Mozart");
        musicLinks.add("https://en.wikipedia.org/wiki/Johann_Sebastian_Bach");
        musicLinks.add("https://en.wikipedia.org/wiki/Antonio_Vivaldi");
        musicLinks.add("https://en.wikipedia.org/wiki/Ludwig_van_Beethoven");
        musicLinks.add("https://en.wikipedia.org/wiki/Fr%C3%A9d%C3%A9ric_Chopin");
        musicHtmlFiles = new ArrayList<>();
        for (String link : musicLinks) {
            int index = link.lastIndexOf("/") + 1;
            String fileName = link.substring(index);
            musicHtmlFiles.add(HTML_FILE_PREFIX + fileName + HTML_FILE_SUFFIX);
        }
        
        itLinks = new ArrayList<>();
        itLinks.add("https://en.wikipedia.org/wiki/Computer");
        itLinks.add("https://en.wikipedia.org/wiki/Central_processing_unit");
        itLinks.add("https://en.wikipedia.org/wiki/Operating_system");
        itLinks.add("https://en.wikipedia.org/wiki/Operating_system");
        itLinks.add("https://en.wikipedia.org/wiki/Computer_programming");
        itHtmlFiles = new ArrayList<>();
        for (String link : itLinks) {
            int index = link.lastIndexOf("/") + 1;
            String fileName = link.substring(index);
            itHtmlFiles.add(HTML_FILE_PREFIX + fileName + HTML_FILE_SUFFIX);
        }
        
        documentsCount = zoologyLinks.size() + musicLinks.size() + itLinks.size();
        downloadProgressCount = 0;
        compareProgressCount = 0;
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        words = new HashMap<>();
        
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
        
        JButton downloadButton = new JButton("Download all documents and create dictionaries");
        downloadButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        downloadButton.setBounds(12, 154, 348, 40);
        mainPanel.add(downloadButton);
        
        JButton compareButton = new JButton("Compare documents");
        compareButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        compareButton.setBounds(12, 207, 348, 40);
        mainPanel.add(compareButton);
        
        JTextArea resultsArea = new JTextArea();
        resultsArea.setBounds(12, 264, 560, 190);
        //mainPanel.add(resultsArea);
        
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setVisible(false);
        scrollPane.setBounds(12, 270, 560, 220);
        mainPanel.add(scrollPane);
        
        JLabel downloadProgressLabelitLinks = new JLabel("Download progress:");
        downloadProgressLabelitLinks.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        downloadProgressLabelitLinks.setBounds(372, 154, 134, 14);
        mainPanel.add(downloadProgressLabelitLinks);
        
        JLabel compareProgressLabel = new JLabel("Compare progress:");
        compareProgressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        compareProgressLabel.setBounds(372, 207, 134, 14);
        mainPanel.add(compareProgressLabel);
        
        JProgressBar downloadProgressBar = new JProgressBar();
        downloadProgressBar.setStringPainted(true);
        downloadProgressBar.setBounds(372, 175, 146, 14);
        downloadProgressBar.setMinimum(0);
        downloadProgressBar.setMaximum(documentsCount);
        mainPanel.add(downloadProgressBar);
        
        JProgressBar compareProgressBar = new JProgressBar();
        compareProgressBar.setStringPainted(true);
        compareProgressBar.setMinimum(0);
        compareProgressBar.setMaximum(0);
        compareProgressBar.setBounds(372, 230, 146, 14);
        mainPanel.add(compareProgressBar);
        
        frmComparedocsApplication.setVisible(true);
        frmComparedocsApplication.setBounds(100, 100, 600, 570);
        scrollPane.setVisible(true);
        
        downloadButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (int i=0; i<zoologyLinks.size(); i++) {
                    try {
                        String link = zoologyLinks.get(i);
                        String htmlFilePath = zoologyHtmlFiles.get(i);
                        String txtFilePath = htmlFilePath.substring(0, htmlFilePath.lastIndexOf(".")+1) + "txt";
                        System.out.println(txtFilePath);
                        HtmlDownloader downloader = new HtmlDownloader(link);
                        downloader.download(htmlFilePath);
                        HtmlParser parser = new HtmlParser(htmlFilePath, downloader.getCharset(), link);
                        parser.parse(txtFilePath);
                        words.put(link, parser.getWordList());
                        downloadProgressCount++;
                        downloadProgressBar.setValue(downloadProgressCount);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                
                for (int i=0; i<musicLinks.size(); i++) {
                    try {
                        String link = musicLinks.get(i);
                        String htmlFilePath = musicHtmlFiles.get(i);
                        String txtFilePath = htmlFilePath.substring(0, htmlFilePath.lastIndexOf(".")+1) + "txt";
                        System.out.println(txtFilePath);
                        HtmlDownloader downloader = new HtmlDownloader(link);
                        downloader.download(htmlFilePath);
                        HtmlParser parser = new HtmlParser(htmlFilePath, downloader.getCharset(), link);
                        parser.parse(txtFilePath);
                        words.put(link, parser.getWordList());
                        downloadProgressCount++;
                        downloadProgressBar.setValue(downloadProgressCount);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                
                for (int i=0; i<itLinks.size(); i++) {
                    try {
                        String link = itLinks.get(i);
                        String htmlFilePath = itHtmlFiles.get(i);
                        String txtFilePath = htmlFilePath.substring(0, htmlFilePath.lastIndexOf(".")+1) + "txt";
                        System.out.println(txtFilePath);
                        HtmlDownloader downloader = new HtmlDownloader(link);
                        downloader.download(htmlFilePath);
                        HtmlParser parser = new HtmlParser(htmlFilePath, downloader.getCharset(), link);
                        parser.parse(txtFilePath);
                        words.put(link, parser.getWordList());
                        downloadProgressCount++;
                        downloadProgressBar.setValue(downloadProgressCount);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                
                for (String link : words.keySet()) { 
                    System.out.println("Link: " + link + " --> words List size: " + words.get(link).size());
                }
            }
        });
        
        compareButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
        });
        
        
    }
}
