import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private final DoodlePre doodlePre;
    private static MainFrame mainFrame;

    private final Dimension FRAMESIZE =new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/3,Toolkit.getDefaultToolkit().getScreenSize().height);
    private JLabel scoreLabel;
    private final JFrame frame;
    private JLabel gameOverLabel;
    private JLabel title;

    private final Timer platformTimer;
    private Thread platformThread ;

    private int score=0;
    private final Color MYYELLOW =new Color(255, 214, 0);  // Vibrant yellow color
    private final Font MYFONT = new Font("Press Start 2P", Font.PLAIN, 36);  // Retro arcade font
    private int remainingHeightDifference=0;
    private int lastJumpHeight=0;
    private PlatformPre bufferedPlatform;
    private final Timer jumpTimer;
    private int requestedHeight=0;
    private boolean enoughSpace = true;
    private DrawPanel drawPanel= new DrawPanel();
    private PlatformPre highestPlatform;



    public static void main(String[] args){
        mainFrame= new MainFrame();
        mainFrame.open();

    }
    public static void gameOver(){
        mainFrame.doodlePre.gameOver();
        //jumpTimer.stop();
        //stopPlatformTimer();
        //gameOverLabel.setVisible(true);
    }

    public static void newHeight(int velocity, int accelerationCounter, int MAXACCELARATIONCOUNTER, int lastjumpedPlatformHeight){
        mainFrame.updateHeight(velocity,accelerationCounter,MAXACCELARATIONCOUNTER,lastjumpedPlatformHeight);
    }

    public MainFrame() {
        doodlePre =new DoodlePre();
        drawPanel.setDoodle(doodlePre);
        drawPanel.setScreensize(Toolkit.getDefaultToolkit().getScreenSize());

        frame = new JFrame("Doodle Jump (Pac-Man-Edition)");

        platformThread = new Thread(createPlatforms);
        jumpTimer = new Timer(2,e ->{
            try {
                doodlePre.jump();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            drawPanel.repaint();

        } );
        platformTimer = new Timer(2, e ->{
            if(remainingHeightDifference>= doodlePre.getMAXVELOCITY()){
                remainingHeightDifference-= doodlePre.getMAXVELOCITY();
                score+= doodlePre.getMAXVELOCITY();
                movePlatforms(doodlePre.getMAXVELOCITY());
                if(lastJumpHeight-remainingHeightDifference>=requestedHeight ){
                    PlatformDat.addPlattform(bufferedPlatform);
                    requestedHeight=0;
                    lastJumpHeight=0;
                    bufferedPlatform=null;
                    enoughSpace=true;
                }

            }else if(remainingHeightDifference>=0){
                score+=remainingHeightDifference;
                scoreLabel.setText(score+"");
                frame.repaint();

                movePlatforms(remainingHeightDifference);
                remainingHeightDifference=-1;
                //TODO doodlePre.heightUpdated();

            }else{
                stopPlatformTimer();
            }
        });

        PlatformPre firstPlatform = new PlatformPre(new Point(300, doodlePre.getBaseHeight())) ;
        highestPlatform=firstPlatform;
        PlatformDat.addPlattform(firstPlatform);
        jumpTimer.start();
        platformThread.setDaemon(true);
        platformThread.start();
    }




    Runnable createPlatforms = () -> {
        PlatformPre p;
        while(!Thread.interrupted()) {
            while(enoughSpace) {
                p = PlatformDat.getNewPlatform(highestPlatform.getPosition().y, FRAMESIZE);
                if (p.getPosition().y <= FRAMESIZE.height / 8 + 50) {
                    requestedHeight = FRAMESIZE.height / 8 + 50 - p.getPosition().y;
                    bufferedPlatform = p;
                    enoughSpace=false;
                } else {
                    PlatformDat.addPlattform(p);
                    highestPlatform = p;
                }
            }
        }
    };

    private void stopPlatformTimer(){
        platformTimer.stop();
    }


    private void updateHeight(int velocity, int accelerationCounter, int MAXACCELARATIONCOUNTER, int lastjumpedPlatformHeight){

        lastJumpHeight=doodlePre.getBaseHeight()-lastjumpedPlatformHeight;
        while(velocity!=0){
            movePlatforms(velocity);
            if(accelerationCounter==MAXACCELARATIONCOUNTER){
                accelerationCounter=0;
                velocity-=1;
            }
            else {
                accelerationCounter+=1;
            }
        }
        drawPanel.repaint();
    }

    private void movePlatforms(int heightDifference){

        ApplyFunction move =(p)->p.move(heightDifference);
        boolean isFinished =PlatformDat.applyMethod(move);
        if(isFinished){
            doodlePre.heightUpdated();
        }
    }


    public void starOver() throws InterruptedException {
        gameOverLabel.setVisible(false);
        PlatformDat.clearPlatforms();
        scoreLabel.setText("0");
        PlatformPre firstPlatform = new PlatformPre(new Point(300, doodlePre.getBaseHeight()));
        PlatformDat.addPlattform(firstPlatform);
        highestPlatform=firstPlatform;
        Thread.sleep(100);
        jumpTimer.start();
        enoughSpace=true;
        platformTimer.start();
        drawPanel.setGameOver(false);
    }

    public void open() {
        createArcadeGameLabels();


        drawPanel.add(title);
        drawPanel.add(scoreLabel);
        drawPanel.add(gameOverLabel);
        drawPanel.repaint();

        drawPanel.addKeyListener(doodlePre.getKeyListener());
        drawPanel.setFocusable(true);
        /*drawPanel.setSize(FRAMESIZE.width,FRAMESIZE.height/8*7);
        drawPanel.setLocation(0,700);
        drawPanel.repaint();*/

        frame.add(drawPanel);

        frame.setBackground(new Color(70, 0, 70));
        frame.setResizable(false);
        frame.setLocation(FRAMESIZE.width, 0);
        frame.setSize(FRAMESIZE.width, FRAMESIZE.height);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
    private void createArcadeGameLabels() {
        title = new JLabel("Doodle Jump: Pac-Man-Edition");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(MYYELLOW);
        title.setFont(MYFONT);
        title.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        title.setLocation(FRAMESIZE.width/6,0);


        scoreLabel = new JLabel(Integer.toString(score));
        scoreLabel.setForeground(MYYELLOW);
        scoreLabel.setFont(MYFONT);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //  scoreLabel.setVerticalAlignment(SwingConstants.CENTER);

        gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverLabel.setVerticalAlignment(SwingConstants.CENTER);
        gameOverLabel.setForeground(new Color(255, 0, 0));  // Vibrant yellow color
        gameOverLabel.setFont(MYFONT);
        gameOverLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 3),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        gameOverLabel.setVisible(false);
    }









}
