import javax.swing.*;
import java.awt.*;

public class MainFrame {

    private final Doodle doodle;
    private final Dimension SCREENSIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private final DrawPanel drawPanel = new DrawPanel();
    Thread platformThread ;
    private int highestPlatform=900;
    private int score=0;
    private final Color MYYELLOW =new Color(255, 214, 0);  // Vibrant yellow color
    private final Font MYFONT = new Font("Press Start 2P", Font.PLAIN, 36);  // Retro arcade font

    public MainFrame() {

        doodle= new Doodle();
        drawPanel.setDoodle(doodle);
        platformThread = new Thread(createPlatforms);
        Timer jumpTimer = new Timer(2,e ->{
            doodle.jump();
            drawPanel.repaint();
        } );
        Platform firstPlatform = new Platform(new Point(300,doodle.getBaseHeight()));
        Platform.addPlattform(firstPlatform);
        jumpTimer.start();
        platformThread.setDaemon(true);
        platformThread.start();
    }

    Runnable createPlatforms = () -> {
        boolean enoughSpace=true;
        Platform p;
        while(enoughSpace){
            p=getNewPlatform();
            if(p==null){
                enoughSpace=false;
            }else{
                Platform.addPlattform(p);
            }
        }
    };

    private void updateHeight(int heightdifference){
        score+=heightdifference;
        drawPanel.repaint();
    }

    private void gameOver(){
        platformThread.interrupt();
    }

    public void open(){
        JFrame frame = new JFrame("Doodle Jump (Pac-Man-Edition)");
        JLabel title = createArcadeGameTitleLabel("Doodle Jump: Pac-Man-Edition");
        JLabel scoreLabel = new JLabel(Integer.toString(score));
        scoreLabel.setForeground(MYYELLOW);
        scoreLabel.setFont(MYFONT);
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        drawPanel.add(title);
        drawPanel.add(scoreLabel);
        drawPanel.repaint();
        drawPanel.addKeyListener(doodle);
        drawPanel.setFocusable(true);

        frame.add(drawPanel);
        frame.setBackground(new Color(0,0,139));
        frame.setResizable(false);
        frame.setLocation(SCREENSIZE.width/2-300,0);
        frame.setSize(600,SCREENSIZE.height);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public JLabel createArcadeGameTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(MYYELLOW);
        label.setFont(MYFONT);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        label.setLocation(0,0);
        return label;
    }

    public Point getRandomPosition(){
        int x = (int)(Math.random()*(600-Platform.getLENGTH())+Platform.getLENGTH()/2);
        int y= highestPlatform-
                ((int)(Math.random()*(SCREENSIZE.height/2-SCREENSIZE.height/6-20))+20);
        highestPlatform=y;

        return new Point(x,y);
    }

    public Platform getNewPlatform(){
        Point position = getRandomPosition();
        if(position.y>Platform.getHEIGHT()+SCREENSIZE.height/8){
           return new Platform(position);
        }
        else{
            return null;
        }
    }
}