package Jaikin;

import java.awt.Color;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
    private JFrame frame;
    public Window() {
        this.frame = new JFrame("JAINKIN");
    }

    public void intialze() {
        frame.setSize(800, 800);
        frame.setLocation(500, 100);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }

}
