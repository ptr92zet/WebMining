package ptr.studies.java.webmining.links;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

import ptr.studies.java.webmining.html.HtmlDownloader;
import ptr.studies.java.webmining.html.HtmlParser;

public class LinkAnalyzer {
    
    private static final String slash = File.separator;   
    private static final String DOWNLOAD_DIR_PATH = System.getProperty("user.home") + slash + "Desktop" + slash + "webmining_docs" + slash + "links" + slash;
    private static final String SAME_IP_FILE = DOWNLOAD_DIR_PATH + "FILES_WITH_LINKS" + slash + "sameIp.txt";
    private static final String EXTERNAL_IP_FILE = DOWNLOAD_DIR_PATH + "FILES_WITH_LINKS" + slash + "externalIp.txt";
    private static final String HTML_SUFFIX = ".html";
    
    private static int count;
    private static String hostIp;
    private static Path sameIpFilePath;
    private static Path externalIpFilePath;

    
    public LinkAnalyzer(int n, String hostIpAddress) throws URISyntaxException, IOException {
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

    public static void analyze(String linkUrl, String resolvedHost, String resolvedIp, int nDepth) throws URISyntaxException, IOException {
        count++;
        ArrayList<String> links = new ArrayList<>();
        System.out.println("");
        System.out.println("Called analyze() with params: N: " + nDepth + " LINK: " + linkUrl);
        String currentHostName = resolvedHost;
        String currentHostIpAddr = resolvedIp;
        System.out.println("Current HOST: " + currentHostName + ", Current HOST IP: " + currentHostIpAddr + ", BASE HOST IP: " + hostIp);
        
        if (! (linkUrl.startsWith("http:") || linkUrl.startsWith("https:") )) {
            System.out.println("Resolving relative link: " + linkUrl);
            linkUrl = resolveRelativeLink(linkUrl, currentHostName);
            System.out.println("Resolved link: " + linkUrl);
        }
        
        try {
            InetAddress currentHost = InetAddress.getByName((new URI(linkUrl)).getHost());
            currentHostName = currentHost.getHostName(); 
            currentHostIpAddr = currentHost.getHostAddress();
            if (currentHostIpAddr.equals(hostIp)){
                System.out.println("Appending current link to SAME IP file: " + linkUrl);
                Files.write(sameIpFilePath, (linkUrl+"\n").getBytes(), StandardOpenOption.APPEND);
            } else {
                System.out.println("Appending current link to EXTERNAL IP file: " + linkUrl);
                Files.write(externalIpFilePath, (linkUrl+"\n").getBytes(), StandardOpenOption.APPEND);
            }
    
            // create current HTML file in the "links" directory, with 'count' name, i.e. 4.html
            String currentFile = DOWNLOAD_DIR_PATH + count + HTML_SUFFIX;
            System.out.println("HTML file to download: " + currentFile);
            Path currentFilePath = Paths.get(currentFile);
            if (!Files.exists(currentFilePath)) {
                System.out.println("HTML file: " + currentFile + " does not exist, creating file");
                Files.createFile(currentFilePath);
            }
            
            // download current HTML file
            HtmlDownloader downloader = new HtmlDownloader(linkUrl);
            System.out.println("Downloading HTML content: " + linkUrl);
            downloader.download(currentFile);
            System.out.println("Downloaded HTML content to file: " + currentFile);
            System.out.println("File size in bytes: " + Files.size(currentFilePath));
            
            // parse current HTML file and get links
            System.out.println("Parsing HTML file: " + currentFile + ", charset: " + downloader.getCharset() + ", base Url: " + linkUrl);
            HtmlParser parser = new HtmlParser(currentFile, downloader.getCharset(), linkUrl);
            links = parser.getLinks();
            System.out.println("In the parsed file found: " + links.size() + " links");
            if (links.size() != 0) {
                System.out.println("FIRST LINK: " + links.get(0));
                System.out.println("ALL LINKS: " + Arrays.toString(links.toArray()));
            } else {
                System.out.println("FISRT LINK: NONE, there was 0 links under: " + linkUrl);
            }
        } catch (UnknownHostException u) {
            //u.printStackTrace();
            System.out.println("************************ UnknownHostException: link: " + linkUrl + " may no longer work! Skipping link...");
        } catch (URISyntaxException s) {
            //s.printStackTrace();
            System.out.println("************************ URISyntaxException: URI: " + linkUrl + " has invalid syntax (most likely unsupported character)! Skipping link...");
        }
        
        // recursive calls until depth is n
        System.out.println("Current nDepth: " + nDepth);
        if (nDepth>0) {
            if (links.size() != 0) {
                System.out.println("Calling analyze() recursively for " + links.size() + " links from: " + linkUrl);
                for (String link : links) {
                    System.out.println("Calling analyze() with parameters: N: " + (nDepth-1) + ", LINK: " + link);
                    analyze(link, currentHostName, currentHostIpAddr, nDepth-1);
                }
            } else {
                System.out.println("NOT CALLING analyze() recursively for: " + linkUrl + ", it has 0 links");
            }
        }
        System.out.println("Exiting analyze called with params: N: " + nDepth + ", LINK: " + linkUrl);
    }
    
    
    private static String resolveRelativeLink(String relativeLink, String currentHostName) {
        String formattedLink = "";
        formattedLink = "http://" + currentHostName + "/" + relativeLink;
        return formattedLink;
    }
    
}
