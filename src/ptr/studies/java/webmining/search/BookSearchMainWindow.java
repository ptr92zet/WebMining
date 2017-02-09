package ptr.studies.java.webmining.search;


import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

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

public class BookSearchMainWindow {

    private final String SLASH = File.separator;   
    private final String BOOKS_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH + "webmining_docs" + SLASH + "search" + SLASH + "books" + SLASH;
    private final String INDEXES_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH + "webmining_docs" + SLASH + "search" + SLASH + "indexes" + SLASH;
    
    private JFrame frmBooksearchApplication;
    private JTextField searchTitlesField;
    private JTextField searchTitlesTextField;
    
    private BookIndexer indexer;

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
        frmBooksearchApplication.setBounds(100, 100, 900, 300);
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
                indexer = new BookIndexer();
                indexer.indexByTitles();
                IndexReader reader = null;
                IndexSearcher searcher;
                try {
                    String query = "remarkable";
                    Query q = new QueryParser("text", indexer.getAnalyzer()).parse(query);
                    Directory dir = FSDirectory.open(indexer.getTitlesIndexDir());
                    int hits = 20;
                    reader = DirectoryReader.open(dir);
                    searcher = new IndexSearcher(reader);
                    TopDocs docs = searcher.search(q, hits);
                    ScoreDoc[] docHits = docs.scoreDocs;
                    
                    System.out.println("Found: " + docHits.length + " hits.");
                    for (int i=0; i<docHits.length; i++) {
                        int docId = docHits[i].doc;
                        Document doc = searcher.doc(docId);
                        System.out.println((i+1) + ") " + doc.get("title") + "\n");
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        indexTitlesButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        indexTitlesButton.setBounds(20, 57, 400, 38);
        frmBooksearchApplication.getContentPane().add(indexTitlesButton);
        
        JButton btnIndexBooksBy = new JButton("Index books by TITLES and TEXT");
        btnIndexBooksBy.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnIndexBooksBy.setBounds(461, 57, 400, 38);
        frmBooksearchApplication.getContentPane().add(btnIndexBooksBy);
        
        JLabel searchTitlesLabel = new JLabel("Search books by TITLES:");
        searchTitlesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesLabel.setBounds(20, 109, 164, 14);
        frmBooksearchApplication.getContentPane().add(searchTitlesLabel);
        
        JLabel searchTitlesTextLabel = new JLabel("Search books by TITLES OR TEXT:");
        searchTitlesTextLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesTextLabel.setBounds(461, 109, 197, 14);
        frmBooksearchApplication.getContentPane().add(searchTitlesTextLabel);
        
        searchTitlesField = new JTextField();
        searchTitlesField.setBounds(20, 130, 400, 30);
        frmBooksearchApplication.getContentPane().add(searchTitlesField);
        searchTitlesField.setColumns(10);
        
        searchTitlesTextField = new JTextField();
        searchTitlesTextField.setColumns(10);
        searchTitlesTextField.setBounds(461, 134, 400, 30);
        frmBooksearchApplication.getContentPane().add(searchTitlesTextField);
        
        JButton searchTitlesButton = new JButton("Search by TITLES");
        searchTitlesButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesButton.setBounds(20, 171, 400, 38);
        frmBooksearchApplication.getContentPane().add(searchTitlesButton);
        
        JButton searchTitlesTextButton = new JButton("Search by TITLES or TEXT");
        searchTitlesTextButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchTitlesTextButton.setBounds(461, 175, 400, 38);
        frmBooksearchApplication.getContentPane().add(searchTitlesTextButton);
        frmBooksearchApplication.setVisible(true);
    }
}
