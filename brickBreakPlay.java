import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class brickBreakPlay extends JPanel implements KeyListener, ActionListener {
    private boolean running = false;
    private int score = 0;
    private int brickNum = 20;

    private Timer gameTime;
    private int timeDelay = 5;

    private int playerX = 310;
    
    private int ballX = 120;
    private int ballY = 350;
    private int ballXDirection = -1;
    private int ballYDirection = -2;

    private mapCreator map;


    public brickBreakPlay() {
        map = new mapCreator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        gameTime = new Timer(timeDelay, this);
        gameTime.start();
    }
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);
        
        g.setColor(Color.blue);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3); 
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score,590,30);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Press P to Pause/Resume",30,30);

        

        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Color.pink);
        g.fillOval(ballX, ballY, 20, 20);

        if(brickNum <= 0) {
            running = false;
            ballXDirection = 0;
            ballYDirection = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON", 240, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Start Over", 230, 350);
        }


        if(ballY > 570) {
            running = false;
            ballXDirection = 0;
            ballYDirection = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER", 240, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Start Over", 230, 350);

        }

        g.dispose();

        map.draw((Graphics2D)g);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameTime.start();
        if(running) {
            if(new Rectangle(ballX,ballY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
                ballYDirection = -ballYDirection;
            }
            A: for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballX, ballY, 20,20);
                        Rectangle brickRectangle = rect;

                        if(ballRectangle.intersects(brickRectangle)) {
                            map.setBrickValue(0, i, j);
                            brickNum--;
                            score += 5;

                            if(ballX + 19 <= brickRectangle.x || ballX + 1 >= brickRectangle.x + brickRectangle.width) {
                                ballXDirection = -ballXDirection;
                            }
                            else {
                                ballYDirection = -ballYDirection;
                            }

                            break A;
                        }
                    }
                }
            }
            ballX += ballXDirection;
            ballY += ballYDirection;
            if(ballX < 0) {
                ballXDirection = -ballXDirection;
            }
            if(ballY < 0) {
                ballYDirection = -ballYDirection;
            }
            if(ballX > 670) {
                ballXDirection = -ballXDirection;
            }
        }
        repaint();
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            }
            else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX <= 10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!running) {
                Random rand = new Random();
                running = true;
                ballX = 120;
                ballY = 350;
                ballXDirection = -1;
                ballYDirection = -2;
                playerX = 310;
                score = 0;
                brickNum = 21;
                map = new mapCreator(rand.nextInt(5) + 1,rand.nextInt(5) + 3);
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_P) {
            if(running) {
                running = false;
            }
            else if (!running) {
                running = true;
            }
        }
    }

    public void moveRight() {
        running = true;
        playerX += 30;
    }
    public void moveLeft() {
        running = true;
        playerX -= 30;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    
}
