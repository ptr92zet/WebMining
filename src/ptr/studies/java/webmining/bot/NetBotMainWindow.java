package ptr.studies.java.webmining.bot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import ptr.studies.java.webmining.index.PageIndexer;
import ptr.studies.java.webmining.links.LinkAnalyzer;
import ptr.studies.java.webmining.links.LinkAnalyzerIndexer;
import ptr.studies.java.webmining.search.PageSearcher;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

public class NetBotMainWindow {

    private JFrame frmNetbotApplication;
    private JTextField searchField;
    private JTextArea resultsArea;
    private JTextField indexStartField;
    
    LinkAnalyzerIndexer analyzer;
    PageSearcher searcher;
    private final String slash = File.separator;  
    private final String INDEX_DIR = System.getProperty("user.home") + slash + "Desktop" + slash
            + "webmining_docs" + slash + "bot" + slash + "index" + slash;
    
    /**
     * Create the application.
     */
    public NetBotMainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmNetbotApplication = new JFrame();
        frmNetbotApplication.setTitle("NetBot Application");
        frmNetbotApplication.setBounds(100, 100, 782, 500);
        frmNetbotApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmNetbotApplication.getContentPane().setLayout(null);
        
        JButton indexButton = new JButton("INDEX pages");
        indexButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
//                    String firstLinks[] = indexStartField.getText().split(" ");
//                    int maxPages = 11000;
//                    int maxPagesPerStartPage = maxPages / firstLinks.length;
//                    for (String firstLink : firstLinks) {
                        InetAddress host = InetAddress.getByName((new URI(indexStartField.getText())).getHost());
                        String hostName = host.getHostName(); 
                        analyzer = new LinkAnalyzerIndexer(hostName, 11000);
                        LinkAnalyzerIndexer.analyzeAndIndex(indexStartField.getText(), hostName, 10);
                        LinkAnalyzerIndexer.clearAnalyzer();
//                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        indexButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        indexButton.setBounds(322, 30, 432, 56);
        frmNetbotApplication.getContentPane().add(indexButton);
        
        JLabel lblSearchInContent = new JLabel("<html><b>Search in content</b><br>\r\n- for <b>exact phrase</b> search, put it in <b>quotes</b>, i.e. \"foo bar foo\",<br>\r\n- for <b>wildcard search</b>, put \"<b>*</b>\" at the end, i.e. \"foobar*\"</html>");
        lblSearchInContent.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSearchInContent.setBounds(10, 111, 381, 41);
        frmNetbotApplication.getContentPane().add(lblSearchInContent);
        
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchField.setBounds(10, 156, 462, 41);
        frmNetbotApplication.getContentPane().add(searchField);
        searchField.setColumns(10);
        
        JButton btnNewButton = new JButton("Search in content");
        btnNewButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                PageSearcher searcher = new PageSearcher(INDEX_DIR, false);
                String expr = searchField.getText();
                String results = searcher.searchOnPages(expr, 5);
                resultsArea.setText(results);
            }
        });
        btnNewButton.setBounds(498, 156, 256, 41);
        frmNetbotApplication.getContentPane().add(btnNewButton);
        
        resultsArea = new JTextArea();
        
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBounds(10, 246, 744, 205);
        frmNetbotApplication.getContentPane().add(scrollPane);
        
        JLabel lblresultsBest = new JLabel("<html><b>Results</b> (5 best pages):");
        lblresultsBest.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblresultsBest.setBounds(10, 219, 381, 23);
        frmNetbotApplication.getContentPane().add(lblresultsBest);
        
        indexStartField = new JTextField();
        indexStartField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        indexStartField.setColumns(10);
        indexStartField.setBounds(10, 45, 283, 41);
        frmNetbotApplication.getContentPane().add(indexStartField);
        
        JLabel indexStartLabel = new JLabel("<html><b>Indexing starting page</b></html>");
        indexStartLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        indexStartLabel.setBounds(10, 11, 283, 30);
        frmNetbotApplication.getContentPane().add(indexStartLabel);

        
        frmNetbotApplication.setVisible(true);
    }
}
