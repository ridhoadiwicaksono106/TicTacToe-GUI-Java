
import javax.swing.SwingUtilities;


public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new TicTacToeGUI();
        }
    });
}
