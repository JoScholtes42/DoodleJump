/*
 * @author Jo Scholtes
 * */
import javax.swing.*;
import java.awt.*;

public class MainFrame {

    private final Doodle doodle;
    private final Dimension SCREENSIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private final DrawPanel drawPanel = new DrawPanel();
    private final Timer platformTimer;
    Thread platformThread ;
    private int highestPlatform=900;
    private int score=0;
    private final Color MYYELLOW =new Color(255, 214, 0);  // Vibrant yellow color
    private final Font MYFONT = new Font("Press Start 2P", Font.PLAIN, 36);  // Retro arcade font
    private int remainingHeightDifference=0;
    private int lastJumpHeight=0;
    private Platform bufferedPlatform;
    private final Timer jumpTimer;
    private int requestedHeight=0;
    private boolean enoughSpace = true;
    private JLabel scoreLabel;
    JFrame frame;
    JLabel gameOverLabel = new JLabel("Game Over");


    public MainFrame() {
        frame = new JFrame("Doodle Jump (Pac-Man-Edition)");
        doodle=new Doodle();
        Messager.setMainFrame(this);
        doodle.takeMainFrame();
        drawPanel.takeDoodle();
        platformThread = new Thread(createPlatforms);
        jumpTimer = new Timer(2,e ->{
            try {
                doodle.jump();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            drawPanel.repaint();

        } );
        platformTimer = new Timer(2, e ->{
            if(remainingHeightDifference>=doodle.getMAXVELOCITY()){
                remainingHeightDifference-=doodle.getMAXVELOCITY();
                score+= doodle.getMAXVELOCITY();
                movePlatforms(doodle.getMAXVELOCITY());
                if(lastJumpHeight-remainingHeightDifference>=requestedHeight+50 ){
                    Platform.addPlattform(bufferedPlatform);
                    enoughSpace=true;
                    requestedHeight=0;
                    lastJumpHeight=0;
                }

            }else if(remainingHeightDifference>=0){
                score+=remainingHeightDifference;
                scoreLabel.setText(score+"");
                frame.repaint();

                movePlatforms(remainingHeightDifference);
                remainingHeightDifference=-1;
                doodle.heightUpdated();

            }else{
                stopPlatformTimer();
            }
        });
        Platform firstPlatform = new Platform(new Point(300,doodle.getBaseHeight()));
        Platform.addPlattform(firstPlatform);
        jumpTimer.start();
        platformThread.setDaemon(true);
        platformThread.start();
    }

    private void movePlatforms(int heightDifference){
        for(Platform p:Platform.getPlatforms()){
            p.setYPosition(heightDifference);
        }
    }

    Runnable createPlatforms = () -> {
        while(!Thread.interrupted()) {
            Platform p;
            while (enoughSpace) {
                p = getNewPlatform();
                if (p == null) {
                    enoughSpace = false;
                } else {
                    Platform.addPlattform(p);
                }
            }
        }
    };

    private void stopPlatformTimer(){
        platformTimer.stop();
    }


    public void updateHeight(int heightdifference){
        lastJumpHeight=heightdifference;
        remainingHeightDifference=heightdifference;
        platformTimer.start();
        drawPanel.repaint();
    }

    public void gameOver(){
        jumpTimer.stop();
        stopPlatformTimer();
        gameOverLabel.setVisible(true);



    }

    public void starOver() throws InterruptedException {
        gameOverLabel.setVisible(false);
        Platform.deletePlatforms();
        scoreLabel.setText("0");
        Platform firstPlatform = new Platform(new Point(300,doodle.getBaseHeight()));
        Platform.addPlattform(firstPlatform);
        highestPlatform=firstPlatform.getPosition().y;
        Thread.sleep(100);
        jumpTimer.start();
        enoughSpace=true;
        platformTimer.start();
        drawPanel.setGameOver(false);
    }

    public void open(){

        JLabel title = createArcadeGameLabels("Doodle Jump: Pac-Man-Edition");
        scoreLabel = new JLabel(Integer.toString(score));
        scoreLabel.setForeground(MYYELLOW);
        scoreLabel.setFont(MYFONT);
        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        drawPanel.add(title);
        drawPanel.add(scoreLabel);
        drawPanel.repaint();
        drawPanel.addKeyListener(doodle);
        drawPanel.setFocusable(true);
        gameOverLabel.setLocation(0,SCREENSIZE.height);
        drawPanel.add(gameOverLabel);

        frame.add(drawPanel);
        frame.setBackground(new Color(0,0,139));
        frame.setResizable(false);
        frame.setLocation(SCREENSIZE.width/2-300,0);
        frame.setSize(600,SCREENSIZE.height);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public JLabel createArcadeGameLabels(String Title) {
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setForeground(new Color(255, 0, 0));  // Vibrant yellow color

        gameOverLabel.setFont(MYFONT);

        gameOverLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        gameOverLabel.setVisible(false);

        JLabel label = new JLabel(Title);
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
                ((int)(Math.random()*(SCREENSIZE.height/2-SCREENSIZE.height/6-100))+20);
        return new Point(x,y);
    }

    public Platform getNewPlatform(){
        Point position = getRandomPosition();
        if(position.y>Platform.getHEIGHT()+SCREENSIZE.height/8){
            highestPlatform= position.y;
           return new Platform(position);
        }
        else{
            requestedHeight= position.y;
            bufferedPlatform=new Platform(position);
            return null;
        }
    }
}