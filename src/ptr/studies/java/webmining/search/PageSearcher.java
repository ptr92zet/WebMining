package ptr.studies.java.webmining.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class PageSearcher {
        
    private Path indexDirPath;
    private Directory indexDir;
    private String[] fields = { "url_tokens", "text" };
    private boolean exact;
    
    private StandardAnalyzer analyzer;
    
    public PageSearcher(String indexDirectory, boolean exactSearch) {
        this.indexDirPath = Paths.get(indexDirectory);
        this.analyzer = new StandardAnalyzer();
        this.exact = exactSearch;
    }
    
    public String searchOnPages(String expression, int maxHits) {
        StringBuilder builder = new StringBuilder();
        
        String queryStr = processExpression(expression);
        DirectoryReader reader = null;
        IndexSearcher searcher = null;
        indexDir = null;
        QueryParser queryParser =  new MultiFieldQueryParser(fields, analyzer);
        
        try {
            Query query = queryParser.parse(queryStr);
            indexDir = FSDirectory.open(indexDirPath);
            reader = DirectoryReader.open(indexDir);
            searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, maxHits);
            ScoreDoc[] docHits = docs.scoreDocs;
            
            for (int i=0; i<docHits.length; i++) {
                int docId = docHits[i].doc;
                Document doc = searcher.doc(docId);
                String pageUrl = stripNavStrings(doc.get("page_url"));
                builder.append(i + ") " + pageUrl + ", score: " + docHits[i].score + "\n");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                indexDir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (builder.toString().equals("")) {
            return "Expression: " + queryStr + " was not found!";
        } else {
            return builder.toString();
        }
    }
    
    private String stripNavStrings(String url) {
        String pageUrl = url;
        if (pageUrl.endsWith("#NavLinks")) {
            pageUrl = pageUrl.substring(0, pageUrl.lastIndexOf("#NavLinks"));
        } else if (pageUrl.endsWith("#NavMore")) {
            pageUrl = pageUrl.substring(0, pageUrl.lastIndexOf("#NavMore"));
        }
        return pageUrl;
    }
    
    private String processExpression(String expr) {
        String expression = "";
        if (expr.startsWith("*")) {
            expression = expr.substring(1);
        } else {
            expression = expr;
        }
        return expression;
    }
}
