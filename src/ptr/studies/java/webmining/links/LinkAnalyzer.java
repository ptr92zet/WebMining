package ptr.studies.java.webmining.links;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ptr.studies.java.webmining.html.HtmlDownloader;
import ptr.studies.java.webmining.html.HtmlParser;

public class LinkAnalyzer {
    
    private static final String slash = File.separator;   
    private static final String DOWNLOAD_DIR_PATH = System.getProperty("user.home") + slash + "Desktop" + slash + "webmining_docs" + slash + "links" + slash;
    private static final String SAME_IP_FILE = DOWNLOAD_DIR_PATH + "FILES_WITH_LINKS" + slash + "sameIp.txt";
    private static final String EXTERNAL_IP_FILE = DOWNLOAD_DIR_PATH + "FILES_WITH_LINKS" + slash + "externalIp.txt";
    private static final String HTML_SUFFIX = ".html";
    
    private int depth;
    private static int count;
    private static String hostIp;
    private static Path sameIpFilePath;
    private static Path externalIpFilePath;

    
    public LinkAnalyzer(int n, String hostIpAddress) throws URISyntaxException, IOException {
        depth = n;
        hostIp = hostIpAddress;
        count = 0;
        sameIpFilePath = Paths.get(SAME_IP_FILE);
        externalIpFilePath = Paths.get(EXTERNAL_IP_FILE);
        if (!Files.exists(sameIpFilePath)) {
            Files.createFile(sameIpFilePath);
        }
        if (!Files.exists(externalIpFilePath)) {
            Files.createFile(externalIpFilePath);
        }
        Files.write(sameIpFilePath, "\n".getBytes());
        Files.write(externalIpFilePath, "\n".getBytes());
    }

    public static void analyze(String linkUrl, int nDepth) throws URISyntaxException, IOException {
        count++;
        InetAddress host = InetAddress.getByName((new URI(linkUrl)).getHost());
        String hostName = host.getHostName(); 
        String hostIpAddr = host.getHostAddress();
        System.out.println("Current HOST: " + hostName + ", Current HOST IP: " + hostIpAddr + ", current link: " + linkUrl + ", BASE HOST IP: " + hostIp);
        if (hostIpAddr.equals(hostIp)){
            Files.write(sameIpFilePath, (linkUrl+"\n").getBytes(), StandardOpenOption.APPEND);
        } else {
            Files.write(externalIpFilePath, (linkUrl+"\n").getBytes(), StandardOpenOption.APPEND);
        }

        String currentFile = DOWNLOAD_DIR_PATH + count + HTML_SUFFIX;
        Path currentFilePath = Paths.get(currentFile);
        
        // create current HTML file in the "links" directory, with 'count' name, i.e. 4.html
        if (!Files.exists(currentFilePath)) {
            Files.createFile(currentFilePath);
        }
        
        String formattedLink = "";
        
        // for relative links
        if (!linkUrl.startsWith("http://")) {
            System.out.println("Found relative link: " + linkUrl + ", changing to absolute link...");
            formattedLink = "http://" + hostName + "/" + linkUrl;
            System.out.println("Absolute link: " + formattedLink);
        } else {
            formattedLink = linkUrl;
        }
            
        HtmlDownloader downloader = new HtmlDownloader(formattedLink);
        downloader.download(currentFile);
        HtmlParser parser = new HtmlParser(currentFile, downloader.getCharset(), linkUrl);
        ArrayList<String> links = parser.getLinks();
        
        //recursive calls until depth is n
        if (nDepth>0) {
            for (String link : links) {
                analyze(link, nDepth-1);
            }
        }
        
    }
}

//  String encodedLink = URLEncoder.encode(linkUrl, Charset.defaultCharset().name());
//  URI url = new URI(encodedLink);
//  InetAddress domain = InetAddress.getByName(url.getHost());
//  String domainIP = domain.getHostAddress();
//  String text = "Domain: " + domain.getHostName() + ", IP: " + domainIP + ", link: " + linkUrl + "\n";
//  if (domainIP.equals(hostIp)){
//      Files.write(sameIpFilePath, text.getBytes(), StandardOpenOption.APPEND);
//  } else {
//      Files.write(externalIpFilePath, text.getBytes(), StandardOpenOption.APPEND);
//  }
//  System.out.print(text);

        

        
//        try {
//
//            
//
//            for (String link : links) {
//                if (!link.startsWith("javascript")) {
//                    if (!link.startsWith("http://")) {
//                        link = "http://" + hostName + "/" + link;
//                        System.out.println("Changed domain! " + link);
//                    }
//                    String encodedLink = URLEncoder.encode(link, downloader.getCharset());
//                    URI url = new URI(encodedLink);
//                    InetAddress domain = InetAddress.getByName(url.getHost());
//                    String domainIP = domain.getHostAddress();
//                    String text = "Domain: " + domain.getHostName() + ", IP: " + domainIP + ", link: " + link + "\n";
//                    if (domainIP.equals(hostIp)){
//                        Files.write(sameIpFilePath, text.getBytes(), StandardOpenOption.APPEND);
//                    } else {
//                        Files.write(externalIpFilePath, text.getBytes(), StandardOpenOption.APPEND);
//                    }
//                    System.out.print(text);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
