package ptr.studies.java.webmining.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlDownloader {

    private URL url;
    private String charset = "UTF-8";

    public HtmlDownloader(String pageAddress) throws MalformedURLException {
        this.url = new URL(pageAddress);
    }

    public void download(String htmlFilePath) {
        InputStream htmlStream = null;
        ;
        BufferedReader reader = null;
        PrintWriter writer = null;
        String line;
        try {
            this.charset = recognizePageCharser();
            URLConnection connection = url.openConnection();
            connection.connect();
            htmlStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(htmlStream, charset));
            writer = new PrintWriter(htmlFilePath, charset);
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (htmlStream != null) {
                    htmlStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getCharset() {
        return this.charset;
    }

    private String recognizePageCharser() throws IOException {
        Document doc = Jsoup.connect(url.toString()).get();
        Charset charset = doc.charset();
        return charset.toString();
    }
}

// public void processTxtFile() throws IOException {
// String text = htmlResponseJsoup.toLowerCase();
// text = text.replaceAll("[.,:;-]", " ");
// text = text.replaceAll("[!?–]", " ");
// text = text.replaceAll("[()]", " ");
// text = text.replaceAll("[\\[\\]]", " ");
// text = text.replaceAll("[{}]", " ");
// text = text.replaceAll("[<>]", " ");
// text = text.replaceAll("\\.\\.\\.", " ");
// text = text.replaceAll("…", " ");
// //processedTxtContent = text.replaceAll("\\.( )+", "");
// processedTxtContent = text;
// writeProcessedFile(processedTxtContent);
// }
//
// private void readHtmlResponse() {
// try {
// readWithUrlConnection();
// readWithJsoup();
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
//
// private void readWithUrlConnection() throws IOException {
// URLConnection connection = url.openConnection();
// connection.connect();
// InputStream htmlStream = connection.getInputStream();
// BufferedReader reader = new BufferedReader(new
// InputStreamReader(htmlStream));
//
// String line;
// StringBuilder response = new StringBuilder();
// while ((line = reader.readLine()) != null) {
// response.append(line + "\n");
// }
// reader.close();
// this.htmlResponse = response.toString();
// }
//
// private void readWithJsoup() throws IOException {
// Document document = Jsoup.parse(url.openStream(), null, url.toString());
//
//// InputStream is = url.openStream();
//// BufferedReader reader = new BufferedReader(new InputStreamReader(is,
// Charset.forName("UTF-8")));
//// StringBuilder builder = new StringBuilder();
//// String line = "";
//// while ((line = reader.readLine()) != null) {
//// builder.append(line);
//// }
//// String doc = Jsoup.clean(builder.toString(), url.toString(), new
// Whitelist().none(), new OutputSettings().prettyPrint(false));
//
// Element head = document.head();
// Element body = document.body();
// String cleanedHead = Jsoup.clean(head.text(), url.toString(), new
// Whitelist().none(),
// new OutputSettings().prettyPrint(false));
// String cleanedBody = Jsoup.clean(body.text(), url.toString(), new
// Whitelist().none(),
// new OutputSettings().prettyPrint(false));
//
// this.htmlResponseJsoup = cleanedHead + cleanedBody;
//// this.htmlResponseJsoup = doc;
// }
//
// private void writeHtmlFile(String content) throws IOException {
// if (!Files.exists(htmlFile)) {
// Files.createFile(htmlFile);
// }
// Files.write(htmlFile, content.getBytes());
// }
//
// private void writeTxtFile(String content) throws IOException {
// if (!Files.exists(txtFile)) {
// Files.createFile(txtFile);
// }
// Files.write(txtFile, content.getBytes());
// }
//
// private void writeProcessedFile(String content) throws IOException {
// if (!Files.exists(processedFile)) {
// Files.createFile(processedFile);
// }
// Files.write(processedFile, content.getBytes());
// }