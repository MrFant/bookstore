package domain;

import java.util.List;

public class PageBean {
    private int currentCount;
    private int currentPage;
    private String Category;
    private int totalCount;
    private List<Product> ps;

    public String getSearchfield() {
        return searchfield;
    }

    public void setSearchfield(String searchfield) {
        this.searchfield = searchfield;
    }

    private String searchfield;
    private int totalPage;

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Product> getPs() {
        return ps;
    }

    public void setPs(List<Product> ps) {
        this.ps = ps;
    }



    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
