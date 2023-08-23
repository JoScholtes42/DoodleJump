
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import static java.awt.event.KeyEvent.*;

public class DoodleBus {
    private final DoodleDat doodleDat = new DoodleDat();
    private final Dimension PANELSIZE = doodleDat.getPANELSIZE();
    private Point currentPosition= doodleDat.getCurrentPosition();
    private final int MAXVELOCITY= doodleDat.getMAXVELOCITY();
    private int velocity = MAXVELOCITY;
    private final int baseHeight= doodleDat.getBaseHeight();
    private int highestJumpedPlatformHeight=baseHeight;

    private int horizontalVelocity= doodleDat.getHorizontalVelocity();
    private int accelerationCounter = 0;
    private final int MAXACCELERATIONCOUNTER= doodleDat.getMAXACCELERATIONCOUNTER();

    HashSet<Integer> pressedKeys = new HashSet<>();

    private boolean isFalling=false; //true, when moving down
    private boolean isCliming=false; //true, when jumping over the height limit



    public DoodleBus() {
        //TODO mainFrame.updateHeight(baseHeight-p.getPosition().y);
        Runnable lookingForPlatformHits = () -> {
            while (!Thread.interrupted()) {

                while (isFalling) {
                    for (PlatformPre p : PlatformDat.getPlatforms()) {
                        if (currentPosition.y >= p.getPosition().y && currentPosition.y <= p.getPosition().y + PlatformDat.getHEIGHT()) {
                            if (currentPosition.x > p.getPosition().x - PlatformDat.getLENGTH() / 2 &&
                                    currentPosition.x < p.getPosition().x + PlatformDat.getLENGTH() / 2) {
                                velocity = MAXVELOCITY;
                                accelerationCounter = 0;
                                highestJumpedPlatformHeight = p.getPosition().y;
                                isFalling = false;
                                //TODO mainFrame.updateHeight(baseHeight-p.getPosition().y);
                            }

                        }
                    }
                }
            }
        };
        Thread lookingForPlatformHitsThread = new Thread(lookingForPlatformHits);
        Timer moveTimer= new Timer(2, e->{
            if(currentPosition.x<=-1){
                currentPosition.x=600;
            }else if(currentPosition.x>=601){
                currentPosition.x=0;
            }
            currentPosition.x+=horizontalVelocity;
        });
        moveTimer.start();
        lookingForPlatformHitsThread.start();
    }

    public int getMAXVELOCITY() {
        return doodleDat.getMAXVELOCITY();
    }

    public Point getCurrentPosition() {
        return doodleDat.getCurrentPosition();
    }

    public int getBaseHeight() {
        return baseHeight;
    }

    public void heightUpdated(){
        doodleDat.setCurrentPosition(new Point(doodleDat.getCurrentPosition().x,doodleDat.getCurrentPosition().y-2));
        isCliming=false;


    }

    public void gameOver(){
        currentPosition = new Point(PANELSIZE.width/2, baseHeight);
        doodleDat.setCurrentPosition(currentPosition);
        isFalling=false;
        accelerationCounter =0;
        heightUpdated();


    }

    public KeyListener getKeyListener(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}

            @Override
            public void keyPressed(KeyEvent keyEvent) {

                switch (keyEvent.getKeyCode()) {
                    case (VK_LEFT):
                    case (VK_RIGHT):
                        pressedKeys.add(keyEvent.getKeyCode());
                        updateHorizontalVelocity();
                        break;
                    /*case (VK_SPACE): //TODO Restart Game
                        try {
                            mainFrame.starOver();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;*/
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case (VK_LEFT):
                    case (VK_RIGHT):
                        pressedKeys.remove(keyEvent.getKeyCode());
                        updateHorizontalVelocity();
                        break;
                }
            }
        };
    }

    public void jump() {
        System.out.println(currentPosition.y);
        if(!isCliming) {
            currentPosition.y -= velocity;
            accelerationCounter += 1;
            if (accelerationCounter == MAXACCELERATIONCOUNTER) {
                accelerationCounter = 0;
                velocity -= 1;

            }
            if (currentPosition.y <= 629) {

                MainFrame.newHeight(velocity, accelerationCounter, MAXACCELERATIONCOUNTER,highestJumpedPlatformHeight);
                velocity = 0;
                isCliming = true;
                return;
            }
            if (velocity < 0) {
                if (currentPosition.y > PANELSIZE.height + 50) {
                    MainFrame.gameOver();
                }
                isFalling = true;
            }
        }

    }





    private void updateHorizontalVelocity() {
        horizontalVelocity=0;
        if (pressedKeys.contains(VK_LEFT)) {
            horizontalVelocity = -2;
        }
        if (pressedKeys.contains(VK_RIGHT)) {
            horizontalVelocity = 2;
        }
    }
}
