package model;

import control.TokenGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Attributes {

    public static List<Attributes> allAttributes = new ArrayList<>();
    private String attributeId;
    private String field;
    private Set<String> values = new HashSet<String>();

    public Attributes(String field) {
        attributeId = TokenGenerator.generateAttributeId();
        this.field = field;
        allAttributes.add(this);
    }

    public static void addAttribute(Attributes attribute) {
        allAttributes.add(attribute);
    }

    public void addAttributeValue(String value) {
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

    public String getAttributeId() {
        return attributeId;
    }

    public static Attributes getAttributeById (String attributeId) throws Exception{
        for (Attributes a : allAttributes){
            if (a.getAttributeId().equals(attributeId))return a;
        }
        throw new Exception("attribute not found");
    }
}
