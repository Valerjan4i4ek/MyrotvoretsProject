public class PagesData {
    private int pageNumber;
    private String link;

    public PagesData(int pageNumber, String link) {
        this.pageNumber = pageNumber;
        this.link = link;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
