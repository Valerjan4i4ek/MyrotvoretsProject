import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParsingClass {
    static NewsDataCache newsDataCache = new NewsDataCache();
    static TextArticleCache textArticleCache = new TextArticleCache();
    static final String myrotvoretsLink = "https://myrotvorets.news/";

    public static void main(String[] args) throws IOException {
//        List<NewsData> newsDataList = parsingNews(myrotvoretsLink);
        Map<Integer, NewsData> newsDataMap = parsingNews(myrotvoretsLink);
        List<PagesData> pagesDataList = parsingPages(myrotvoretsLink);
        start(newsDataMap, pagesDataList);
//        start(newsDataList, pagesDataList);
    }

    public static void start(Map<Integer, NewsData> newsDataMap, List<PagesData> pagesList) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("News or Pages : 1 or 2");
        int newsOrPages = scanner.nextInt();
        int newsNumber;
        int pagesNumber;
        if(newsOrPages == 1){
            System.out.println("Choose number of news");
            newsNumber = scanner.nextInt();
            if(newsDataMap.containsKey(newsNumber)){
                NewsData newsData = newsDataMap.get(newsNumber);
                if(getNewsDataCache(newsData.getLink()) != null){
                    if(getTextArticleCache(newsData.getLink()) != null){
                        System.out.println(parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink()));
                        parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink());
                        start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
                    }
                    else{
                        String s = parsingText(newsData.getLink());
                        System.out.println(s);
                        addTextArticleCache(newsData.getArticle(), newsData.getLink(), s);
                        parsingText(Objects.requireNonNull(getNewsDataCache(newsData.getLink())).getLink());
                        start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
                    }
                }
                else{
                    addNewsDataCache(newsData.getAuthor(), newsData.getArticle(), newsData.getLink());
                    if(getTextArticleCache(newsData.getLink()) != null){
                        System.out.println(parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink()));
                        parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink());
                        start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
                    }
                    else{
                        String s = parsingText(newsData.getLink());
                        System.out.println(s);
                        addTextArticleCache(newsData.getArticle(), newsData.getLink(), s);
                        parsingText(newsData.getLink());
                        start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
                    }
                }
            }
//            for(NewsData newsData : newsList){
//                if(newsData.getNumberOfNews() == newsNumber){
//                    if(getNewsDataCache(newsData.getLink()) != null){
//                        if(getTextArticleCache(newsData.getLink()) != null){
//                            System.out.println(parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink()));
//                            parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink());
//                            start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
//                        }
//                        else{
//                            String s = parsingText(newsData.getLink());
//                            System.out.println(s);
//                            addTextArticleCache(newsData.getArticle(), newsData.getLink(), s);
//                            parsingText(Objects.requireNonNull(getNewsDataCache(newsData.getLink())).getLink());
//                            start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
//                        }
//                    }
//                    else{
//                        addNewsDataCache(newsData.getAuthor(), newsData.getArticle(), newsData.getLink());
//                        if(getTextArticleCache(newsData.getLink()) != null){
//                            System.out.println(parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink()));
//                            parsingText(Objects.requireNonNull(getTextArticleCache(newsData.getLink())).getLink());
//                            start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
//                        }
//                        else{
//                            String s = parsingText(newsData.getLink());
//                            System.out.println(s);
//                            addTextArticleCache(newsData.getArticle(), newsData.getLink(), s);
//                            parsingText(newsData.getLink());
//                            start(parsingNews(myrotvoretsLink), parsingPages(myrotvoretsLink));
//                        }
//                    }
//                    //method news parsing
//                }
//            }
        }
        else if(newsOrPages == 2){
            System.out.println("Choose number of page");
            pagesNumber = scanner.nextInt();
            for(PagesData pagesData : pagesList){
                if(pagesData.getPageNumber() == pagesNumber){
                    start(parsingNews(pagesData.getLink()), parsingPages(pagesData.getLink()));
                    //method pages parsing
                }
            }
        }
        else{
            System.out.println("incorrect");
        }
    }

    public static void addTextArticleCache(String article, String link, String articleText){
        textArticleCache.addTextArticleCache(article, link, articleText);
    }

    public static TextArticle getTextArticleCache(String link){
        if(textArticleCache.getTextArticleCache(link) != null){
            return textArticleCache.getTextArticleCache(link);
        }
        return null;
    }

    public static void addNewsDataCache(String author, String article, String link){
        newsDataCache.addNewsDataCache(author, article, link);
    }

    public static NewsData getNewsDataCache(String link){
        if(newsDataCache.getNewsDataCache(link) != null){
            return newsDataCache.getNewsDataCache(link);
        }
        return null;
    }

    public static String parsingText(String link) throws IOException {
        Document doc = Jsoup.connect(link)
                .userAgent("Chrome/104.0.0.0")
                .referrer("http://www.google.com")
                .get();

        StringBuilder s = new StringBuilder();

        Elements elements = doc.select("article");
        for(Element element : elements.select("p")){
//            System.out.println(element.text());
            s.append(element.text() + "\n");
        }
        return s.toString();
    }

    public static Map<Integer, NewsData> parsingNews(String link) throws IOException{
        Map<Integer, NewsData> map = new ConcurrentHashMap<>();
        Document doc = Jsoup.connect(link)
                .userAgent("Chrome/104.0.0.0")
                .referrer("http://www.google.com")
                .get();

        Elements listNews = doc.select("article");
        int i = 1;
        for(Element element : listNews){
            System.out.println(i + " " + element.select("a").get(0).text());
            System.out.println(element.select("a").get(2).text());
            System.out.println(element.select("a").get(2).attr("href"));
            NewsData newsData = new NewsData(i, element.select("a").get(0).text(), element.select("a").get(2).text(), element.select("a").get(2).attr("href"));
            map.put(i, newsData);
            System.out.println();
            i++;
        }
        return map;
    }

//    public static List<NewsData> parsingNews(String link) throws IOException {
//        List<NewsData> list = new ArrayList<>();
//        Document doc = Jsoup.connect(link)
//                .userAgent("Chrome/104.0.0.0")
//                .referrer("http://www.google.com")
//                .get();
//
//        Elements listNews = doc.select("article");
//        int i = 1;
//        for(Element element : listNews){
//            System.out.println(i + " " + element.select("a").get(0).text());
//            System.out.println(element.select("a").get(2).text());
//            System.out.println(element.select("a").get(2).attr("href"));
//            NewsData newsData = new NewsData(i, element.select("a").get(0).text(), element.select("a").get(2).text(), element.select("a").get(2).attr("href"));
//            list.add(newsData);
//            System.out.println();
//            i++;
//        }
//        return list;
//    }

    public static List<PagesData> parsingPages(String link) throws IOException {
        List<PagesData> list = new ArrayList<>();
        Document doc = Jsoup.connect(link)
                .userAgent("Chrome/104.0.0.0")
                .referrer("http://www.google.com")
                .get();

        Elements pages = doc.select("#main > div");
        for(Element element : pages.select("a")){
            if(!element.text().equals("Наступна ›") && !element.text().equals("Остання »") && !element.text().equals("‹ Попередня") && !element.text().equals("Сторінка")){
                System.out.print(element.text() + "  ");
                System.out.println(element.attr("href"));
                PagesData pagesData = new PagesData(Integer.parseInt(element.text()), element.attr("href"));
                list.add(pagesData);
            }
        }
        return list;
    }
}
