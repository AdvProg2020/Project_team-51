package control.Filters;

import model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PriceRangeFilterTest {


    PriceRangeFilter filter;
    Product p1;
    Product p2;
    Product p3;
    Product p4;


    @BeforeEach
    void setup(){
        filter = PriceRangeFilter.getInstance();
        p1 = new Product(" ", "a" , "" , 20.00 , null , 10 , null , "");
        p2 = new Product(" ", "e" , "" , 30.00 , null , 0 , null , "");
        p3 = new Product(" ", "c" , "" , 40.00 , null , 10 , null , "");
        p4 = new Product(" ", "f" , "" , 50.00 , null , 0 , null , "");
    }

    @Test
    void applyFilter() {
        ArrayList<Product> actual = new ArrayList<>(filter.applyFilter(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),25.00 , 35.00));
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p2)));
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    void getInstance() {
        filter = PriceRangeFilter.getInstance();
        Assert.assertEquals(filter,PriceRangeFilter.getInstance());
    }
}