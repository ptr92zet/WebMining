package ptr.studies.java.webmining;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

public class HtmlParser {

    private Path htmlFile;
    private Path txtFile;
    private String baseUrl;
    private String charset;
    private String txtContent;
    private ArrayList<String> words;

    public HtmlParser(String htmlFilePath, String charset, String baseUrl) {
        this.htmlFile = Paths.get(htmlFilePath);
        this.charset = charset;
        this.baseUrl = baseUrl;
        this.words = new ArrayList<>(); 
    }

    public void parse(String txtFilePath) throws IOException {
        txtFile = Paths.get(txtFilePath);
        Document doc = Jsoup.parse(htmlFile.toFile(), charset, baseUrl);

        Element head = doc.head();
        Element body = doc.body();
        String cleanedHead = Jsoup.clean(head.text(), baseUrl, new Whitelist().none(),
                new OutputSettings().prettyPrint(false));
        String cleanedBody = Jsoup.clean(body.text(), baseUrl, new Whitelist().none(),
                new OutputSettings().prettyPrint(false));

        txtContent = cleanedHead + cleanedBody;
        txtContent = processText(txtContent);
        createWordList(txtContent);
        writeProcessedFile(txtContent);
    }

    public ArrayList<String> getWordList() {
        return this.words;
    }
    
    private void createWordList(String text) {
        for (String word : text.split(" ")) {
            words.add(word);
        }
    }
    
    private String processText(String text) {
        text = text.toLowerCase();
        text = text.replaceAll("[.,:;-]", " ");
        text = text.replaceAll("[!?–]", " ");
        text = text.replaceAll("[()]", " ");
        text = text.replaceAll("[\\[\\]]", " ");
        text = text.replaceAll("[{}]", " ");
        text = text.replaceAll("[<>]", " ");
        text = text.replaceAll("\\.\\.\\.", " ");
        text = text.replaceAll("…", " ");
        text = text.replaceAll("\"", "");
        
        // replace all consecutive whitespaces, tabs, newlines etc. with only one space 
        text = text.replaceAll("\\s+", " ");
        return text;
    }

    private void writeProcessedFile(String content) {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(txtFile.toString(), charset);
            writer.write(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
