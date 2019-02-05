import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventTest {
    private Event football;
    private Event volleyball;
    private Event football2;
    private Event volleyball2;

    @Before
    public void setup() {
        football = new Event("Football", 83);
        volleyball = new Event("Volleyball", 32);
        football2 = new Event("Football", 12);
        volleyball2 = new Event("Volleyball", 32);
    }

    @Test
    public void testCreate() {
        assertEquals("Football", football.getName());
        assertEquals(83, football.getTicketsRemaining());
    }

    @Test
    public void testToString() {
        assertEquals("Football - 83", football.toString());
    }

    @Test
    public void testCompare() {
        assertTrue(football.compareTo(volleyball) < 0);
        assertTrue(volleyball.compareTo(football) > 0);
        assertTrue(football2.compareTo(football) < 0);
        assertEquals(0, volleyball.compareTo(volleyball2));
    }

}
