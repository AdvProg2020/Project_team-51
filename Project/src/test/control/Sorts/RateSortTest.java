package control.Sorts;

import model.Product;
import model.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateSortTest {
    public Sort rateSort;
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
        rateSort = RateSort.getInstance();
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
    void applyAscendingSort() {
        ArrayList<Product> actual = rateSort.applySort(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),true);
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p4,p1,p3,p2)));

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
    }

    @Test
    void applyDescendingSort() {
        ArrayList<Product> actual = rateSort.applySort(new ArrayList<>(Arrays.asList(p1,p2,p3,p4)),false);
        ArrayList<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p2,p3,p1,p4)));

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i),actual.get(i));
        }
    }

    @Test
    void getInstance() {
        rateSort = RateSort.getInstance();
        assertEquals(RateSort.getInstance(), rateSort);
    }

}