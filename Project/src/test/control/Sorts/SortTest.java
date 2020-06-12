package control.Sorts;

import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortTest {

    Product p1;
    Product p2;
    Product p3;
    Product p4;


    @BeforeEach
    void setup() {
        p1 = new Product(" ", "a", "", 0.00, null, 10, null, "", null);
        p2 = new Product(" ", "e", "", 0.00, null, 10, null, "", null);
        p3 = new Product(" ", "c", "", 0.00, null, 10, null, "", null);
        p4 = new Product(" ", "f", "", 0.00, null, 10, null, "", null);
    }

//    @Test
//    void applySort() {
//    }

    @Test
    void getName() {
        String test = p1.getName();
        assertEquals("a", test);
    }

    @Test
    void getAscending() {
        boolean test = NameSort.getInstance().getAscending();
        Sort sample = NameSort.getInstance();
        sample.applySort(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), false);
        assertTrue(test == false);
    }
}