package ptr.studies.java.webmining.links;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import ptr.studies.java.webmining.html.HtmlDownloader;
import ptr.studies.java.webmining.html.HtmlParser;
import ptr.studies.java.webmining.index.PageIndexer;

public class LinkAnalyzerIndexer {
    
    private static final String slash = File.separator;   
    private static final String HTML_SUFFIX = ".html";
    private static final String TXT_SUFFIX = ".txt";
    
    private static final String INDEX_DIR = System.getProperty("user.home") + slash + "Desktop" + slash
            + "webmining_docs" + slash + "bot" + slash + "index" + slash;
    private static final String CONTENT_TXT_DIR = System.getProperty("user.home") + slash + "Desktop" + slash + "webmining_docs"
            + slash + "bot" + slash + "content" + slash;
    private static final String CONTENT_HTML_DIR = System.getProperty("user.home") + slash + "Desktop" + slash + "webmining_docs"
            + slash + "bot" + slash + "content_html" + slash;
    
    private static int pagesCount;
    private static int maxPagesCount;
    private static String hostName;
    private static PageIndexer indexer;
    
    
    public LinkAnalyzerIndexer(String host, int maxPages) {
        hostName = host;
        maxPagesCount = maxPages;
        pagesCount = 0;
        try {
            indexer = new PageIndexer(INDEX_DIR);
            indexer.openIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void analyzeAndIndex(String linkUrl, String resolvedHost, int nDepth) {
        if (pagesCount <= maxPagesCount) {
            ArrayList<String> links = new ArrayList<>();
            
            String currentHostName = resolvedHost;
            if (! (linkUrl.startsWith("http:") || linkUrl.startsWith("https:") )) {
                System.out.println("Resolving relative link: " + linkUrl);
                linkUrl = resolveRelativeLink(linkUrl, currentHostName);
                System.out.println("Resolved link: " + linkUrl);
            }
            
            try {
                InetAddress currentHost = InetAddress.getByName((new URI(linkUrl)).getHost());
                currentHostName = currentHost.getHostName(); 
        
                // create current HTML file in the "links" directory, with 'count' name, i.e. 4.html
                String currentHtmlFile = CONTENT_HTML_DIR + pagesCount + HTML_SUFFIX;
                Path currentHtmlFilePath = Paths.get(currentHtmlFile);
                
                // download current HTML file
                HtmlDownloader downloader = new HtmlDownloader(linkUrl);
                downloader.download(currentHtmlFile);
                System.out.println("Downloaded HTML file: " + currentHtmlFile + ", size in bytes: " + Files.size(currentHtmlFilePath));
                
                // parse current HTML file and get links
                System.out.println("Parsing HTML file: " + currentHtmlFile + ", charset: " + downloader.getCharset() + ", base Url: " + linkUrl);
                String currentTxtFile = CONTENT_TXT_DIR + pagesCount + TXT_SUFFIX;
                Path currentTxtFilePath = Paths.get(currentTxtFile);
                HtmlParser parser = new HtmlParser(currentHtmlFile, downloader.getCharset(), linkUrl);
                links = parser.getLinks();
                parser.parse(currentTxtFile);
                System.out.println("Parsed to TXT file: " + currentTxtFile + ", size in bytes: " + Files.size(currentTxtFilePath));
    
                System.out.println("In the parsed file found: " + links.size() + " links");
                if (links.size() != 0) {
                    System.out.println("FIRST LINK: " + links.get(0));
                    System.out.println("ALL LINKS: " + Arrays.toString(links.toArray()));
                } else {
                    System.out.println("FISRT LINK: NONE, there was 0 links under: " + linkUrl);
                }            
    
                System.out.println("Adding the file: " + currentTxtFile + " from page: " + linkUrl + " to Index");
                indexer.addToIndex(currentTxtFile, linkUrl, downloader.getCharsetObj());
                
                pagesCount++;
                
            } catch (UnknownHostException u) {
                //u.printStackTrace();
                System.out.println("************************ UnknownHostException: link: " + linkUrl + " may no longer work! Skipping link...");
            } catch (URISyntaxException s) {
                //s.printStackTrace();
                System.out.println("************************ URISyntaxException: URI: " + linkUrl + " has invalid syntax (most likely unsupported character)! Skipping link...");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("************************ MalformedURLException: URI: " + linkUrl + " is malformed! Skipping link...");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("************************ IOException: URI: " + linkUrl + ". Problem with parsing! Skipping link...");
            }
            
            // recursive calls until depth is n
            System.out.println("Current nDepth: " + nDepth + ", pages COUNT: " + pagesCount + ", maxPages: " + maxPagesCount);
            if (nDepth>0) {
                if (links.size() != 0) {
                    System.out.println("Calling analyze() recursively for " + links.size() + " links from: " + linkUrl);
                    for (String link : links) {
                        System.out.println("Calling analyze() with parameters: N: " + (nDepth-1) + ", LINK: " + link + ", HOST: " + currentHostName + ". Current pagesCount is: " + pagesCount);
                        analyzeAndIndex(link, currentHostName, nDepth-1);
                    }
                } else {
                    System.out.println("NOT CALLING analyze() recursively for: " + linkUrl + ", it has 0 links");
                }
            }
            System.out.println("Exiting analyze called with params: N: " + nDepth + ", LINK: " + linkUrl + ", COUNT: " + pagesCount);
        }
    }
    
    public static void closeIndex() {
        indexer.closeIndex();
    }
    
    public static void clearAnalyzer() {
        hostName = "";
        maxPagesCount = 0;
        pagesCount = 0;
        indexer.closeIndex();
    }
    
    public String getIndex() {
        return INDEX_DIR;
    }
    
    private static String resolveRelativeLink(String relativeLink, String currentHostName) {
        String formattedLink = "";
        if (relativeLink.startsWith("/")) {
            relativeLink = relativeLink.replaceFirst("/", "");
        }
        formattedLink = "http://" + currentHostName + "/" + relativeLink;
        return formattedLink;
    }
}
