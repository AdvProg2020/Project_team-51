package control.Filters;

import model.Category;
import model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {

    Filter filter;
    Product p1;
    Product p2;
    Product p3;
    Product p4;
    Category c1;
    Category c2;
    Category c3;


    @BeforeEach
    void setup(){
        c1 = new Category("salam" , null) ;
        c2 = new Category("chetori ?" , c1);
        c3 = new Category("khobam" , null);
        p1 = new Product(" ", "a" , "" , 20.00 , null , 10 , c1 , "");
        p2 = new Product(" ", "e" , "" , 30.00 , null , 0 , c2 , "");
        p3 = new Product(" ", "c" , "" , 40.00 , null , 10 , c3 , "");
        p4 = new Product(" ", "f" , "" , 50.00 , null , 0 , null , "");
    }

    @Test
    void applyFilter() {
        filter = AvailabilityFilter.getInstance();
        ArrayList<Product> actual = new ArrayList<>(filter.applyFilter(new ArrayList<>(Arrays.asList(p1,p2,p3,p4))));
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p1,p3)));
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    void applyFilter2() {
        filter = CategoryFilter.getInstance();
        ArrayList<Category> categories = new ArrayList<>(Arrays.asList(c2,c3));
        ArrayList<Product> actual = new ArrayList<>(filter.applyFilter(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),categories));
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p2,p3)));
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    void applyFilter3() {
        filter = PriceRangeFilter.getInstance();
        ArrayList<Product> actual = new ArrayList<>(filter.applyFilter(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),25.00 , 35.00));
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p2)));
        Assert.assertTrue(expected.equals(actual));
    }


    @Test
    void getInstance() {
        filter = AvailabilityFilter.getInstance();
        Assert.assertEquals(filter,AvailabilityFilter.getInstance());
    }

    @Test
    void getName() {
        filter = RateRangeFilter.getInstance();
        String name = filter.getName();
        assertEquals(filter.name , name);
    }

}