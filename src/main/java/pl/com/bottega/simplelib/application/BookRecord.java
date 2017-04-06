package pl.com.bottega.simplelib.application;

public class BookRecord {
    private String author;
    private String title;

    private String publishYear;

    private long availableCopies;
    private long lendCopies;
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public void setAvailableCopies(long availableCopies) {
        this.availableCopies = availableCopies;
    }

    public long getAvailableCopies() {
        return availableCopies;
    }

    public void setLendCopies(long lendCopies) {
        this.lendCopies = lendCopies;
    }

    public long getLendCopies() {
        return lendCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishYear() {
        return publishYear;
    }
}
