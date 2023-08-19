import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame {

    private final Doodle doodle;
    private final Dimension SCREENSIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private final DrawPanel drawPanel = new DrawPanel();


    public MainFrame() {
        doodle= new Doodle();
        drawPanel.setDoodle(doodle);
        TimerTask jump = new TimerTask() {
            @Override
            public void run() {
                doodle.jump();
                drawPanel.repaint();
            }
        };
        Timer jumpTimer = new Timer(true);
        jumpTimer.schedule(jump,500,2);
    }


    public void open(){
        JFrame frame = new JFrame();
        drawPanel.repaint();
        drawPanel.addKeyListener(doodle);
        drawPanel.setFocusable(true);

        frame.add(drawPanel);
        frame.setBackground(new Color(0,0,139));

        System.out.println(SCREENSIZE);

        frame.setResizable(false);
        frame.setLocation(SCREENSIZE.width/2-300,0);
        frame.setSize(600,SCREENSIZE.height);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }



    public Point getRandomPosition(){
        int x = (int)(Math.random()*(600-Platform.getLENGTH())+Platform.getLENGTH()/2);
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


}
