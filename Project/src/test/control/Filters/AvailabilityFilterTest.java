package control.Filters;

import model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class AvailabilityFilterTest {

    AvailabilityFilter filter;
    Product p1;
    Product p2;
    Product p3;
    Product p4;


    @BeforeEach
    void setup(){
        filter = AvailabilityFilter.getInstance();
        p1 = new Product(" ", "a" , "" , 0.00 , null , 10 , null , "");
        p2 = new Product(" ", "e" , "" , 0.00 , null , 0 , null , "");
        p3 = new Product(" ", "c" , "" , 0.00 , null , 10 , null , "");
        p4 = new Product(" ", "f" , "" , 0.00 , null , 0 , null , "");
    }

    @Test
    void applyFilter() {
        ArrayList<Product> actual = new ArrayList<>(filter.applyFilter(new ArrayList<>(Arrays.asList(p1,p2,p3,p4))));
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p1,p3)));
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    void getInstance() {
        filter = AvailabilityFilter.getInstance();
        Assert.assertEquals(filter,AvailabilityFilter.getInstance());
    }
}