package group1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Group1
 */
public class SeatCollectionTest {
    
    public SeatCollectionTest() {
    }
    
    SeatCollection collection;
    SeatCollection scollection;
    Seat s1;
    
    @Before
    public void setUp() {
        //initialize collection
        collection = new SeatCollection();
        scollection = new SeatCollection();
        //clear existing Seats.
        collection.deleteClass();
        collection.createClass(2, 2);
        scollection.deleteClass();
        scollection.createClass(3, 3);
    }
    
    /**
     * Test of deleteClass method, of class SeatCollection.
     */
    @Test
    public void testDeleteClass() {
        int count = collection.getSortedArray().length;
        assertSame(count, 4);
        collection.deleteClass();
        count = collection.getSortedArray().length;
        assertSame(count, 0);
        //test that the file reflects the change
        SeatCollection collection2 = new SeatCollection();
        Seat[] seats = collection2.getSortedArray();
        assertSame(seats.length, 0);
    }

    /**
     * Test of updateSeat method, of class SeatCollection.
     */
    @Test
    public void testUpdateSeat() {
        //alter a field of a Seat
        Seat[] seats1 = collection.getSortedArray();
        seats1[0].setStudentName("Vanessa");
        //update the collection
        collection.updateSeat();
        //test that the file reflects the change
        SeatCollection collection2 = new SeatCollection();
        Seat[] seats2 = collection2.getSortedArray();
        assertEquals(seats2[0].getStudentName(), "Vanessa");
    }

    /**
     * Test of disableSeat method, of class SeatCollection.
     */
    @Test
    public void testDisableSeat_int() {
        collection.disableSeat(0);
        collection.updateSeat();
        SeatCollection collection2 = new SeatCollection();
        Seat[] seats2 = collection2.getSortedArray();
        assertEquals(seats2[0].isOccupied(), false);
    }

    /**
     * Test of enableSeat method, of class SeatCollection.
     */
    @Test
    public void testEnableSeat() {
        collection.disableSeat(0);
        collection.updateSeat();
        SeatCollection collection2 = new SeatCollection();
        Seat[] seats2 = collection2.getSortedArray();
        assertEquals(seats2[0].isOccupied(), false);
        collection.enableSeat(0);
        collection.updateSeat();
        SeatCollection collection3 = new SeatCollection();
        Seat[] seats3 = collection3.getSortedArray();
        assertEquals(seats3[0].isOccupied(), true);
    }

    /**
     * Test of createClass method, of class SeatCollection.
     */
    @Test
    public void testCreateClass() {
        SeatCollection collection2 = new SeatCollection();
        collection2.createClass(4, 7);
        Seat[] seats2 = collection2.getSortedArray();
        assertEquals(seats2.length, 28);
    }

    /**
     * Test of countPresentStudents method, of class SeatCollection.
     */
    @Test
    public void testCountPresentStudents() {
        collection.disableSeat(0);
        collection.updateSeat();
        int count = collection.countPresentStudents();
        assertEquals(count, 3);
        scollection.disableSeat(0);
        scollection.disableSeat(1);
        scollection.updateSeat();
        int count2 = scollection.countPresentStudents();
        assertEquals(count2, 7);
    }

    /**
     * Test of generateByMaxGroups method, of class SeatCollection.
     */
    @Test
    public void testGenerateByMaxGroups() {
        collection.generateByMaxGroups(2);
        Seat[] seats = collection.getSortedArray();
        int count = 1;
        for (int i = 1; i < seats.length; i++) {
            if (seats[i].getGroupNumber() != seats[i - 1].getGroupNumber()) {
                count += 1;
            }
        }
        assertEquals(count, 2);
    }

    /**
     * Test of generateByStudentPerGroup method, of class SeatCollection.
     */
    @Test
    public void testGenerateByStudentPerGroup() {
        scollection.generateByStudentPerGroup(3);
        Seat[] seats = scollection.getSortedArray();
        int count = 1;
        for (int i = 1; i < seats.length; i++) {
            if (seats[i].getGroupNumber() != seats[i - 1].getGroupNumber()) {
                count += 1;
            }
        }
        assertEquals(count, 3);
    }

    /**
     * Test of startGenerating method, of class SeatCollection.
     */
    @Test
    public void testStartGenerating() {
        scollection.startGenerating(3, 4);
        Seat[] seats = scollection.getSortedArray();
        int count = 1;
        for (int i = 1; i < seats.length; i++) {
            if (seats[i].getGroupNumber() != seats[i - 1].getGroupNumber()) {
                count += 1;
            }
        }
        assertEquals(count, 4);
        scollection.startGenerating(5, 2);
        Seat[] seats2 = scollection.getSortedArray();
        int count2 = 1;
        for (int i = 1; i < seats2.length; i++) {
            if (seats2[i].getGroupNumber() != seats2[i - 1].getGroupNumber()) {
                count2 += 1;
            }
        }
        assertEquals(count2, 2);
    }

    /**
     * Test of countGroups method, of class SeatCollection.
     */
    @Test
    public void testCountGroups() {
        scollection.generateByMaxGroups(3);
        int count = scollection.countGroups();
        assertEquals(count, 3);
    }

    /**
     * Test of swapSeats method, of class SeatCollection.
     */
    @Test
    public void testSwapSeats() {
        Seat[] seats = collection.getSortedArray();
        seats[0].setGroupNumber(1);
        seats[1].setGroupNumber(3);
        seats[0].setStudentName("Henry");
        seats[1].setStudentName("Cindy");
        seats[0].setOccupied(true);
        seats[1].setOccupied(false);
        collection.swapSeats(0, 1);
        assertEquals(seats[0].getGroupNumber(), 3);
        assertEquals(seats[0].getStudentName(), "Cindy");
        assertFalse(seats[0].isOccupied());
        assertEquals(seats[1].getGroupNumber(), 1);
        assertEquals(seats[1].getStudentName(), "Henry");
        assertTrue(seats[1].isOccupied());
    }

    /**
     * Test of getRowsColumns method, of class SeatCollection.
     */
    @Test
    public void testGetRowsColumns() {
        SeatCollection collection2 = new SeatCollection();
        collection2.createClass(5, 8);
        int[] rowsColumns = collection2.getRowsColumns();
        assertEquals(rowsColumns[0], 5);
        assertEquals(rowsColumns[1], 8);
    }

    /**
     * Test of changeStudentName method, of class SeatCollection.
     */
    @Test
    public void testChangeStudentName() {
        collection.changeStudentName(0, "Xu");
        Seat[] seats = collection.getSortedArray();
        assertEquals(seats[0].getStudentName(), "Xu");
    }

    /**
     * Test of resetGroupNumbers method, of class SeatCollection.
     */
    @Test
    public void testResetGroupNumbers() {
        Seat[] seats = collection.getSortedArray();
        seats[0].setGroupNumber(5);
        seats[1].setGroupNumber(4);
        seats[2].setGroupNumber(8);
        seats[3].setGroupNumber(1);
        collection.resetGroupNumbers();
        for (Seat s: seats) {
            assertEquals(s.getGroupNumber(), 0);
        }
    }
}
