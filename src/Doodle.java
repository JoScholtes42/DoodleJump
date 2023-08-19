import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class Doodle implements KeyListener {

    private final int WIDTH=600;
    private final int HEIGHT=Toolkit.getDefaultToolkit().getScreenSize().height;
    private int baseHeight = HEIGHT/6*5;
    private final Point currentPosition = new Point(WIDTH/2, baseHeight);
    private int horizontalVelocity=0;
    private final int MAXVELOCITY=3;
    private int velocity = MAXVELOCITY;
    private int counter = 0;
    private int acceleration = -1;
    HashSet <Integer> pressedKeys = new HashSet<>();

    public Doodle() {
        Timer moveTimer= new Timer(1,e->{
            switch(currentPosition.x){
                case(-1):
                    currentPosition.x=600;
                    break;
                case(601):
                    currentPosition.x=0;
                    break;
            }
            currentPosition.x+=horizontalVelocity;
        });
        moveTimer.start();
    }

    public int getBaseHeight() {
        return baseHeight;
    }

    public void setBaseHeight(int baseHeight) {
        this.baseHeight = baseHeight;
    }

    public void jump(){
        currentPosition.y -= velocity;
        counter+=1;
        if (counter == 60) {
            if (velocity > MAXVELOCITY)
                acceleration = 0;
            counter = 0;
            velocity += acceleration;
        }
        if(currentPosition.y>= baseHeight){
            hitsPlatform();
        }
    }

    public void hitsPlatform(){
        acceleration=-1;
        counter=0;
        velocity=MAXVELOCITY;
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
            horizontalVelocity = -1;
        }
        if (pressedKeys.contains(VK_RIGHT)) {
            horizontalVelocity = 1;
        }
    }
}