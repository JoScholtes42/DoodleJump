import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame {

    private final Doodle doodle;
    private final Dimension SCREENSIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private final DrawPanel drawPanel = new DrawPanel();
    private final java.util.Timer jumpTimer = new Timer(true);


    public MainFrame() {
        doodle= new Doodle();
        drawPanel.setDoodle(doodle);
    }


    public void open(){
        JFrame frame = new JFrame();
        drawPanel.repaint();
        frame.add(drawPanel);
        frame.setBackground(new Color(0,0,139));

        System.out.println(SCREENSIZE);

        frame.setResizable(false);
        frame.setLocation(SCREENSIZE.width/3,0);
        frame.setSize(SCREENSIZE.width/3,SCREENSIZE.height);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void start(){
        jumpTimer.schedule(jump,500,2);


    }
    public Point getRandomPosition(){
        int x = (int)(Math.random()*(SCREENSIZE.width/3-Platform.getLENGTH())+Platform.getLENGTH()/2);
        int y= Platform.getHighestPlatformHeight()-
                ((int)(Math.random()*(SCREENSIZE.height/2-SCREENSIZE.height/6*5-50)+Platform.getHEIGHT()/2));
        return new Point(x,y);
    }


    public Platform getNewPlatform(){
        Point position = getRandomPosition();
        if(position.y>SCREENSIZE.height/6*5+Platform.getHEIGHT()){
           return new Platform(position);
        }
        else{
            return null;
        }
    }



    private final TimerTask jump = new TimerTask() {
        @Override
        public void run() {
            doodle.jump();
            drawPanel.repaint();
        }
    };
}
