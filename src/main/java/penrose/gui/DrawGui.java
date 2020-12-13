package penrose.gui;

import javax.swing.*;
import java.awt.*;

public class DrawGui {
    public static void main(String[] args) {
        PenroseFrame frame = new PenroseFrame();
        frame.setTitle("Penrose Animation");
        frame.setBackground(new Color(39, 174, 96));
        frame.pack();

        // put the frame in the middle of the display
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);
    }
}
