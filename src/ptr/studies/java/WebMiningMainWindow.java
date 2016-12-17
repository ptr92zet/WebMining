package ptr.studies.java;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WebMiningMainWindow {

    private JFrame frmWebminingApplication;
    private JTextField urlField;
    private HtmlDownloader downloader;
    private HtmlParser parser;
    private final String htmlPath = System.getProperty("user.home") + "\\Desktop\\test.html";
    private final String txtPath = System.getProperty("user.home") + "\\Desktop\\test.txt";
    private JTextField htmlPathField;
    private JTextField txtPathField;

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
        downloadButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        downloadButton.setBounds(10, 206, 414, 30);
        frmWebminingApplication.getContentPane().add(downloadButton);

        JLabel htmlFilePathLabel = new JLabel(".html file path on disk:");
        htmlFilePathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        htmlFilePathLabel.setBounds(10, 78, 141, 19);
        frmWebminingApplication.getContentPane().add(htmlFilePathLabel);

        htmlPathField = new JTextField();
        htmlPathField.setText(htmlPath);
        htmlPathField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        htmlPathField.setColumns(10);
        htmlPathField.setBounds(10, 98, 414, 30);
        frmWebminingApplication.getContentPane().add(htmlPathField);

        JLabel txtFilePathLabel = new JLabel(".txt file path on disk:");
        txtFilePathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtFilePathLabel.setBounds(10, 139, 141, 19);
        frmWebminingApplication.getContentPane().add(txtFilePathLabel);

        txtPathField = new JTextField();
        txtPathField.setText(txtPath);
        txtPathField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtPathField.setColumns(10);
        txtPathField.setBounds(10, 155, 414, 30);
        frmWebminingApplication.getContentPane().add(txtPathField);
        frmWebminingApplication.setVisible(true);

        downloadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    downloader = new HtmlDownloader(urlField.getText());
                    downloader.download(htmlPathField.getText());
                    parser = new HtmlParser(htmlPathField.getText(), downloader.getCharset(), urlField.getText());
                    parser.parse(txtPathField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
