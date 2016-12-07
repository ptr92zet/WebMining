package ptr.studies.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;

public class HtmlDownloader {

    private URL url;
    private Path htmlFile;
    private Path txtFile;
    private Path processedFile;
    private String htmlResponse;
    private String htmlResponseJsoup;
    private String processedTxtContent;

    public HtmlDownloader(String htmlFilePath, String txtFilePath, String processedFile) {
        this.htmlFile = Paths.get(htmlFilePath);
        this.txtFile = Paths.get(txtFilePath);
        this.processedFile = Paths.get(processedFile);
    }

    public void download(String pageAddress) throws MalformedURLException, IOException {
        url = new URL(pageAddress);
        readHtmlResponse();
        writeHtmlFile(htmlResponse);
    }
    
    public void parseHtmlToTxt() throws IOException {
        writeTxtFile(htmlResponseJsoup);
    }
   
    public void processTxtFile() throws IOException {
        String text = htmlResponseJsoup.toLowerCase();
        text = text.replaceAll("[,:;-]", "");
        processedTxtContent = text.replaceAll("\\.( )+", "");
        writeProcessedFile(processedTxtContent);  
    }
    
    private void readHtmlResponse() {
        try {
            readWithUrlConnection();
            readWithJsoup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readWithUrlConnection() throws IOException {
        URLConnection connection = url.openConnection();
        connection.connect();
        InputStream htmlStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(htmlStream));

        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line + "\n");
        }
        reader.close();
        this.htmlResponse = response.toString();
    }

    private void readWithJsoup() throws IOException {
        Document document = Jsoup.parse(url.openStream(), "ISO-8859-2", url.toString());
        Element head = document.head();
        Element body = document.body();
        String cleanedHead = Jsoup.clean(head.text(), "", new Whitelist().none(),
                new OutputSettings().prettyPrint(false));
        String cleanedBody = Jsoup.clean(body.text(), "", new Whitelist().none(),
                new OutputSettings().prettyPrint(false));
        this.htmlResponseJsoup = cleanedHead + cleanedBody;
    }

    private void writeHtmlFile(String content) throws IOException {
        if (!Files.exists(htmlFile)) {
            Files.createFile(htmlFile);
        }
        Files.write(htmlFile, content.getBytes());
    }

    private void writeTxtFile(String content) throws IOException {
        if (!Files.exists(txtFile)) {
            Files.createFile(txtFile);
        }
        Files.write(txtFile, content.getBytes());
    }
    
    private void writeProcessedFile(String content) throws IOException {
        if (!Files.exists(processedFile)) {
            Files.createFile(processedFile);
        }
        Files.write(processedFile, content.getBytes());
    }
}
