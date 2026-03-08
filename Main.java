import Jaikin.JaikinPanel;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Chaikin's Algorithm Animation");
			JaikinPanel panel = new JaikinPanel();

			frame.add(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);

			// Adding a key listener to the frame for the ESC key
			panel.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						System.exit(0);
					}
				}
			});

			frame.setVisible(true);
		});
	}
}
