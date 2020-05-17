package control.Filters;

import model.Category;
import model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CategoryFilterTest {


    CategoryFilter filter;
    Category c1;
    Category c2;
    Category c3;
    Product p1;
    Product p2;
    Product p3;
    Product p4;


    @BeforeEach
    void setup(){
        filter = CategoryFilter.getInstance();
        c1 = new Category("salam" , null) ;
        c2 = new Category("chetori ?" , c1);
        c3 = new Category("khobam" , null);
        p1 = new Product(" ", "a" , "" , 0.00 , null , 10 , c1 , "", null);
        p2 = new Product(" ", "e" , "" , 0.00 , null , 0 , c2 , "", null);
        p3 = new Product(" ", "c" , "" , 0.00 , null , 10 , c3 , "", null);
        p4 = new Product(" ", "f" , "" , 0.00 , null , 0 , null , "", null);
    }

    @Test
    void applyFilter() {
        ArrayList<Category> categories = new ArrayList<>(Arrays.asList(c2,c3));
        ArrayList<Product> actual = new ArrayList<>(filter.applyFilter(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),categories));
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p2,p3)));
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    void getInstance() {
        filter = CategoryFilter.getInstance();
        Assert.assertEquals(filter,CategoryFilter.getInstance());
    }
}