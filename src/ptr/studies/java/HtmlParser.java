package ptr.studies.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class HtmlParser {

    private String htmlContent;
    private String txtContent;
    private Document doc;

    private Path txtFile;
            
    public HtmlParser(String htmlContent, String txtFilePath) {
        this.htmlContent = htmlContent;
        this.txtFile = Paths.get(txtFilePath);
    }

    public void parse() throws IOException {
        //String cleanedContent = Jsoup.clean(htmlContent, "", new Whitelist().none(), new OutputSettings().prettyPrint(false));
        doc = Jsoup.parse(htmlContent);
        Element head = doc.head();
        Element body = doc.body();
        String cleanedHead = Jsoup.clean(head.text(), "", new Whitelist().none(), new OutputSettings().prettyPrint(false));
        String cleanedBody = Jsoup.clean(body.text(), "", new Whitelist().none(), new OutputSettings().prettyPrint(false));
        txtContent = cleanedHead + cleanedBody;
        saveToFile();
    }
    
    private void saveToFile() throws IOException {
        if (!Files.exists(txtFile)) {
            Files.createFile(txtFile);
        }
        Files.write(txtFile, this.txtContent.getBytes());
    }
    
}
