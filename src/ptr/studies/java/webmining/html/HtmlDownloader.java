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
    private Charset charset = Charset.defaultCharset();
    private String charsetName = charset.name();

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
            this.charsetName = recognizePageCharset();
            URLConnection connection = url.openConnection();
            connection.connect();
            htmlStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(htmlStream, charsetName));
            writer = new PrintWriter(htmlFilePath, charsetName);
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
        } catch (org.jsoup.HttpStatusException h) {
            System.out.println("ERROR 404: Page is unavailable: " + url.toString());
        } catch (org.jsoup.UnsupportedMimeTypeException h) {
            System.out.println("************************ UnsupportedMimeTypeException: Link: " + url.toString() + " points to unsupported resource type! Skipping link...");
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
        return this.charsetName;
    }

    public Charset getCharsetObj() {
        return this.charset;
    }
    
    private String recognizePageCharset() throws IOException {
        Document doc = Jsoup.connect(url.toString()).get();
        charset = doc.charset();
        return charset.toString();
    }
}