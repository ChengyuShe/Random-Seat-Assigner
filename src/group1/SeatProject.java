package group1;

/**
 * This is the main class that launches the GUI.
 *
 * @author Chengyu She
 * @author Xu Shi
 * @author Vanessa Ross
 * @author Cindy Yang
 * @version 22.12/10/2019
 */
public class SeatProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SeatView view = new SeatView("Group Generater");
        view.setDefaultCloseOperation(SeatView.EXIT_ON_CLOSE);
        //Minimize size.
        view.pack();
        view.setVisible(true);
        //Move GUI to center.
        view.setLocationRelativeTo(null);
    }
}
