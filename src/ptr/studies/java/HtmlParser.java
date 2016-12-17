package ptr.studies.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public HtmlParser(String htmlFilePath, String charset, String baseUrl) {
        this.htmlFile = Paths.get(htmlFilePath);
        this.charset = charset;
        this.baseUrl = baseUrl;
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
        writeProcessedFile(txtContent);
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
