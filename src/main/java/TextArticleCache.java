import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextArticleCache {
    Map<String, TextArticle> textArticleCacheMap = new ConcurrentHashMap<>();

    public Map<String, TextArticle> addTextArticleCache(String article, String link, String articleText){
        textArticleCacheMap.put(article, new TextArticle(article, link, articleText));
        return textArticleCacheMap;
    }

    public TextArticle getTextArticleCache(String article){
        return textArticleCacheMap.get(article);
    }
}
