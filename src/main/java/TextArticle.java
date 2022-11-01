public class TextArticle {
    private String article;
    private String link;
    private String articleText;

    public TextArticle(String article, String link, String articleText) {
        this.article = article;
        this.link = link;
        this.articleText = articleText;
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

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }
}
