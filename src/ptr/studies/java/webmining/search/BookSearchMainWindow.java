package ptr.studies.java.webmining.search;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class BookSearchMainWindow {

    private final String SLASH = File.separator;   
    private final String BOOKS_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH + "webmining_docs" + SLASH + "search" + SLASH + "books" + SLASH;
    private final String INDEXES_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH + "webmining_docs" + SLASH + "search" + SLASH + "indexes" + SLASH;
    
    private JFrame frmBooksearchApplication;
    private JTextField searchTitlesField;
    private JTextField searchTitlesTextField;
    
    private BookIndexer titlesIndexer;
    private BookIndexer titlesTextIndexer;
    private BookSearcher titlesSearcher;
    private BookSearcher titlesTextSearcher;
    private JTextArea resultsTextArea;
    private JTextField titlesMaxHitsField;
    private JTextField titlesTextMaxHitsField;

    /**
     * Create the application.
     */
    public BookSearchMainWindow() {
        initialize();
    }
    
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmBooksearchApplication = new JFrame();
        frmBooksearchApplication.setTitle("BookSearch Application");
        frmBooksearchApplication.setBounds(100, 100, 900, 700);
        frmBooksearchApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmBooksearchApplication.getContentPane().setLayout(null);
        
        JLabel booksLabel = new JLabel("Path to directory with books:");
        booksLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        booksLabel.setBounds(20, 10, 164, 14);
        frmBooksearchApplication.getContentPane().add(booksLabel);
        
        JLabel booksPathLabel = new JLabel("C:\\Users\\epiozie\\Desktop\\webmining_docs\\search\\books");
        booksPathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        booksPathLabel.setBounds(207, 10, 440, 14);
        frmBooksearchApplication.getContentPane().add(booksPathLabel);
        
        JLabel indexFilesLabel = new JLabel("Path to directory with index files:");
        indexFilesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        indexFilesLabel.setBounds(20, 31, 179, 14);
        frmBooksearchApplication.getContentPane().add(indexFilesLabel);
        
        JLabel indexFilesPathLabel = new JLabel("C:\\Users\\epiozie\\Desktop\\webmining_docs\\search\\indexes");
        indexFilesPathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        indexFilesPathLabel.setBounds(207, 32, 389, 14);
        frmBooksearchApplication.getContentPane().add(indexFilesPathLabel);
        
        JButton indexTitlesButton = new JButton("Index books by TITLES");
        indexTitlesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                titlesIndexer = new BookIndexer();
                titlesIndexer.indexByTitles();
            }
        });
        indexTitlesButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        indexTitlesButton.setBounds(20, 57, 400, 38);
        frmBooksearchApplication.getContentPane().add(indexTitlesButton);
        
        JButton btnIndexBooksBy = new JButton("Index books by TITLES and TEXT");
        btnIndexBooksBy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                titlesTextIndexer = new BookIndexer();
                titlesTextIndexer.indexByTitlesAndText();
            }
        });
        btnIndexBooksBy.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnIndexBooksBy.setBounds(461, 57, 400, 38);
        frmBooksearchApplication.getContentPane().add(btnIndexBooksBy);
        
        JLabel searchTitlesLabel = new JLabel("Search books by TITLES:");
        searchTitlesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesLabel.setBounds(20, 109, 164, 14);
        frmBooksearchApplication.getContentPane().add(searchTitlesLabel);
        
        JLabel searchTitlesTextLabel = new JLabel("Search books by TITLES OR TEXT:");
        searchTitlesTextLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesTextLabel.setBounds(461, 106, 197, 14);
        frmBooksearchApplication.getContentPane().add(searchTitlesTextLabel);
        
        searchTitlesField = new JTextField();
        searchTitlesField.setBounds(20, 130, 400, 30);
        frmBooksearchApplication.getContentPane().add(searchTitlesField);
        searchTitlesField.setColumns(10);
        
        searchTitlesTextField = new JTextField();
        searchTitlesTextField.setColumns(10);
        searchTitlesTextField.setBounds(461, 131, 400, 29);
        frmBooksearchApplication.getContentPane().add(searchTitlesTextField);
        
        JLabel titlesMaxHitsLabel = new JLabel("Max hits:");
        titlesMaxHitsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titlesMaxHitsLabel.setBounds(20, 171, 133, 14);
        frmBooksearchApplication.getContentPane().add(titlesMaxHitsLabel);
        
        JLabel titlesTextMaxHitsLabel = new JLabel("Max hits:");
        titlesTextMaxHitsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titlesTextMaxHitsLabel.setBounds(461, 171, 133, 14);
        frmBooksearchApplication.getContentPane().add(titlesTextMaxHitsLabel);
        
        titlesMaxHitsField = new JTextField();
        titlesMaxHitsField.setColumns(10);
        titlesMaxHitsField.setBounds(20, 196, 179, 30);
        frmBooksearchApplication.getContentPane().add(titlesMaxHitsField);
        
        titlesTextMaxHitsField = new JTextField();
        titlesTextMaxHitsField.setColumns(10);
        titlesTextMaxHitsField.setBounds(461, 196, 179, 30);
        frmBooksearchApplication.getContentPane().add(titlesTextMaxHitsField);
        
        JLabel searchTypeLabel = new JLabel("Search type:");
        searchTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTypeLabel.setBounds(673, 171, 133, 14);
        frmBooksearchApplication.getContentPane().add(searchTypeLabel);
        
        JComboBox searchTypeComboBox = new JComboBox();
        searchTypeComboBox.setModel(new DefaultComboBoxModel(SearchType.values()));
        searchTypeComboBox.setBounds(673, 196, 188, 29);
        frmBooksearchApplication.getContentPane().add(searchTypeComboBox);
        
        JButton searchTitlesButton = new JButton("Search");
        searchTitlesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                titlesSearcher = new BookSearcher();
                titlesSearcher.setAnalyzer(new StandardAnalyzer());
                String expression = searchTitlesField.getText();
                String hitsToParse = titlesMaxHitsField.getText();
                if (hitsToParse.equals("")) {
                    hitsToParse = "20";
                }
                int maxHits = Integer.parseInt(hitsToParse);
                String results = titlesSearcher.searchByTitles(expression, maxHits);
                resultsTextArea.setText("Searched by TITLES. Searched for expression: " + expression + ". Set max hits: " + maxHits + "\n");
                resultsTextArea.append(results);
            }
        });
        searchTitlesButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesButton.setBounds(20, 237, 400, 38);
        frmBooksearchApplication.getContentPane().add(searchTitlesButton);
        
        JButton searchTitlesTextButton = new JButton("Search");
        searchTitlesTextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                titlesTextSearcher = new BookSearcher();
                titlesTextSearcher.setAnalyzer(new StandardAnalyzer());
                String expression = searchTitlesTextField.getText();
                String hitsToParse = titlesTextMaxHitsField.getText();
                if (hitsToParse.equals("")) {
                    hitsToParse = "20";
                }
                int maxHits = Integer.parseInt(hitsToParse);
                SearchType type = (SearchType) searchTypeComboBox.getSelectedItem();
                String results = titlesTextSearcher.searchByTitlesOrText(expression, maxHits, type);
                resultsTextArea.setText("Searched by: " + type + ". Searched for expression: " + expression + ". Set max hits: " + maxHits + "\n");
                resultsTextArea.append(results);
            }
        });
        searchTitlesTextButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesTextButton.setBounds(461, 237, 400, 38);
        frmBooksearchApplication.getContentPane().add(searchTitlesTextButton);
        
        resultsTextArea = new JTextArea();
        resultsTextArea.setBounds(20, 230, 841, 421);
        resultsTextArea.setColumns(10);
        
        JScrollPane resultsScrollPane = new JScrollPane(resultsTextArea);
        resultsScrollPane.setBounds(20, 293, 841, 358);
        frmBooksearchApplication.getContentPane().add(resultsScrollPane);
        

        
        
        
        frmBooksearchApplication.setVisible(true);
    }
}
