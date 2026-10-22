package pebble;

import javax.swing.SwingUtilities;

public class Main {

    /** boots up the swing window on the edt */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PebbleGUI gui = new PebbleGUI();
            gui.setVisible(true);
        });
    }
}
