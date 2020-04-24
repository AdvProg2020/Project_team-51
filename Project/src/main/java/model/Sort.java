package model;

public class Sort <T> {
    private String name ;
    private T data ;
    private Boolean isDescending;

    public Sort(String name, T data, Boolean isDescending) {
        this.name = name;
        this.data = data;
        this.isDescending = isDescending;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDescending() {
        return isDescending;
    }

    public void setDescending(Boolean descending) {
        isDescending = descending;
    }

    public T getData() {
        return data;
    }
}
