package ptr.studies.java.webmining.wordsorter;

import javax.swing.JFrame;
import javax.swing.JTextField;

import ptr.studies.java.webmining.HtmlDownloader;
import ptr.studies.java.webmining.HtmlParser;
import ptr.studies.java.webmining.WordCountPair;
import ptr.studies.java.webmining.WordSorter;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JScrollPane;

public class WebMiningMainWindow {

    private JFrame frmWebminingApplication;
    private JTextField urlField;
    private HtmlDownloader downloader;
    private HtmlParser parser;
    private WordSorter sorter;
    private final String htmlPath = System.getProperty("user.home") + "\\Desktop\\test.html";
    private final String txtPath = System.getProperty("user.home") + "\\Desktop\\test.txt";
    private JTextField kWordsField;
    private JTextField threshField;

    /**
     * Create the application.
     */
    public WebMiningMainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmWebminingApplication = new JFrame();
        frmWebminingApplication.setFont(new Font("Segoe Script", Font.PLAIN, 11));
        frmWebminingApplication.setTitle("WebMining Application");
        frmWebminingApplication.setBounds(100, 100, 445, 375);
        frmWebminingApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmWebminingApplication.getContentPane().setLayout(null);

        urlField = new JTextField();
        urlField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        urlField.setBounds(10, 33, 414, 30);
        frmWebminingApplication.getContentPane().add(urlField);
        urlField.setColumns(10);

        JLabel urlLabel = new JLabel("Webpage URL address:");
        urlLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        urlLabel.setBounds(10, 11, 141, 19);
        frmWebminingApplication.getContentPane().add(urlLabel);

        JButton downloadButton = new JButton("Download webpage as .html");
        downloadButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        downloadButton.setBounds(10, 76, 414, 30);
        frmWebminingApplication.getContentPane().add(downloadButton);
        
        JPanel panel = new JPanel();
        panel.setBounds(10, 134, 414, 194);
        frmWebminingApplication.getContentPane().add(panel);
        panel.setLayout(null);
        
        JButton sortNaivelyButton = new JButton("Sort words naively");
        sortNaivelyButton.setEnabled(false);
        sortNaivelyButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sortNaivelyButton.setBounds(10, 107, 179, 32);
        panel.add(sortNaivelyButton);
        
        JLabel threshLabel = new JLabel("thresh for words:");
        threshLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        threshLabel.setBounds(10, 59, 163, 14);
        panel.add(threshLabel);
        
        JLabel kWordsLabel = new JLabel("k words:");
        kWordsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        kWordsLabel.setBounds(10, 9, 163, 14);
        panel.add(kWordsLabel);
        
        threshField = new JTextField();
        threshField.setEnabled(false);
        threshField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        threshField.setBounds(10, 73, 163, 23);
        panel.add(threshField);
        threshField.setColumns(10);
        
        JButton sortOptimallyButton = new JButton("Sort words optimally");
        sortOptimallyButton.setEnabled(false);
        sortOptimallyButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sortOptimallyButton.setBounds(10, 150, 179, 32);
        panel.add(sortOptimallyButton);
        
        kWordsField = new JTextField();
        kWordsField.setEnabled(false);
        kWordsField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        kWordsField.setBounds(10, 25, 163, 23);
        panel.add(kWordsField);
        kWordsField.setColumns(10);
        
        JLabel sortedWordsLabel = new JLabel("Words on the page (sorted):");
        sortedWordsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sortedWordsLabel.setBounds(199, 9, 205, 14);
        panel.add(sortedWordsLabel);
        
        JTextArea sortedWordsArea = new JTextArea();
        sortedWordsArea.setEnabled(false);
        sortedWordsArea.setBounds(199, 25, 205, 158);
        //panel.add(sortedWordsArea);
        
        JScrollPane sortedWordsScrollPane = new JScrollPane(sortedWordsArea);
        sortedWordsScrollPane.setBounds(199, 25, 205, 157);
        panel.add(sortedWordsScrollPane);
        frmWebminingApplication.setVisible(true);

        downloadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    downloader = new HtmlDownloader(urlField.getText());
                    downloader.download(htmlPath);
                    parser = new HtmlParser(htmlPath, downloader.getCharset(), urlField.getText());
                    parser.parse(txtPath);
                    kWordsField.setEnabled(true);
                    threshField.setEnabled(true);
                    sortedWordsArea.setEnabled(true);
                    sortNaivelyButton.setEnabled(true);
                    sortOptimallyButton.setEnabled(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        sortNaivelyButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter = new WordSorter(parser.getWordList(), Integer.parseInt(kWordsField.getText()), Integer.parseInt(threshField.getText()));
                long naiveTimeStart = System.nanoTime();
                ArrayList<WordCountPair> sortedWords = sorter.sortNaive();
                long naiveTimeStop = System.nanoTime();
                
                StringBuilder builder = new StringBuilder();
                for (WordCountPair pair : sortedWords) {
                    builder.append(pair.toString() + "\n");
                }
                double elapsedTime = (naiveTimeStop - naiveTimeStart)/1000000.0;
                builder.append("\nTime NAIVE (ms): " + elapsedTime + "\n\n");
                sortedWordsArea.setText(builder.toString());
            }
        });
        
        sortOptimallyButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                long optimalTimeStart = System.nanoTime();
                ArrayList<WordCountPair> sortedWords = sorter.sortOptimally();
                long optimalTimeStop = System.nanoTime();
                
                StringBuilder builder = new StringBuilder();
                for (WordCountPair pair : sortedWords) {
                    builder.append(pair.toString() + "\n");
                }
                double elapsedTime = (optimalTimeStop - optimalTimeStart)/1000000.0;
                builder.append("\nTime OPTIMAL (ms): " + elapsedTime);
                sortedWordsArea.append(builder.toString());
            }
        });
    }
}
