import java.awt.*;


public class DrawPanel extends javax.swing.JPanel {

    Doodle doodle;
    private final Dimension SCREENSIZE=Toolkit.getDefaultToolkit().getScreenSize();

    public void setDoodle(Doodle doodle) {
        this.doodle = doodle;
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,SCREENSIZE.height/12,SCREENSIZE.width/3,SCREENSIZE.height/12*11);
        doodle.draw(g);
        for(Platform p : Platform.getPlatforms()){
            p.draw(g);
        }
    }

    }
