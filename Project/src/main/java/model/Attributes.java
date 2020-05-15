package model;

import java.util.HashSet;
import java.util.Set;

public class Attributes {

    private String field ;
    private Set<String> values = new HashSet<String>() ;

    public Attributes(String field , String ... values) {
        this.field = field;
    }

    public void addAttributeValue(String value){
        values.add(value);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }


}
