package pl.com.bottega.simplelib.application;

public class BookQuery {


    private String exactTitle;
    private String phraseInTitle;
    private String author;
    private Integer publishedBefore;
    private Integer publishedAfter;

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
}
