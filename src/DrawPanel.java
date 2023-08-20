/*
 * @author Jo Scholtes
 * */
import java.awt.*;

public class DrawPanel extends javax.swing.JPanel {

    private Doodle doodle;
    private final Dimension SCREENSIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private boolean gameOver=false;

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void takeDoodle() {
        doodle= Messager.getDoodle();
    }

    public DrawPanel() {
        Messager.setDrawPanel(this);
    }



    public void paintComponent(Graphics g){
        if(!gameOver) {
            for (Component c : this.getComponents()) {
                c.repaint();
            }
            g.setColor(Color.BLACK);
            g.fillRect(0, SCREENSIZE.height / 8, SCREENSIZE.width / 3, SCREENSIZE.height / 8 * 7);
            doodle.draw(g);
            for (Platform p : Platform.getPlatforms()) {
                p.draw(g);
            }
        }
    }
}