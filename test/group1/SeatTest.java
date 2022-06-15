package group1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit Tests class for testing the Seat class.
 *
 * @author Group1
 * @version 1 12/6/2019
 */
public class SeatTest {

    Seat s1, s2, s3;

    @Before
    public void setUp() {
        s1 = new Seat(2, 3);
        s1.setStudentName("Herny");
        s1.setGroupNumber(1);
        s2 = new Seat(2, 3);
        s2.setStudentName("Herny");
        s2.setGroupNumber(1);
        s3 = new Seat(3, 4);
        s3.setStudentName("Jam");
        s3.setGroupNumber(2);
    }

    /**
     * Test of toString method, of class Seat.
     */
    @Test
    public void testToString() {
        assertEquals("Herny", s1.toString());
    }

    /**
     * Test of equals method, of class Seat.
     */
    @Test
    public void testEquals() {
        assertTrue(s1.equals(s2));
        assertFalse(s1.equals(s3));
    }

    /**
     * Test of compareTo method, of class Seat.
     */
    @Test
    public void testCompareTo() {
        assertSame(0, s1.compareTo(s2));
        assertTrue(s1.compareTo(s3) < 0);
    }
}
