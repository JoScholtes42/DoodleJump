/*
* @author Jo Scholtes
* */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import static java.awt.event.KeyEvent.*;

public class Doodle implements KeyListener {

    private final int WIDTH=600;
    private final int HEIGHT=Toolkit.getDefaultToolkit().getScreenSize().height;
    private int baseHeight = HEIGHT/6*5;
    private Point currentPosition = new Point(WIDTH/2, baseHeight);
    private int horizontalVelocity=0;
    private final int MAXVELOCITY=3;
    private int velocity = MAXVELOCITY;
    private int counter = 0;
    private int acceleration = -1;
    HashSet <Integer> pressedKeys = new HashSet<>();
    private boolean isFalling=false;
    private MainFrame mainFrame;
    private boolean isCliming=false;//when jumping over the height limit

    public Doodle() {
        Thread lookingForPlatformHitsThread = new Thread(lookingForPlatformHits);
        Timer moveTimer= new Timer(2,e->{
            if(currentPosition.x<=-1){
                currentPosition.x=600;
            }else if(currentPosition.x>=601){
                currentPosition.x=0;
            }
            currentPosition.x+=horizontalVelocity;
        });
        moveTimer.start();
        lookingForPlatformHitsThread.start();
        Messager.setDoodle(this);


    }
    public void takeMainFrame(){
        mainFrame=Messager.getMainFrame();
    }


    public int getMAXVELOCITY() {
        return MAXVELOCITY;
    }

    public void gameOver(){
        currentPosition = new Point(WIDTH/2, baseHeight);
        isFalling=false;
        counter=0;
        heightUpdated();


    }

    Runnable lookingForPlatformHits =() -> {
        while(!Thread.interrupted()) {
            while (isFalling) {
                for (Platform p : Platform.getPlatforms()) {
                    if (currentPosition.y >= p.getPosition().y&& currentPosition.y<=p.getPosition().y+Platform.getHEIGHT()) {
                        if (currentPosition.x > p.getPosition().x - Platform.getLENGTH() / 2 &&
                                currentPosition.x < p.getPosition().x + Platform.getLENGTH() / 2) {
                            velocity = MAXVELOCITY;
                            counter = 0;
                            isFalling=false;
                            mainFrame.updateHeight(baseHeight-p.getPosition().y);
                            baseHeight=p.getPosition().y;
                        }

                    }
                }
            }
        }
    };

    public int getBaseHeight() {
        return baseHeight;
    }


    public void jump() throws InterruptedException {

        if(baseHeight==900){
            isCliming=false;
        }
        if(!isCliming) {
            currentPosition.y -= velocity;
            counter += 1;
            if (counter == 60) {
                counter = 0;
                velocity += acceleration;

            }
            if (currentPosition.y <= HEIGHT / 2 - 10) {
                velocity = 0;
                isCliming = true;
                currentPosition.y+=5;
                return;
            }
            if (velocity < 0) {
                if (currentPosition.y > HEIGHT + 50) {
                    Messager.gameOver();
                    return;
                }
                isFalling = true;
            }
        }
    }

    public void heightUpdated(){

        currentPosition.y-=5;
        baseHeight=900;
       isCliming=false;

    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        int SIZE = 25;
        g.fillOval(currentPosition.x-(SIZE /2), currentPosition.y- SIZE, SIZE, SIZE);
        g.setColor(Color.black);
        g.drawOval(currentPosition.x-(SIZE /2), currentPosition.y- SIZE, SIZE, SIZE);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()){
            case(VK_LEFT):
            case(VK_RIGHT):
                pressedKeys.add(keyEvent.getKeyCode());
                updateHorizontalVelocity();
                break;
            case(VK_SPACE):
                try {
                    mainFrame.starOver();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case(VK_LEFT):
            case(VK_RIGHT):
                pressedKeys.remove(keyEvent.getKeyCode());
                updateHorizontalVelocity();
                break;
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