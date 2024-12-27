import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class SimpleDotEscape extends JPanel implements ActionListener, KeyListener {
    private int dotX = 200, dotY = 550, score = 0;
    private int obstacleX = 100, obstacleY = 0;
    private boolean gameOver = false;
    private Timer timer;

    public SimpleDotEscape() {
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, 0, getHeight(), Color.BLACK);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 100, 250);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Final Score: " + score, 140, 300);
            g.drawString("Press 'R' to Restart", 120, 340);
            g.drawString("Press 'E' to Exit", 130, 370);
            return;
        }

        g.setColor(Color.CYAN);
        g.fillOval(dotX, dotY, 20, 20);

        g.setColor(Color.RED);
        g.fillRect(obstacleX, obstacleY, 50, 20);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Score: " + score, 10, 20);
    }

    private void restartGame() {
        dotX = 200;
        dotY = 550;
        obstacleX = 100;
        obstacleY = 0;
        score = 0;
        gameOver = false;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            obstacleY += 5;
            if (obstacleY > 600) {
                obstacleY = 0;
                obstacleX = new Random().nextInt(350);
                score++;
            }

            if (new Rectangle(dotX, dotY, 20, 20).intersects(new Rectangle(obstacleX, obstacleY, 50, 20))) {
                gameOver = true;
                timer.stop();
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && dotX > 0) dotX -= 20;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && dotX < 380) dotX += 20;
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) restartGame();
        if (e.getKeyCode() == KeyEvent.VK_E) System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}

public class SimpleDotEscapeWithStart extends JFrame {
    public SimpleDotEscapeWithStart() {
        JPanel startPanel = new JPanel();
        startPanel.setBackground(Color.BLACK);
        startPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Simple Dot Escape", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.CYAN);
        startPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel instructionsLabel = new JLabel(
                "<html><div style='text-align: center;'>Use LEFT and RIGHT arrow keys to move.<br>Avoid falling obstacles.<br>Press START to begin.</div></html>",
                JLabel.CENTER);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        instructionsLabel.setForeground(Color.WHITE);
        startPanel.add(instructionsLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setBackground(Color.CYAN);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            dispose(); // Close start screen
            JFrame gameFrame = new JFrame();
            gameFrame.setUndecorated(true); // Make the game window undecorated
            SimpleDotEscape game = new SimpleDotEscape();
            gameFrame.add(game);
            gameFrame.pack();
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);
            gameFrame.setResizable(false);
            gameFrame.setLocationRelativeTo(null);
        });

        startPanel.add(startButton, BorderLayout.SOUTH);

        setUndecorated(true); 
        add(startPanel);
        setTitle("Simple Dot Escape - Start Screen");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SimpleDotEscapeWithStart();
    }
}
