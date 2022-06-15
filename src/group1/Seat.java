package group1;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is the data class that represents a seat object in a classroom.
 *
 * @author Chengyu She
 * @author Xu Shi
 * @author Vanessa Ross
 * @author Cindy Yang
 * @version 22.0 12/10/2019
 */
public class Seat implements Comparable<Seat>, Serializable {

    //private fields
    private boolean occupied = true;
    private int row;
    private int column;
    private String studentName;
    private int groupNumber = 0;

    /**
     * Full constructor.
     *
     * @param row the number of rows in a classroom
     * @param column the number of columns in a classroom
     */
    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Provides access to the seat if it is occupied or not.
     *
     * @return the occupied status of the seat.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Allows the occupied seat to be set.
     *
     * @param occupied the occupied status to be set, either true or false.
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * Provides access to the number of rows in a classroom.
     *
     * @return the row position of the seat.
     */
    public int getRow() {
        return row;
    }

    /**
     * Allows the number of rows in a classroom to be set.
     *
     * @param row the row to be set.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Provides access to the number of columns in a classroom.
     *
     * @return the column position of the seat.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Allows the number of columns in a classroom to be set.
     *
     * @param column the column to be set.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Provides access to the student's name.
     *
     * @return the student's name that sets on the seat.
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * Allows the student's name to be set.
     *
     * @param studentName name to be set.
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Provides access to the group number.
     *
     * @return the group number.
     */
    public int getGroupNumber() {
        return groupNumber;
    }

    /**
     * Allows the number of groups in a classroom to be set.
     *
     * @param groupNumber the group number to be set.
     */
    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Provides a string representation of the seat.
     *
     * @return the student's name
     */
    @Override
    public String toString() {
        return studentName;
    }

    /**
     * Creates a unique identifier for the student.
     *
     * @return the unique identifier
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.studentName);
        return hash;
    }

    /**
     * Determines if one student has the same name as another.
     *
     * @param obj the other student
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seat other = (Seat) obj;
        if (!Objects.equals(this.studentName, other.studentName)) {
            return false;
        }
        return true;
    }

    /**
     * Compares a group with another for sorting purposes.
     *
     * @param other the other group
     * @return a number less than 0 if this group should come before the other,
     * 0 if they are the same, and a number greater than 0 if this group should
     * come after the other group.
     */
    @Override
    public int compareTo(Seat other) {
        return Integer.compare(this.groupNumber, other.groupNumber);
    }
}
