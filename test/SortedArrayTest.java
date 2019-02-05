import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class SortedArrayTest {
    private SortedArrayList<String> sortedStringList;
    private SortedArrayList<Integer> sortedIntList;

    @Before
    public void setup() {
        sortedStringList = new SortedArrayList<>();
        sortedStringList.add("Beta");
        sortedStringList.add("Golf");
        sortedStringList.add("Foxtrot");
        sortedStringList.add("Alpha");

        sortedIntList = new SortedArrayList<>();
        sortedIntList.add(9);
        sortedIntList.add(3);
        sortedIntList.add(10);
        sortedIntList.add(5);
        sortedIntList.add(3);
        sortedIntList.add(8);
    }

    @Test
    public void testAdd() {
        assert(sortedStringList.contains("Alpha"));
        assert(sortedStringList.contains("Beta"));
    }

    @Test
    public void testSortedAddStrings() {
        assertEquals("Foxtrot", sortedStringList.get(2));
        assertEquals("Golf", sortedStringList.get(3));
    }

    @Test
    public void testSortedAddInt() {
        assertEquals("3", sortedIntList.get(0).toString());
        assertEquals("9", sortedIntList.get(4).toString());
    }

    @Test
    public void testBinarySearch() {
        assertEquals(3, Collections.binarySearch(sortedIntList, 8));
    }

}
