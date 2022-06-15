package group1;

import java.io.*;
import java.util.*;

/**
 * Collection class that manages to collection of groups for the program and
 * maintains the persistent file of groups.
 *
 * @author Chengyu She
 * @author Xu Shi
 * @author Vanessa Ross
 * @author Cindy Yang
 * @version 22.12/10/2019
 */
public class SeatCollection {

    ArrayList<Seat> seatList;
    //private fields
    private final String FILENAME = "seats.ser";
    private int studentPerGroup;
    private int maxGroups;

    /**
     * Default constructor.
     */
    public SeatCollection() {
        seatList = new ArrayList<>();
        readCollection();
    }

    /**
     * Clears the stored collection. Used when deleting the class.
     */
    public void deleteClass() {
        seatList.clear();
        writeCollection();
    }

    /**
     * Updates the stored collection. Used when a seat in the array list has
     * been updated.
     */
    public void updateSeat() {
        writeCollection();
    }

    /**
     * Gets the index of the seat to be disabled from the collection.
     *
     * @param i the seat object to disable
     */
    public void disableSeat(int i) {
        seatList.get(i).setOccupied(false);
        seatList.get(i).setGroupNumber(0);
        writeCollection();
    }

    /**
     * Gets the index of the seat to be enabled from the collection.
     *
     * @param i the seat object to enable
     */
    public void enableSeat(int i) {
        seatList.get(i).setOccupied(true);
        writeCollection();
    }

    /**
     * Provides access to the collection of seats as a sorted array.
     *
     * @return the sorted array
     */
    public Seat[] getSortedArray() {
        Collections.sort(seatList);
        return seatList.toArray(new Seat[seatList.size()]);
    }

    /**
     * Private method to write collection to a file.
     *
     * @return true if success, false if not
     */
    private boolean writeCollection() {
        boolean success = true;
        try (FileOutputStream fos = new FileOutputStream(FILENAME);
                ObjectOutputStream output = new ObjectOutputStream(fos)) {
            output.writeObject(seatList);
        } catch (Exception ex) {
            System.out.println("Cannot write to file:\n" + ex.getMessage());
            success = false;
        }
        return success;
    }

    /**
     * This method reads collection from a file.
     *
     * @return true if success, false if not
     */
    private boolean readCollection() {
        boolean success = true;
        File ser = new File(FILENAME);
        if (ser.exists()) {
            try (FileInputStream fis = new FileInputStream(FILENAME);
                    ObjectInputStream input = new ObjectInputStream(fis)) {
                seatList = (ArrayList<Seat>) input.readObject();
            } catch (Exception ex) {
                System.out.println("Cannot read from file:\n" + ex.getMessage());
                success = false;
            }
        }
        return success;
    }

    /**
     * Generate seats based on number of rows and columns.
     *
     * @param rows the number of rows of the classroom.
     * @param columns the number of columns of the classroom.
     */
    public void createClass(int rows, int columns) {
        deleteClass();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seatList.add(new Seat(i + 1, j + 1));
            }
        }
        //Set temp student names.
        for (int i = 0; i < seatList.size(); i++) {
            seatList.get(i).setStudentName("Student " + (i + 1));
        }
        writeCollection();
    }

    /**
     * Prints the class from the stored collection.
     */
    public void printClass() {
        readCollection();
        for (Seat s : seatList) {
            System.out.print("[" + s + "]: " + s.getGroupNumber() + " ");
        }
    }

    /**
     * Counts the number of present students in the class.
     *
     * @return the number of present students
     */
    public int countPresentStudents() {
        int presentStudents = 0;
        for (Seat s : seatList) {
            if (s.isOccupied()) {
                presentStudents += 1;
            }
        }
        return presentStudents;
    }

    /**
     * Generates the maximum number of groups in a classroom.
     *
     * @param maxGroups the maximum number of groups
     */
    public void generateByMaxGroups(int maxGroups) {
        readCollection();
        int classSize = countPresentStudents();
        int studentPerGroup = (classSize / maxGroups) + 1;
        startGenerating(studentPerGroup, maxGroups);
    }

    /**
     * Generates the number of students per group in a classroom.
     *
     * @param studentPerGroup the number of students per group
     */
    public void generateByStudentPerGroup(int studentPerGroup) {
        readCollection();
        int classSize = countPresentStudents();
        int maxGroups;
        if (classSize % studentPerGroup == 0) {
            maxGroups = classSize / studentPerGroup;

        } else {
            maxGroups = (classSize / studentPerGroup) + 1;
        }
        startGenerating(studentPerGroup, maxGroups);
    }

    /**
     * Divides occupied seats into groups based on the maximum number of groups
     * and the number of students per group. This method also makes sure even
     * distribution of the groups.
     *
     * @param studentPerGroup the number of students per group
     * @param maxGroups the maximum number of groups
     */
    public void startGenerating(int studentPerGroup, int maxGroups) {
        readCollection();
        Random rand = new Random();
        int[] groups = new int[maxGroups];
        //Use temperary list to ensure evenly distributed groups.
        ArrayList<Seat> temp = createPresentStudentsArray();
        for (int i = 0; i < studentPerGroup; i++) {
            for (int j = 0; j < groups.length; j++) {
                if (temp.size() > 0) {
                    int random = rand.nextInt(temp.size());
                    temp.get(random).setGroupNumber(j + 1);
                    temp.remove(random);
                    groups[j] += 1;
                }
            }
        }
        //Check for single person groups.
        ArrayList<Integer> singleGroups = new ArrayList<>();
        ArrayList<Seat> temp2 = createPresentStudentsArray();
        for (int i = 0; i < groups.length; i++) {
            if (groups[i] == 1) {
                singleGroups.add(i);
                for (Seat s : temp2) {
                    if (s.getGroupNumber() == (i + 1)) {
                        int random;
                        do {
                            random = rand.nextInt(groups.length);
                        } while (singleGroups.contains(random));
                        s.setGroupNumber(random + 1);
                    }
                }
            }
        }
        writeCollection();
    }

    /**
     * Creates an ArrayList of group numbers.
     *
     * @return an integer array list of group numbers.
     */
    public ArrayList<Integer> getGroupsArray() {
        //Count the number of groups.
        ArrayList<Integer> groupCount = new ArrayList<>();
        ArrayList<Seat> temp = createPresentStudentsArray();
        for (Seat s : temp) {
            if (!groupCount.contains(s.getGroupNumber())) {
                groupCount.add(s.getGroupNumber());
            }
        }
        return groupCount;
    }

    /**
     * Counts the number of groups.
     *
     * @return the total number of groups.
     */
    public int countGroups() {
        //Count the number of groups.
        ArrayList<Integer> groupCount = new ArrayList<>();
        ArrayList<Seat> temp = createPresentStudentsArray();
        for (Seat s : temp) {
            if (!groupCount.contains(s.getGroupNumber())) {
                groupCount.add(s.getGroupNumber());
            }
        }
        return groupCount.size();
    }

    /**
     * Prints the groups and the students in each group.
     *
     * @return a string array where each element contains students who are in
     * the same group
     */
    public String[] printGroups() {
        readCollection();
        //Count the number of groups.
        int count = countGroups();
        String[] namesInGroups = new String[count];
        ArrayList<Seat> temp = createPresentStudentsArray();
        for (int i = 0; i < count; i++) {
            namesInGroups[i] = "                  Group " + (i + 1) + ":    ";
            for (Seat s : temp) {
                if (s.getGroupNumber() == (i + 1)) {
                    namesInGroups[i] += s.getStudentName() + ",  ";
                }
            }
            //Removes last comma.
            namesInGroups[i] = namesInGroups[i].substring(0, namesInGroups[i].length() - 3);
        }
        return namesInGroups;
    }

    /**
     * Creates an array list of occupied seats.
     *
     * @return an array list of occupied seats.
     */
    public ArrayList<Seat> createPresentStudentsArray() {
        //Use temperary list to ensure evenly distributed groups.
        ArrayList<Seat> temp = new ArrayList<>();
        for (Seat s : seatList) {
            if (s.isOccupied()) {
                temp.add(s);
            }
        }
        return temp;
    }

    /**
     * Sets the occupied status to true or false.
     *
     * @param s the seat object's status to be changed.
     */
    public void disableSeat(Seat s) {
        if (s.isOccupied()) {
            s.setOccupied(false);
        } else {
            s.setOccupied(true);
        }
        writeCollection();
    }

    /**
     * Takes two indexes and swaps the name, occupied seat, and group number.
     *
     * @param index1 the index of the first seat.
     * @param index2 the index of the seat to be swapped.
     */
    public void swapSeats(int index1, int index2) {
        //Swap name, occupied and groupNumber.
        String tempName = seatList.get(index1).getStudentName();
        seatList.get(index1).setStudentName(seatList.get(index2).getStudentName());
        seatList.get(index2).setStudentName(tempName);
        boolean tempOccupied = seatList.get(index1).isOccupied();
        seatList.get(index1).setOccupied(seatList.get(index2).isOccupied());
        seatList.get(index2).setOccupied(tempOccupied);
        int tempGroupNumber = seatList.get(index1).getGroupNumber();
        seatList.get(index1).setGroupNumber(seatList.get(index2).getGroupNumber());
        seatList.get(index2).setGroupNumber(tempGroupNumber);
        writeCollection();
    }

    /**
     * Provides access to the total number of seats in the ArrayList.
     *
     * @return the total number of seat objects.
     */
    public int getSize() {
        return seatList.size();
    }

    /**
     * Provides access to the collection of rows and columns in an array.
     *
     * @return the rows and columns in an array with two elements.
     */
    public int[] getRowsColumns() {
        int[] rowsColumns = new int[2];
        ArrayList<Integer> rowCounter = new ArrayList<>();
        ArrayList<Integer> columnCounter = new ArrayList<>();
        //Check for rows.
        for (Seat s : seatList) {
            if (!rowCounter.contains(s.getRow())) {
                rowCounter.add(s.getRow());
            }
        }
        rowsColumns[0] = rowCounter.size();
        for (Seat s : seatList) {
            if (!columnCounter.contains(s.getColumn())) {
                columnCounter.add(s.getColumn());
            }
        }
        rowsColumns[1] = columnCounter.size();
        return rowsColumns;
    }

    /**
     * Changes a student's name from the collection. This method is used when
     * the user changes the student's name.
     *
     * @param index the index of the student's name in the collection
     * @param name the student's name
     */
    public void changeStudentName(int index, String name) {
        seatList.get(index).setStudentName(name);
        writeCollection();
    }

    /**
     * Resets group numbers. This method resets all group numbers to zero.
     */
    public void resetGroupNumbers() {
        for (Seat s : seatList) {
            s.setGroupNumber(0);
        }
    }

    /**
     * Creates an array list of the disabled seats.
     *
     * @return an array list of disabled seats.
     */
    public ArrayList<Integer> getDisabledSeatsArray() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).isOccupied() == false) {
                arr.add(i);
            }
        }
        return arr;
    }
}
