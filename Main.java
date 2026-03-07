import Jaikin.Jaikin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JPanel implements MouseListener, KeyListener {
	private ArrayList<Jaikin.Point2D> points = new ArrayList<>();
	private ArrayList<Jaikin.Point2D> tmpPoints = new ArrayList<>();
	private boolean started = false;
	private int step = 0;
	private long timer = 0;

	public Main() {
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.BLACK);
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		timer = System.currentTimeMillis();

		Timer animationTimer = new Timer(16, e -> {
			if (started) {
				if (step < 7 && System.currentTimeMillis() > timer + 500) {
					tmpPoints = Jaikin.smooth(points, step);
					step++;
					timer = System.currentTimeMillis();
				}

				if (step >= 7) {
					tmpPoints = new ArrayList<>(points);
					timer = System.currentTimeMillis();
					step = 0;
				}
			}
			repaint();
		});
		animationTimer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.RED);

		for (Jaikin.Point2D point : points) {
			g2d.drawOval((int) (point.x - 5), (int) (point.y - 5), 10, 10);
		}
		if (started && tmpPoints.size() > 1) {
			g2d.setColor(Color.YELLOW);
			g2d.setStroke(new BasicStroke(2));
			for (int i = 0; i < tmpPoints.size() - 1; i++) {
				Jaikin.Point2D p1 = tmpPoints.get(i);
				Jaikin.Point2D p2 = tmpPoints.get(i + 1);
				g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
			}
		}
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 14));
		g2d.drawString("Click to add points | Enter to start | D to clear | ESC to exit", 10, getHeight() - 10);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!started && e.getButton() == MouseEvent.BUTTON1) {
			points.add(new Jaikin.Point2D(e.getX(), e.getY()));
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && points.size() > 1) {
			started = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			started = false;
			step = 0;
			points.clear();
			tmpPoints.clear();
			repaint();
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Chaikin Curve Smoothing");
			Main panel = new Main();
			frame.add(panel);
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
