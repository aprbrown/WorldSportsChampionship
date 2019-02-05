import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientTest {
    private Client anna;
    private Client terry;
    private Client charlie;
    private Client anna2;

    @Before
    public void setup() {
        anna = new Client("Anna", "Smith");
        terry = new Client("Terry", "Bridges");
        charlie = new Client("Charlie", "Smith");
        anna2 = new Client("Anna", "Smith");

        anna.addEventToClient("Football", 2);
        anna.addEventToClient("Athletics", 4);
    }

    @Test
    public void testName() {
        assertEquals("Anna", anna.getFirstName());
        assertEquals("Smith", anna.getLastName());
        assertEquals("Anna Smith", anna.getFullName());
    }

    @Test
    public void testToString() {
        assertEquals("Terry Bridges", terry.toString());
        assertEquals("Anna Smith [Athletics - 4, Football - 2]", anna.toString());
    }

    @Test
    public void testCompare() {
        assertEquals(0, anna.compareTo(anna2));
        assertTrue(terry.compareTo(charlie) < 0);
        assertTrue(charlie.compareTo(anna) > 0);
    }

    @Test
    public void testEventsList() {
        assertEquals("Football - 2", anna.getEventsAttending().get(1).toString());
        assertEquals("Athletics - 4", anna.getEventsAttending().get(0).toString());
    }


}
