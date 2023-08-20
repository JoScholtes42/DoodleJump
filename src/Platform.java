/*
 * @author Jo Scholtes
 * */
import java.awt.*;
import java.util.ArrayList;

public class Platform {

    private static final ArrayList<Platform> platforms = new ArrayList<>();
    private static final int LENGTH =80;
    private static final int HEIGHT =15;
    private Point position;

    public static int getLENGTH() {
        return LENGTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Point getPosition() {
        return position;
    }

    public static void deletePlatforms(){
        platforms.clear();
    }

    public void setYPosition(int yDifference){
        position.y+=yDifference;
    }

    public Platform(Point position) {
        this.position = position;
    }

    public synchronized static ArrayList<Platform> getPlatforms() {
        return platforms;
    }


    public synchronized static void addPlattform(Platform p){
        platforms.add(p);
    }

    public synchronized static void remove(Platform p){
        platforms.remove(p);
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.drawRoundRect(position.x-LENGTH/2, position.y,LENGTH,HEIGHT,15,15 );

    }

}
