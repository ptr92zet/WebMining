package ptr.studies.java.webmining.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class BookSearcher {

    private final String SLASH = File.separator;
    private final String TITLES_INDEX_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH
            + "webmining_docs" + SLASH + "search" + SLASH + "indexes" + SLASH + "titles" + SLASH;
    private final String TITLES_TEXT_INDEX_DIR = System.getProperty("user.home") + SLASH + "Desktop" + SLASH
            + "webmining_docs" + SLASH + "search" + SLASH + "indexes" + SLASH + "titles_and_text" + SLASH;
    
    private Path titlesIndexDir;
    private Path titlesTextIndexDir;
    
    private StandardAnalyzer analyzer;
    
    public BookSearcher() {
        this.titlesIndexDir = Paths.get(TITLES_INDEX_DIR);
        this.titlesTextIndexDir = Paths.get(TITLES_TEXT_INDEX_DIR);
        this.analyzer = new StandardAnalyzer();
    }
    
    public void setAnalyzer(StandardAnalyzer newAnalyzer) {
        this.analyzer = newAnalyzer;
    }
    
    public String searchByTitles(String expression, int maxHits) {
        StringBuilder builder = new StringBuilder();
        String queryStr = expression;
        DirectoryReader reader = null;
        IndexSearcher searcher = null;
        Directory dir = null;
        try {
            QueryParser queryParser = new QueryParser("title", analyzer);
            Query query = queryParser.parse(queryStr);
            System.out.println("Analyzing query: " + query.toString());
            dir = FSDirectory.open(titlesIndexDir);
            reader = DirectoryReader.open(dir);
            searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, maxHits);
            ScoreDoc[] docHits = docs.scoreDocs;
            
            for (int i=0; i<docHits.length; i++) {
                int docId = docHits[i].doc;
                Document doc = searcher.doc(docId);
                builder.append((i+1) + ") " + doc.get("title") + "\n");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                dir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return builder.toString();
    }
    
    public String searchByTitlesOrText(String expression, int maxHits, SearchType searchType) {
        StringBuilder builder = new StringBuilder();
        String queryStr = expression;
        DirectoryReader reader = null;
        IndexSearcher searcher = null;
        Directory dir = null;
        QueryParser queryParser = determineSearchType(searchType);
        
        try {
            Query query = queryParser.parse(queryStr);
            dir = FSDirectory.open(titlesTextIndexDir);
            reader = DirectoryReader.open(dir);
            searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, maxHits);
            ScoreDoc[] docHits = docs.scoreDocs;
            
            for (int i=0; i<docHits.length; i++) {
                int docId = docHits[i].doc;
                Document doc = searcher.doc(docId);
                builder.append((i+1) + ") " + doc.get("title") + "\n");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return builder.toString();
    }
    
    private QueryParser determineSearchType(SearchType searchType) {
        QueryParser queryParser = null;
        
        if (searchType.equals(SearchType.TITLE)) {
            queryParser = new QueryParser("title", analyzer);
        } else if (searchType.equals(SearchType.TEXT)) {
            queryParser = new QueryParser("text", analyzer);
        } else if (searchType.equals(SearchType.TEXT_OR_TITLE)) {
            String[] fields = { "title", "text" };
            queryParser = new MultiFieldQueryParser(fields, analyzer);
        } else {
            queryParser = new QueryParser("text", analyzer);
        }
        
        return queryParser;
    }
}
