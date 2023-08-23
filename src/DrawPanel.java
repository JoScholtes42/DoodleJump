import java.awt.*;

public class DrawPanel extends javax.swing.JPanel {

    private DoodlePre doodle;
    private boolean gameOver=false;
    private Dimension screensize;

    public void setScreensize(Dimension screensize) {
        this.screensize = screensize;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setDoodle(DoodlePre doodle) {
        this.doodle = doodle;
    }

    public void paintComponent(Graphics g){
        if(!gameOver) {
            for (Component c : this.getComponents()) {
                c.repaint();
            }
            g.setColor(Color.BLACK);
            g.fillRect(0, screensize.height/8, screensize.width/3, screensize.height/8*7);
            doodle.draw(g);
            ApplyFunction draw =(p)->p.draw(g);
            PlatformDat.applyMethod(draw);

        }
    }
}
