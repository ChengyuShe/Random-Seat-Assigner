package group1;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This class constructs the GUI.
 *
 * @author Chengyu She
 * @author Xu Shi
 * @author Vanessa Ross
 * @author Cindy Yang
 * @version 22.12/10/2019
 */
public class SeatView extends JFrame {

    private SeatCollection list = new SeatCollection();
    CardLayout c1;
    JLabel lblClassName;
    JLabel lblStudentGroupNumber;
    JLabel lblGroupInput;
    JPanel pnlClassView;
    JPanel pnlCenter;
    JPanel pnlClass;
    JPanel pnlStudent;
    JPanel pnlGroups;
    JPanel pnlEdit;
    JPanel pnlSetRowColumns;
    JPanel pnlClassLayout;
    JPanel pnlGroupsLayout;
    JPanel pnlGroupsButtons;
    JToggleButton tbtnClass;
    JToggleButton tbtnStudent;
    JToggleButton tbtnGroups;
    JToggleButton tbtnEdit;
    JTextField txtSetRows;
    JTextField txtSetColumns;
    JTextField txtStudentName;
    JTextField txtGroupInput;
    private JButton btnConfirmDelete;
    private JButton btnGenerate;
    private JButton btnStudentUpdateConfirm;
    private JButton btnStudentDisableEnable;
    private JButton btnSwap;
    ArrayList<JButton> buttonList;
    ArrayList<JPanel> seatRowPanels;
    ArrayList<JPanel> groupPanels = new ArrayList<>();
    ButtonGroup grpGenerateMethods;
    ButtonGroup grpClassView;
    JRadioButton rbStudentPerGroup;
    JRadioButton rbMaxGroups;
    Color colorDefaultBack = Color.WHITE;
    Color colorDefaultFore = Color.BLACK;
    Color colorDisabledBack = Color.BLACK;
    Color colorDisabledFore = Color.DARK_GRAY;
    Color[] colorList;
    int[] rowsColumns;
    String errorInteger = "Please enter an integer.";
    String errorEmpty = "Please enter a value.";
    String errorClassSize = "Please enter values between 1 and 10";
    String errorNotEnoghStudents = "Not enogh present students!";
    String errorColor = "Cannot load color groups, too many groups.";
    String errorLessThanOne = "Please enter an integer greater than 1.";
    String errorNameTooLong = "Student Name is too long.";
    int selectedStudentIndex;
    boolean swapping = false;

    /**
     * The constructor.
     *
     * @param title the frame title
     */
    public SeatView(String title) {
        super(title);
        addComponents();
        addEventHandlers();
        selectedStudentIndex = 0;
        rowsColumns = list.getRowsColumns();
        list.resetGroupNumbers();
        if (rowsColumns[0] != 0) {
            loadSeats(rowsColumns[0], rowsColumns[1]);
            btnConfirmDelete.setText("Delete Class");
            txtSetRows.disable();
            txtSetColumns.disable();
            txtSetRows.setText("" + rowsColumns[0]);
            txtSetColumns.setText("" + rowsColumns[1]);
            showStudentPanel(true);
        }
    }

    /**
     * A method used to add components to the frame.
     */
    public void addComponents() {
        //Inventory panel
        grpClassView = new ButtonGroup();
        pnlClassView = new JPanel();
        pnlClassView.setLayout(new GridLayout(1, 0));
        tbtnEdit = new JToggleButton("Edit");
        pnlClassView.add(tbtnEdit);
        grpClassView.add(tbtnEdit);
        tbtnEdit.setSelected(true);
        tbtnClass = new JToggleButton("Class");
        pnlClassView.add(tbtnClass);
        grpClassView.add(tbtnClass);
        tbtnStudent = new JToggleButton("Student");
        tbtnGroups = new JToggleButton("Groups");
        pnlClassView.add(tbtnGroups);
        grpClassView.add(tbtnGroups);
        add(pnlClassView, BorderLayout.NORTH);
        //Edit panel
        pnlEdit = new JPanel();
        pnlEdit.setLayout(new BoxLayout(pnlEdit, BoxLayout.Y_AXIS));
        JPanel pnlEditHeader = new JPanel();
        pnlEditHeader.setLayout(new BorderLayout());
        JLabel lblEdit = new JLabel("Edit Class Information");
        lblEdit.setHorizontalAlignment(SwingConstants.CENTER);
        pnlEditHeader.add(lblEdit);
        pnlEdit.add(pnlEditHeader);
        JPanel pnlSetClassRows = new JPanel();
        pnlSetClassRows.setAlignmentY(LEFT_ALIGNMENT);
        pnlSetClassRows.add(new JLabel("Class rows:"));
        txtSetRows = new JTextField(10);
        pnlSetClassRows.add(txtSetRows);
        pnlEdit.add(pnlSetClassRows);
        pnlSetRowColumns = new JPanel();
        pnlSetRowColumns.setAlignmentY(LEFT_ALIGNMENT);
        pnlSetRowColumns.add(new JLabel("Class columns:"));
        txtSetColumns = new JTextField(10);
        pnlSetRowColumns.add(txtSetColumns);
        pnlEdit.add(pnlSetRowColumns);
        JPanel pnlEditButtons = new JPanel();
        btnConfirmDelete = new JButton("Confirm");
        pnlEditButtons.add(btnConfirmDelete);
        pnlEdit.add(pnlEditButtons);
        //Class panel
        pnlClass = new JPanel();
        pnlClass.setLayout(new BoxLayout(pnlClass, BoxLayout.Y_AXIS));
        JPanel pnlClassHeader = new JPanel();
        pnlClassHeader.setLayout(new BorderLayout());
        lblClassName = new JLabel("My Class");
        lblClassName.setHorizontalAlignment(SwingConstants.CENTER);
        pnlClassHeader.add(lblClassName);
        pnlClass.add(pnlClassHeader);
        pnlClassLayout = new JPanel();
        pnlClassLayout.setLayout(new BoxLayout(pnlClassLayout, BoxLayout.Y_AXIS));
        pnlClass.add(pnlClassLayout);
        //Groups panel
        pnlGroups = new JPanel();
        pnlGroups.setLayout(new BoxLayout(pnlGroups, BoxLayout.Y_AXIS));
        JPanel pnlGroupsHeader = new JPanel();
        pnlGroupsHeader.setLayout(new BorderLayout());
        JLabel lblGroups = new JLabel("Groups");
        lblGroups.setHorizontalAlignment(SwingConstants.CENTER);
        pnlGroupsHeader.add(lblGroups);
        pnlGroups.add(pnlGroupsHeader);
        JPanel pnlGenerate = new JPanel();
        pnlGenerate.add(new JLabel("Generate Mode: "));
        grpGenerateMethods = new ButtonGroup();
        rbStudentPerGroup = new JRadioButton("Student per Group", true);
        rbMaxGroups = new JRadioButton("Max Groups");
        grpGenerateMethods.add(rbStudentPerGroup);
        grpGenerateMethods.add(rbMaxGroups);
        pnlGenerate.add(rbStudentPerGroup);
        pnlGenerate.add(rbMaxGroups);
        pnlGroups.add(pnlGenerate);
        JPanel pnlGroupInput = new JPanel();
        lblGroupInput = new JLabel("Number input: ");
        pnlGroupInput.add(lblGroupInput);
        txtGroupInput = new JTextField(5);
        pnlGroupInput.add(txtGroupInput);
        pnlGroups.add(pnlGroupInput);
        pnlGroupsButtons = new JPanel();
        btnGenerate = new JButton("Generate");
        pnlGroupsButtons.add(btnGenerate);
        pnlGroups.add(pnlGroupsButtons);
        pnlGroupsLayout = new JPanel();
        pnlGroupsLayout.setLayout(new BoxLayout(pnlGroupsLayout, BoxLayout.Y_AXIS));
        pnlGroups.add(pnlGroupsLayout);
        //Student panel
        pnlStudent = new JPanel();
        pnlStudent.setLayout(new BoxLayout(pnlStudent, BoxLayout.Y_AXIS));
        JPanel pnlStudentHeader = new JPanel();
        pnlStudentHeader.setLayout(new BorderLayout());
        JLabel lblStudent = new JLabel("Student information");
        lblStudent.setHorizontalAlignment(SwingConstants.CENTER);
        pnlStudentHeader.add(lblStudent);
        pnlStudent.add(pnlStudentHeader);
        JPanel pnlStudentName = new JPanel();
        pnlStudentName.add(new JLabel("Student name: "));
        txtStudentName = new JTextField(10);
        pnlStudentName.add(txtStudentName);
        txtStudentName.disable();
        pnlStudent.add(pnlStudentName);
        JPanel pnlStudentGroupNumber = new JPanel();
        pnlStudentGroupNumber.add(new JLabel("Group Number: "));
        lblStudentGroupNumber = new JLabel("");
        pnlStudentGroupNumber.add(lblStudentGroupNumber);
        pnlStudentGroupNumber.setAlignmentY(LEFT_ALIGNMENT);
        pnlStudent.add(pnlStudentGroupNumber);
        JPanel pnlStudentButtons = new JPanel();
        btnStudentUpdateConfirm = new JButton("Update");
        pnlStudentButtons.add(btnStudentUpdateConfirm);
        btnStudentDisableEnable = new JButton("Disable");
        pnlStudentButtons.add(btnStudentDisableEnable);
        btnSwap = new JButton("Swap Seat");
        pnlStudentButtons.add(btnSwap);
        pnlStudent.add(pnlStudentButtons);
        //Center panel
        c1 = new CardLayout();
        pnlCenter = new JPanel(c1);
        pnlCenter.add(pnlEdit, "Edit");
        pnlCenter.add(pnlGroups, "Groups");
        pnlCenter.add(pnlClass, "Class");
//        pnlCenter.add(pnlStudent, "Student");
        c1.show(pnlCenter, "Edit");
        add(pnlCenter, BorderLayout.CENTER);
    }

    /**
     * Private method used to set up the event handlers.
     */
    private void addEventHandlers() {
        tbtnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(pnlCenter, "Edit");
                resetWindow();
            }
        });
        tbtnClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.countGroups() > 1) {
                    showClassLayoutWithColors();
                }
                c1.show(pnlCenter, "Class");
                resetWindow();
            }
        });
        tbtnGroups.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(pnlCenter, "Groups");
                resetWindow();
            }
        });
        tbtnStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c1.show(pnlCenter, "Student");
                resetWindow();
            }
        });

        btnConfirmDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnConfirmDelete.getText().equals("Confirm")) {
                    if (!txtSetRows.getText().isEmpty() && !txtSetColumns.getText().isEmpty()) {
                        try {
                            int rows = Integer.parseInt(txtSetRows.getText());
                            int columns = Integer.parseInt(txtSetColumns.getText());
                            if (rows >= 1 && rows <= 10 && columns >= 1 && columns <= 10) {
                                list.createClass(rows, columns);
                                loadSeats(rows, columns);
                                c1.show(pnlCenter, "Class");
                                resetWindow();
                                txtSetRows.disable();
                                txtSetColumns.disable();
                                btnConfirmDelete.setText("Delete Class");
                                showStudentPanel(true);
                            } else {
                                txtSetRows.setText("");
                                txtSetColumns.setText("");
                                showErrorMessage(errorClassSize);
                                SeatView.this.txtSetRows.requestFocusInWindow();
                            }
                        } catch (NumberFormatException ime) {
                            //custom title, error icon
                            txtSetRows.setText("");
                            txtSetColumns.setText("");
                            showErrorMessage(errorInteger);
                            SeatView.this.txtSetRows.requestFocusInWindow();
                        }
                    }
                } else if (btnConfirmDelete.getText().equals("Delete Class")) {
                    txtSetRows.enable();
                    txtSetColumns.enable();
                    list.deleteClass();
                    txtSetRows.setText("");
                    txtSetColumns.setText("");
                    SeatView.this.txtSetRows.requestFocusInWindow();
                    for (JPanel p : seatRowPanels) {
                        pnlClassLayout.remove(p);
                    }
                    seatRowPanels.removeAll(buttonList);
                    resetGroupsLayout();
                    btnConfirmDelete.setText("Confirm");
                    showStudentPanel(false);
                    resetWindow();
                }
            }
        });

        btnStudentDisableEnable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnStudentDisableEnable.getText().equals("Disable")) {
                    list.disableSeat(selectedStudentIndex);
                    showClassLayout();
                    buttonList.get(selectedStudentIndex).setBackground(colorDisabledBack);
                    buttonList.get(selectedStudentIndex).setForeground(colorDisabledFore);
                } else if (btnStudentDisableEnable.getText().equals("Enable")) {
                    list.enableSeat(selectedStudentIndex);
                    showClassLayout();
                    buttonList.get(selectedStudentIndex).setBackground(colorDefaultBack);
                    buttonList.get(selectedStudentIndex).setForeground(colorDefaultFore);
                }
            }
        });

        btnStudentUpdateConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (btnStudentUpdateConfirm.getText().equals("Update")) {
                    txtStudentName.enable();
                    SeatView.this.txtStudentName.requestFocusInWindow();
                    txtStudentName.selectAll();
                    btnStudentUpdateConfirm.setText("Confirm");
                } else if (btnStudentUpdateConfirm.getText().equals("Confirm")) {
                    if (!txtStudentName.getText().equals("")) {
                        if (txtStudentName.getText().length() <= 16) {
                            list.changeStudentName(selectedStudentIndex, txtStudentName.getText());
                            btnStudentUpdateConfirm.setText("Update");
                            txtStudentName.disable();
                            showClassLayout();
                            buttonList.get(selectedStudentIndex).setText(txtStudentName.getText());
                            resetWindow();
                        } else {
                            showErrorMessage(errorNameTooLong);
                            txtStudentName.setText(list.seatList.get(selectedStudentIndex).getStudentName());
                        }
                    } else {
                        showErrorMessage(errorEmpty);
                        txtStudentName.setText(list.seatList.get(selectedStudentIndex).getStudentName());
                    }
                }
            }
        });

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list.countPresentStudents() > 1) {
                    if (rbStudentPerGroup.isSelected()) {
                        try {
                            int studentPerGroup = Integer.parseInt(txtGroupInput.getText());
                            if (studentPerGroup > 1) {
                                int classSize = list.countPresentStudents();
                                if (studentPerGroup < classSize) {
                                    resetGroupsLayout();
                                    list.generateByStudentPerGroup(studentPerGroup);
                                    showGroups();
                                } else {
                                    txtGroupInput.setText("");
                                    SeatView.this.txtGroupInput.requestFocusInWindow();
                                    showErrorMessage(errorNotEnoghStudents);
                                }
                            } else {
                                txtGroupInput.setText("");
                                SeatView.this.txtGroupInput.requestFocusInWindow();
                                showErrorMessage(errorLessThanOne);
                            }
                        } catch (NumberFormatException ime) {
                            //custom title, error icon
                            txtGroupInput.setText("");
                            showErrorMessage(errorInteger);
                            SeatView.this.txtGroupInput.requestFocusInWindow();
                        }

                    } else if (rbMaxGroups.isSelected()) {
                        resetGroupsLayout();
                        try {
                            int maxGroups = Integer.parseInt(txtGroupInput.getText());
                            if (maxGroups > 1) {
                                int classSize = list.countPresentStudents();
                                if (maxGroups <= classSize / 2) {
                                    list.generateByMaxGroups(maxGroups);
                                    showGroups();
                                } else {
                                    txtGroupInput.setText("");
                                    SeatView.this.txtGroupInput.requestFocusInWindow();
                                    showErrorMessage(errorNotEnoghStudents);
                                }
                            } else {
                                txtGroupInput.setText("");
                                SeatView.this.txtGroupInput.requestFocusInWindow();
                                showErrorMessage(errorLessThanOne);
                            }
                        } catch (NumberFormatException ime) {
                            //custom title, error icon
                            txtGroupInput.setText("");
                            showErrorMessage(errorInteger);
                            SeatView.this.txtGroupInput.requestFocusInWindow();
                        }
                    }
                } else {
                    showClassLayout();
                    showErrorMessage(errorNotEnoghStudents);
                }
            }
        });

        btnSwap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapping = true;
                showClassLayout();
                showSwapSeatColor(selectedStudentIndex);
            }
        });
    }

    /**
     * Resets the window to the center of the screen.
     */
    public void resetWindow() {
        SeatView.this.pack();
        SeatView.this.setLocationRelativeTo(null);
    }

    /**
     * Loads buttons representing seats in a classroom with action listeners.
     * This method also changes the color of the button representing the seat if
     * the seat is not occupied.
     *
     * @param rows the number of rows
     * @param columns the number of columns
     */
    public void loadSeats(int rows, int columns) {
        //Create buttons for each seat.
        tbtnClass.doClick();
        buttonList = new ArrayList<>();
        seatRowPanels = new ArrayList<>();
        buttonList.clear();
        seatRowPanels.clear();
        for (int i = 0; i < rows; i++) {
            JPanel panel = new JPanel();
            seatRowPanels.add(panel);
            pnlClassLayout.add(panel);
            for (int j = 0; j < columns; j++) {
                JButton button = new JButton();
                button.setBorderPainted(false);
                button.setOpaque(true);
                buttonList.add(button);
                seatRowPanels.get(i).add(button);
                button.setText(list.seatList.get(buttonList.indexOf(button)).toString());
                //set button colors based on occupied status
                if (list.seatList.get(buttonList.indexOf(button)).isOccupied()) {
                    button.setBackground(colorDefaultBack);
                    button.setForeground(colorDefaultFore);
                } else {
                    button.setBackground(colorDisabledBack);
                    button.setForeground(colorDisabledFore);
                }
                //Button actionListeners.
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!swapping) {
                            selectedStudentIndex = buttonList.indexOf(button);
                            txtStudentName.setText(list.seatList.get(selectedStudentIndex).toString());
                            int groupNumber = list.seatList.get(selectedStudentIndex).getGroupNumber();
                            lblStudentGroupNumber.setText(Integer.toString(groupNumber));
                            if (list.seatList.get(selectedStudentIndex).isOccupied()) {
                                btnStudentDisableEnable.setText("Disable");
                            } else {
                                btnStudentDisableEnable.setText("Enable");
                            }
                            c1.show(pnlCenter, "Student");
                            resetWindow();
                        } else {
                            //If swapping seat button is clicked.
                            int toBeSwapped = selectedStudentIndex;
                            selectedStudentIndex = buttonList.indexOf(button);
                            swapStudents(toBeSwapped, selectedStudentIndex);
                        }
                    }
                });
            }
        }
    }

    /**
     * Takes two indexes of the student and swaps the name, occupied status, and
     * group number.
     *
     * @param studentIndex1 the first index of the student
     * @param studentIndex2 the second index of the student
     */
    public void swapStudents(int studentIndex1, int studentIndex2) {
        String tempName = buttonList.get(studentIndex1).getText();
        buttonList.get(studentIndex1).setText(buttonList.get(studentIndex2).getText());
        buttonList.get(studentIndex2).setText(tempName);
        list.swapSeats(studentIndex1, studentIndex2);
        swapping = false;
        ArrayList<Integer> disabledSeats = list.getDisabledSeatsArray();
        if (list.seatList.get(studentIndex2).isOccupied()) {
            buttonList.get(studentIndex2).setBackground(colorDefaultBack);
            buttonList.get(studentIndex2).setForeground(colorDefaultFore);
        }
        for (Integer i : disabledSeats) {
            buttonList.get(i).setBackground(colorDisabledBack);
            buttonList.get(i).setForeground(colorDisabledFore);
        }

        showClassLayoutWithColors();
    }

    /**
     * Displays an error message.
     *
     * @param message the string containing the error message
     */
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(rootPane,
                message,
                "Inane error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays the groups and the students in each group.
     *
     */
    public void showGroups() {
        groupPanels = new ArrayList<>();
        String[] namesInGroups = list.printGroups();
        for (int i = 0; i < namesInGroups.length; i++) {
            JPanel pnlGroupNumber = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel groupMembers = new JLabel(namesInGroups[i]);
            pnlGroupNumber.add(groupMembers);
            pnlGroupsLayout.add(pnlGroupNumber);
            groupPanels.add(pnlGroupNumber);
        }
        tbtnGroups.doClick();
    }

    /**
     * Resets the groups layout under the groups tab.
     *
     */
    public void resetGroupsLayout() {
        if (groupPanels.size() > 0) {
            list.resetGroupNumbers();
            for (JPanel p : groupPanels) {
                pnlGroupsLayout.remove(p);
            }
        }
    }

    /**
     * This method shows or hides the student panel.
     *
     * @param show boolean if true, shows the student panel, if false, hides the
     * panel.
     */
    public void showStudentPanel(boolean show) {
        if (show) {
            pnlCenter.add(pnlStudent, "Student");
        } else {
            pnlCenter.remove(pnlStudent);
        }
    }

    /**
     * This method shows the class panel.
     *
     */
    public void showClassLayout() {
        c1.show(pnlCenter, "Class");
        tbtnClass.doClick();
    }

    /**
     * Indicating swapping seat function is active. Highlights the seat to be
     * swapped while change the others to default colors if occupied.
     *
     * @param index the selected seat
     */
    public void showSwapSeatColor(int index) {
        for (int i = 0; i < buttonList.size(); i++) {
            if (list.seatList.get(i).isOccupied() == true) {
                buttonList.get(i).setBackground(colorDefaultBack);
                buttonList.get(i).setForeground(colorDefaultFore);
            }
        }
        buttonList.get(index).setBackground(Color.cyan);
        buttonList.get(index).setForeground(Color.BLUE);
    }

    /**
     * This method shows the class layout with group colors. group members will
     * share the same color, only shows if max groups is less than 21.
     */
    public void showClassLayoutWithColors() {
        int groupCount = list.countGroups();
        if (groupCount != 1) {
            if (groupCount < 22) {
                colorList = new Color[]{
                    Color.CYAN, Color.GREEN, Color.ORANGE, Color.PINK, Color.MAGENTA,
                    Color.YELLOW, new Color(50, 205, 50), new Color(64, 224, 208),
                    new Color(30, 144, 255), new Color(210, 105, 30),
                    new Color(186, 85, 211), new Color(255, 127, 80), new Color(188, 143, 143),
                    new Color(255, 250, 205), new Color(189, 183, 107), new Color(119, 136, 153),
                    /*Dark*/ new Color(0, 0, 128), new Color(128, 0, 128),
                    new Color(0, 100, 0), new Color(128, 0, 0), new Color(255, 0, 0), new Color(255, 20, 147)
                };
                for (int i = 0; i < buttonList.size(); i++) {
                    if (list.seatList.get(i).isOccupied()) {
                        if (list.seatList.get(i).getGroupNumber() != 0) {
                            int groupNumber = list.seatList.get(i).getGroupNumber();
                            buttonList.get(i).setBackground(colorList[groupNumber - 1]);
                            if (groupNumber > 16) {
                                buttonList.get(i).setForeground(Color.WHITE);
                            } else {
                                buttonList.get(i).setForeground(colorDefaultFore);
                            }
                        } else {
                            buttonList.get(i).setBackground(colorDefaultBack);
                            buttonList.get(i).setForeground(colorDefaultFore);
                        }
                    } else {
                        buttonList.get(i).setBackground(colorDisabledBack);
                        buttonList.get(i).setForeground(colorDisabledFore);
                    }
                }
            } else {
                //If there are more groups than the group colors.
                showErrorMessage(errorColor);
            }
        } else {
            for (int i = 0; i < buttonList.size(); i++) {
                if (list.seatList.get(i).isOccupied()) {
                    buttonList.get(i).setBackground(colorDefaultBack);
                    buttonList.get(i).setForeground(colorDefaultFore);
                } else {
                    buttonList.get(i).setBackground(colorDisabledBack);
                    buttonList.get(i).setForeground(colorDisabledFore);
                }
            }
        }
    }
}
