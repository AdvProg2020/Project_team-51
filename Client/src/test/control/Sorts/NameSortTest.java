package control.Sorts;

import model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameSortTest {

    public Sort nameSort;
    Product p1;
    Product p2;
    Product p3;
    Product p4;


    @BeforeEach
    void setup() {
        nameSort = NameSort.getInstance();
        p1 = new Product(" ", "a", "", 0.00, null, 10, null, "", null);
        p2 = new Product(" ", "e", "", 0.00, null, 10, null, "", null);
        p3 = new Product(" ", "c", "", 0.00, null, 10, null, "", null);
        p4 = new Product(" ", "f", "", 0.00, null, 10, null, "", null);
    }

    @Test
    void applyAscendingSort() {
        List<Product> actual = nameSort.applySort(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), true);
        List<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p1, p3, p2, p4)));
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void applyDescendingSort() {
        List<Product> actual = nameSort.applySort(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), false);
        List<Product> expected = new ArrayList<>(new ArrayList<>(Arrays.asList(p4, p2, p3, p1)));
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    void getInstance() {
        nameSort = NameSort.getInstance();
        assertEquals(NameSort.getInstance(), nameSort);
    }


}