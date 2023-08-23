import java.awt.*;
import java.awt.event.KeyListener;

public class DoodlePre {
    private final DoodleBus doodleBus= new DoodleBus();

    public void heightUpdated(){doodleBus.heightUpdated();}
    public void gameOver(){doodleBus.gameOver();}

    public int getMAXVELOCITY(){return doodleBus.getMAXVELOCITY();}

    public int getBaseHeight(){return doodleBus.getBaseHeight();}

    public KeyListener getKeyListener(){return doodleBus.getKeyListener();}

    public void jump() throws InterruptedException {doodleBus.jump();}

    public void draw(Graphics g){
        Point currentPosition = doodleBus.getCurrentPosition();
        g.setColor(Color.yellow);
        int SIZE = 25;
        g.fillOval(currentPosition.x-(SIZE /2), currentPosition.y- SIZE, SIZE, SIZE);
        g.setColor(Color.black);
        g.drawOval(currentPosition.x-(SIZE /2), currentPosition.y- SIZE, SIZE, SIZE);
    }
}
