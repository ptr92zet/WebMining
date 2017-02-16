package ptr.studies.java.webmining.index;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnmappableCharacterException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.Lock;
import org.apache.lucene.util.Version;

public class BookIndexer {

    private final String SLASH = File.separator;
    private final String BOOKS_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH + "webmining_docs"
            + SLASH + "search" + SLASH + "books" + SLASH;
    private final String TITLES_INDEX_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH
            + "webmining_docs" + SLASH + "search" + SLASH + "indexes" + SLASH + "titles" + SLASH;
    private final String TITLES_TEXT_INDEX_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH
            + "webmining_docs" + SLASH + "search" + SLASH + "indexes" + SLASH + "titles_and_text" + SLASH;

    private Path booksDir;
    private Path titlesIndexDir;
    private Path titlesTextIndexDir;
    private ArrayList<String> bookFiles;
    
    private StandardAnalyzer analyzer;
    private IndexWriterConfig config;


    public BookIndexer() {
        this.booksDir = Paths.get(BOOKS_DIR);
        this.titlesIndexDir = Paths.get(TITLES_INDEX_DIR);
        this.titlesTextIndexDir = Paths.get(TITLES_TEXT_INDEX_DIR);
    }

    public void indexByTitles() {
        initialize();

        int filesIndexed = 0;
        Directory dir = null;
        IndexWriter writer = null;
        List<String> lines = new ArrayList<>();
        
        try {
            dir = FSDirectory.open(titlesIndexDir);
            writer = new IndexWriter(dir, config);
            for (String book : bookFiles) {
                Path path = Paths.get(BOOKS_DIR + book);
                try {
                    lines = Files.readAllLines(path, Charset.defaultCharset());
                } catch (UnmappableCharacterException e) {
                    System.out.println("UnmappableCharacterException! Skipping file: " + path.toString());
                    continue;
                }
                String TITLE = "";
                
//                if (filesIndexed == 100) {
//                    break;
//                }
                
                for (int i=0; i<lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.startsWith("Title:")) {
                        TITLE = line.split("Title:")[1].trim();
                        break;
                    }
                }
                
                Document doc = new Document();
                doc.add(new Field("title", TITLE, TextField.TYPE_STORED));
                writer.addDocument(doc);
                writer.commit();
                System.out.println("Indexing file by TITLE (" + filesIndexed + "): " + path.toString() + " ---> \"" + TITLE + "\"");
                
                filesIndexed++;
            }
            writer.close();
            dir.close();
        } catch (IOException e ) {
            e.printStackTrace();
            try {
                writer.close();
                dir.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    public void indexByTitlesAndText() {
        initialize();
        
        int filesIndexed = 0;
        Directory dir = null;
        IndexWriter writer = null;
        List<String> lines = new ArrayList<>();
        
        try {
            dir = FSDirectory.open(titlesTextIndexDir);
            writer = new IndexWriter(dir, config);
            for (String book : bookFiles) {
                Path path = Paths.get(BOOKS_DIR + book);
                System.out.println("Indexing file by TITLE and TEXT (" + (filesIndexed + 1) + "): " + path.toString());
                try {
                    lines = Files.readAllLines(path, Charset.defaultCharset());
                } catch (UnmappableCharacterException e) {
                    System.out.println("UnmappableCharacterException! Skipping file: " + path.toString());
                    continue;
                }
                String TITLE = "";
                String TEXT = "";
                int titleIndex = 0;
                int textIndex = 0;
                StringBuilder textBuilder = new StringBuilder();
                
//                if (filesIndexed == 100) {
//                    break;
//                }
                
                for (int i=0; i<lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.startsWith("Title:")) {
                        TITLE = line.split("Title:")[1].trim();
                        titleIndex = i;
                        break;
                    }
                }
                for (int i=titleIndex; i<lines.size(); i++) {
                    String line = lines.get(i);
                    if (line.startsWith("*** START OF")) {
                        textIndex = i+1;
                        break;
                    }
                }
                for (int i=textIndex; i<lines.size(); i++) {
                    textBuilder.append(lines.get(i) + "\n");
                }
                
                TEXT = textBuilder.toString();
                Document doc = new Document();
                doc.add(new Field("title", TITLE, TextField.TYPE_STORED));
                doc.add(new Field("text", TEXT, TextField.TYPE_STORED));
                writer.addDocument(doc);
                writer.commit();
                
                filesIndexed++;
            }
            writer.close();
            dir.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                writer.close();
                dir.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    
    public StandardAnalyzer getAnalyzer() {
        return this.analyzer;
    }
    
    public IndexWriterConfig getConfig() {
        return this.config;
    }
    
    public Path getTitlesIndexDir() {
        return this.titlesIndexDir;
    }
    
    public Path getTitlexTextIndexDir() {
        return this.titlesTextIndexDir;
    }
    
    private void initialize() {
        bookFiles = listAllFiles(booksDir);
        analyzer = new StandardAnalyzer();
        config = new IndexWriterConfig(analyzer);
        config.setOpenMode(OpenMode.CREATE);
        config.setRAMBufferSizeMB(128.0);
    }

    private ArrayList<String> listAllFiles(Path dirPath) {
        ArrayList<String> files = new ArrayList<String>();

        try {
            Directory dir = FSDirectory.open(dirPath);
            String[] fileNames = dir.listAll();
            files = new ArrayList<>(Arrays.asList(fileNames));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Listed " + files.size() + " files under: " + dirPath.toString());

        return files;
    }
}
