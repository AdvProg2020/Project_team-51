package control.Filters;

import model.Product;
import model.Rate;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class RateRangeFilterTest {
    RateRangeFilter filter;
    Product p1;
    Product p2;
    Product p3;
    Product p4;
    Rate r1 = new Rate(null , p1 , (short) 2);
    Rate r2 = new Rate(null , p2 , (short) 5);
    Rate r3 = new Rate(null , p3 , (short) 3);
    Rate r4 = new Rate(null , p4 , (short) 1);


    @BeforeEach
    void setup(){
        filter = RateRangeFilter.getInstance();
        p1 = new Product(" ", "a" , "" , 0.00 , null , 10 , null , "", null);
        p2 = new Product(" ", "e" , "" , 0.00 , null , 10 , null , "", null);
        p3 = new Product(" ", "c" , "" , 0.00 , null , 10 , null , "", null);
        p4 = new Product(" ", "f" , "" , 0.00 , null , 10 , null , "", null);
        p1.setRating(new ArrayList<>(Arrays.asList(r1)));
        p2.setRating(new ArrayList<>(Arrays.asList(r2)));
        p3.setRating(new ArrayList<>(Arrays.asList(r3)));
        p4.setRating(new ArrayList<>(Arrays.asList(r4)));

    }

    @Test
    void applyFilter() {
        ArrayList<Product> actual = new ArrayList<>(filter.applyFilter(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),1.00 , 3.00));
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p1,p3,p4)));
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    void getInstance() {
        filter = RateRangeFilter.getInstance();
        Assert.assertEquals(filter,RateRangeFilter.getInstance());
    }
}