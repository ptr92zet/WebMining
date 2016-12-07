package ptr.studies.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;

import org.jsoup.Jsoup;

import java.awt.Font;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Action;

public class WebMiningMainWindow {

    private JFrame frmWebminingApplication;
    private JTextField urlField;
    private HtmlDownloader downloader;
    private HtmlParser parser;
    private final String targetFilePath = System.getProperty("user.home") + "/Desktop/test.html";
    private final String txtFilePath = System.getProperty("user.home") + "/Desktop/test.txt";
    private final String processedFilePath = System.getProperty("user.home") + "/Desktop/test_processed.txt";

    /**
     * Create the application.
     */
    public WebMiningMainWindow() {
        initialize();
        this.downloader = new HtmlDownloader(targetFilePath, txtFilePath, processedFilePath);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        System.out.println(System.getProperty("user.home"));
        frmWebminingApplication = new JFrame();
        frmWebminingApplication.setFont(new Font("Segoe Script", Font.PLAIN, 11));
        frmWebminingApplication.setTitle("WebMining Application");
        frmWebminingApplication.setBounds(100, 100, 450, 300);
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
        downloadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    downloader.download(urlField.getText());
                    downloader.parseHtmlToTxt();
                    downloader.processTxtFile();
//                    parser = new HtmlParser(content, txtFilePath);
//                    parser.parse();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        downloadButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        downloadButton.setBounds(10, 74, 414, 30);
        frmWebminingApplication.getContentPane().add(downloadButton);

        JTextPane logTextPane = new JTextPane();
        logTextPane.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        logTextPane.setEnabled(false);
        logTextPane.setText("Logs from downloading webpage...");
        logTextPane.setBounds(10, 117, 414, 134);
        frmWebminingApplication.getContentPane().add(logTextPane);
        frmWebminingApplication.setVisible(true);
    }
}
