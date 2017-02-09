package ptr.studies.java.webmining.search;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javax.print.DocFlavor;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;

public class BookSearchApplication {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BookSearchMainWindow window = new BookSearchMainWindow();

//                    String slash = File.separator;   
//                    String dirPath = System.getProperty("user.home") + slash + "Desktop" + slash + "webmining_docs" + slash + "search" + slash + "test" + slash;
//                    Path dir = Paths.get(dirPath);
//                    Stream<Path> filesListed = Files.list(dir);
//                    ArrayList<Path> files = new ArrayList<Path>();
//                    Iterator<Path> iter = filesListed.iterator();
//                    while (iter.hasNext()) {
//                        files.add(iter.next());
//                    }
//                    
//                    try {
//
//                        System.out.println("Listed " + files.size() + " files under: " + dir.toString());
//                        Iterator<Path> it = files.iterator();
//                        
//                        StandardAnalyzer analyzer = new StandardAnalyzer();
//                        Directory index = new RAMDirectory();
//                        IndexWriterConfig config = new IndexWriterConfig(analyzer);
//                        IndexWriter writer = new IndexWriter(index, config);
//                        while (it.hasNext()) {
//                            Path file = it.next();
//                            System.out.println("File: " + file.toString());
//                            List<String> lines = Files.readAllLines(file);
//                            String TITLE = lines.get(0).split(": ")[1];
//                            List<String> textLines = lines.subList(lines.indexOf("Text:")+1, lines.size()-1);
//                            StringBuilder b = new StringBuilder();
//                            for (String line : textLines) {
//                                b.append(line + "\n");
//                            }
//                            String TEXT = b.toString();
//                            
//                            Document doc = new Document();
//                            doc.add(new TextField("title", TITLE, Field.Store.YES));
//                            doc.add(new TextField("text", TEXT, Field.Store.YES));
//                            writer.addDocument(doc);
//                            writer.commit();
//                        }
//                        
//                        String query = "Adam";
//                        Query q = new QueryParser("text", analyzer).parse(query);
//                        
//                        int hits = 3;
//                        IndexReader reader = DirectoryReader.open(index);
//                        IndexSearcher searcher = new IndexSearcher(reader);
//                        TopDocs docs = searcher.search(q, hits);
//                        ScoreDoc[] docHits = docs.scoreDocs;
//                        
//                        System.out.println("Found: " + docHits.length + " hits.");
//                        for (int i=0; i<docHits.length; i++) {
//                            int docId = docHits[i].doc;
//                            Document doc = searcher.doc(docId);
//                            System.out.println((i+1) + ") " + doc.get("title") + "\n" + doc.get("text"));
//                        }
//                        
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    
//
//                    
//                    
//
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        });
    }
}
