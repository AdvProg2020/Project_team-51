package model;

public class Filter <T> {
    private String filterName ;
    private T data ;

    public Filter(String filterName, T data) {
        this.filterName = filterName;
        this.data = data;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public T getData() {
        return data;
    }

}
