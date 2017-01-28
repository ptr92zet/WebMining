package ptr.studies.java.webmining.links;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;

import ptr.studies.java.webmining.html.HtmlDownloader;
import ptr.studies.java.webmining.html.HtmlParser;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.awt.event.ActionEvent;

public class LinkAnalyzerMainWindow {

    private final String HTML_FILE_PREFIX = System.getProperty("user.home") + "\\Desktop\\webmining_docs\\links\\";
    private final String HTML_FILE_SUFFIX = ".html";
    private final String FIRST_HTML_NAME = "first_html";
    
    private JFrame frmLinkanalyzerapplication;
    private JTextField urlField;
    private JTextField nField;
    private JButton downloadButton;
    
    private JTextArea sameIPArea;
    private JTextArea externalArea;

    private HtmlDownloader downloader;
    private HtmlParser parser;
    
    /**
     * Create the application.
     */
    public LinkAnalyzerMainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmLinkanalyzerapplication = new JFrame();
        frmLinkanalyzerapplication.setTitle("LinkAnalyzerApplication");
        frmLinkanalyzerapplication.setBounds(100, 100, 600, 550);
        frmLinkanalyzerapplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLinkanalyzerapplication.getContentPane().setLayout(null);
        
        urlField = new JTextField();
        urlField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        urlField.setBounds(10, 30, 414, 38);
        frmLinkanalyzerapplication.getContentPane().add(urlField);
        urlField.setColumns(10);
        
        JLabel urlLabel = new JLabel("HTTP address of the first page:");
        urlLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        urlLabel.setBounds(10, 11, 189, 14);
        frmLinkanalyzerapplication.getContentPane().add(urlLabel);
        
        nField = new JTextField();
        nField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        nField.setBounds(449, 30, 125, 38);
        frmLinkanalyzerapplication.getContentPane().add(nField);
        nField.setColumns(10);
        
        JLabel nLabel = new JLabel("N pages:");
        nLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        nLabel.setBounds(449, 11, 60, 14);
        frmLinkanalyzerapplication.getContentPane().add(nLabel);
        
        downloadButton = new JButton("Download");
        downloadButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        downloadButton.setBounds(10, 79, 564, 38);
        frmLinkanalyzerapplication.getContentPane().add(downloadButton);
        
        sameIPArea = new JTextArea();
        sameIPArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        sameIPArea.setBounds(0, 0, 4, 22);
        frmLinkanalyzerapplication.getContentPane().add(sameIPArea);
        
        externalArea = new JTextArea();
        externalArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        externalArea.setBounds(0, 0, 4, 22);
        frmLinkanalyzerapplication.getContentPane().add(externalArea);
        frmLinkanalyzerapplication.setVisible(true);
        
        JScrollPane sameIPScrollPane = new JScrollPane(sameIPArea);
        sameIPScrollPane.setBounds(10, 142, 564, 169);
        frmLinkanalyzerapplication.getContentPane().add(sameIPScrollPane);
        
        JLabel sameIPLabel = new JLabel("Links from the same IP:");
        sameIPLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sameIPLabel.setBounds(10, 128, 189, 14);
        frmLinkanalyzerapplication.getContentPane().add(sameIPLabel);
        
        JLabel externalLabel = new JLabel("Links to external pages:");
        externalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        externalLabel.setBounds(10, 319, 189, 14);
        frmLinkanalyzerapplication.getContentPane().add(externalLabel);
        
        JScrollPane externalScrollPane = new JScrollPane(externalArea);
        externalScrollPane.setBounds(10, 334, 564, 167);
        frmLinkanalyzerapplication.getContentPane().add(externalScrollPane);

        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String firstLink = urlField.getText();
                    int n = Integer.parseInt(nField.getText());
                    String hostIp = InetAddress.getByName(new URI(firstLink).getHost()).getHostAddress();
                    System.out.println("Host IP before starting analysis: " + hostIp);
                    LinkAnalyzer la = new LinkAnalyzer(n, hostIp);
                    LinkAnalyzer.analyze(firstLink, n);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
