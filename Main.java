import Jaikin.JaikinPanel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Chaikin's Algorithm Animation");
			JaikinPanel panel = new JaikinPanel();

			frame.add(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);

			// Add a WindowListener to intercept the "X" button click
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    panel.cleanup();
                    frame.dispose();
                    System.exit(0);
                }
            });

            // Update your ESC key listener to also run the cleanup
            panel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        panel.cleanup();
                        frame.dispose();
                        System.exit(0);
                    }
                }
            });

			frame.setVisible(true);
		});
	}
}
