package control.Sorts;

import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PriceSortTest {
    public Sort priceSort;
    Product p1;
    Product p2;
    Product p3;
    Product p4;


    @BeforeEach
    void setup(){
        priceSort = PriceSort.getInstance();
        p1 = new Product(" ", "a" , "" , 10.00 , null , 10 , null , "", null);
        p2 = new Product(" ", "e" , "" , 84.00 , null , 10 , null , "", null);
        p3 = new Product(" ", "c" , "" , 15.00 , null , 10 , null , "", null);
        p4 = new Product(" ", "f" , "" , 50.00 , null , 10 , null , "", null);
    }

    @Test
    void applyAscendingSort() {
        ArrayList<Product> actual = priceSort.applySort(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),true);
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p1,p3,p4,p2)));
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
    }

    @Test
    void applyDescendingSort() {
        ArrayList<Product> actual = priceSort.applySort(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),false);
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p2,p4,p3,p1)));
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
    }

    @Test
    void getInstance() {
        priceSort = NameSort.getInstance();
        assertEquals(NameSort.getInstance(), priceSort);
    }


}