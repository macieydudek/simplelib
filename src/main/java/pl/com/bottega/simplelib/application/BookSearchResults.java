package pl.com.bottega.simplelib.application;

import java.util.List;

public class BookSearchResults {

    List<BookRecord> bookrecords;
    private Integer pageNumber;
    private Integer perPage;
    private Long pagesCount;

    public List<BookRecord> getBookrecords() {
        return bookrecords;
    }

    public void setBookrecords(List<BookRecord> bookrecords) {
        this.bookrecords = bookrecords;
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

    public Long getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(Long pagesCount) {
        this.pagesCount = pagesCount;
    }
}
