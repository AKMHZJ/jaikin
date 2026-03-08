package Jaikin;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class JaikinPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private ArrayList<Point2D> points = new ArrayList<>();
    private ArrayList<Point2D> smoothedPoints = new ArrayList<>();
    private Point2D draggedPoint = null;
    private boolean animating = false;
    private int step = 0;
    private long timer = 0;
    private static final int MAX_STEPS = 7;
    private static final int ANIMATION_DELAY_MS = 500;

    public JaikinPanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        timer = System.currentTimeMillis();

        Timer animationTimer = new Timer(16, e -> {
            if (animating) {
                if (System.currentTimeMillis() > timer + ANIMATION_DELAY_MS) {
                    step++;
                    if (step > MAX_STEPS) {
                        step = 0;
                    }
                    updateSmoothedPoints();
                    timer = System.currentTimeMillis();
                }
            }
            repaint();
        });
        animationTimer.start();
    }

    private void updateSmoothedPoints() {
        if (points.size() < 3) {
            smoothedPoints = new ArrayList<>(points);
        } else {
            smoothedPoints = Algorithm.smooth(points, step);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw smoothed curve or line
        if (points.size() == 2) {
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(2));
            Point2D p1 = points.get(0);
            Point2D p2 = points.get(1);
            g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        } else if (points.size() >= 3) {
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(2));
            for (int i = 0; i < smoothedPoints.size() - 1; i++) {
                Point2D p1 = smoothedPoints.get(i);
                Point2D p2 = smoothedPoints.get(i + 1);
                g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
            }
        }

        // Draw original points as small circles
        g2d.setColor(Color.RED);
        for (Point2D point : points) {
            g2d.drawOval((int) (point.x - 5), (int) (point.y - 5), 10, 10);
        }

        // GUI hints
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        String hint = "Click to add | Drag points to move | Enter to start animation | D to clear | ESC to exit";
        if (points.isEmpty()) {
            hint = "Please draw points first | ESC to exit";
        }
        g2d.drawString(hint, 10, getHeight() - 10);

        if (animating && points.size() >= 3) {
            g2d.drawString("Step: " + step, 10, 20);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            // Check if we are clicking on an existing point to drag it
            for (Point2D p : points) {
                if (Math.hypot(p.x - e.getX(), p.y - e.getY()) <= 10) {
                    draggedPoint = p;
                    return;
                }
            }

            points.add(new Point2D(e.getX(), e.getY()));
            updateSmoothedPoints();
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggedPoint != null) {
            draggedPoint.x = e.getX();
            draggedPoint.y = e.getY();
            updateSmoothedPoints();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            draggedPoint = null;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (points.size() >= 3) {
                animating = true;
                step = 0;
                timer = System.currentTimeMillis();
                updateSmoothedPoints();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            animating = false;
            step = 0;
            points.clear();
            smoothedPoints.clear();
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
