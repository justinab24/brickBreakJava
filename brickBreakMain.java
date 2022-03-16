import javax.swing.JFrame;

public class brickBreakMain {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        brickBreakPlay gamePlay = new brickBreakPlay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Brick Breaker Game");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
        obj.setVisible(true);
    }
}