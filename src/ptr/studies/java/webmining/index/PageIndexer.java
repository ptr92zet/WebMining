package ptr.studies.java.webmining.index;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.UnmappableCharacterException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import ptr.studies.java.webmining.html.HtmlDownloader;
import ptr.studies.java.webmining.html.HtmlParser;
import ptr.studies.java.webmining.links.LinkAnalyzer;

public class PageIndexer {
    
    private Path indexDirPath;
    private Directory indexDir;

    private StandardAnalyzer analyzer;
    private IndexWriterConfig config;
    private IndexWriter writer;
    private int pagesAddedToIndex;
    
    public PageIndexer(String indexDirectory) throws IOException {
        this.indexDirPath = Paths.get(indexDirectory);
        this.pagesAddedToIndex = 0;
        initializeSearchEngine();
    }
    
    public void openIndex() {
        try {
            indexDir = FSDirectory.open(indexDirPath);
            writer = new IndexWriter(indexDir, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addToIndex(String txtFilePath, String pageUrl, Charset charset) {
        List<String> lines = new ArrayList<>();
        pagesAddedToIndex++;
        try {
            System.out.println("Indexing page by TITLE and TEXT (" + pagesAddedToIndex + "): " + txtFilePath);
            lines = readFile(txtFilePath, charset);
            String[] urlTokens = pageUrl.split("/");
            StringBuilder textBuilder = new StringBuilder();
            StringBuilder urlTokensBuilder = new StringBuilder();
            
            for (String line : lines) {
                textBuilder.append(line + "\n");
            }
            for (String token : urlTokens) {
                urlTokensBuilder.append(token + " ");
            }
            System.out.println("URL tokens from: " + pageUrl + " -> [" + urlTokensBuilder.toString() + "]");
            Document doc = new Document();
            doc.add(new Field("page_url", pageUrl, TextField.TYPE_STORED));
            doc.add(new Field("url_tokens", urlTokensBuilder.toString(), TextField.TYPE_STORED));
            doc.add(new Field("text", textBuilder.toString(), TextField.TYPE_STORED));
            writer.addDocument(doc);
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println("IOException while trying to add a file: " + txtFilePath + " to index. Trying to close index and writer.");
                writer.close();
                indexDir.close();
            } catch (IOException e1) {
                System.out.println("Could not close index and writer after IOException while indexing file: " + txtFilePath);
                e1.printStackTrace();
            }
        }
    }
    
    public void closeIndex() {
        try {
            writer.close();
            indexDir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void initializeSearchEngine() {
        analyzer = new StandardAnalyzer();
        config = new IndexWriterConfig(analyzer);
        config.setOpenMode(OpenMode.CREATE);
        config.setRAMBufferSizeMB(128.0);        
    }
    
    private List<String> readFile(String txtFilePath, Charset charset) throws IOException {
        Path txtFile = Paths.get(txtFilePath);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(txtFile, charset);
        } catch (UnmappableCharacterException e) {
            System.out.println("UnmappableCharacterException! Skipping file: " + txtFile.toString());
        }
        return lines;
    }
}
