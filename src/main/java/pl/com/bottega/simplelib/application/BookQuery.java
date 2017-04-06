package pl.com.bottega.simplelib.application;

public class BookQuery {


    private String exactTitle;
    private String phraseInTitle;
    private String author;
    private Integer publishedBefore;
    private Integer publishedAfter;

    private Integer pageNumber = 1;
    private Integer perPage = 50;
    private String sortBy;
    private String sortOrder;

    public void setExactTitle(String exactTitle) {
        this.exactTitle = exactTitle;
    }

    public String getExactTitle() {
        return exactTitle;
    }

    public void setPhraseInTitle(String phraseInTitle) {
        this.phraseInTitle = phraseInTitle;
    }

    public String getPhraseInTitle() {
        return phraseInTitle;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setPublishedBefore(int publishedBefore) {
        this.publishedBefore = publishedBefore;
    }

    public Integer getPublishedBefore() {
        return publishedBefore;
    }


    public void setPublishedAfter(int publishedAfter) {
        this.publishedAfter = publishedAfter;
    }

    public Integer getPublishedAfter() {
        return publishedAfter;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return sortOrder;
    }
}
