public class NewsData {
    private int numberOfNews;
    private String author;
    private String article;
    private String link;

    public NewsData(int numberOfNews, String author, String article, String link) {
        this.numberOfNews = numberOfNews;
        this.author = author;
        this.article = article;
        this.link = link;
    }

    public NewsData(String author, String article, String link) {
        this.author = author;
        this.article = article;
        this.link = link;
    }

    public int getNumberOfNews() {
        return numberOfNews;
    }

    public void setNumberOfNews(int numberOfNews) {
        this.numberOfNews = numberOfNews;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
