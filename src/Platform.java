import java.awt.*;
import java.util.ArrayList;

public class Platform {

    private static final ArrayList<Platform> platforms = new ArrayList<>();
    private static final int LENGTH =80;
    private static final int HEIGHT =15;
    private final Point position;

    public static int getLENGTH() {
        return LENGTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Point getPosition() {
        return position;
    }

    public Platform(Point position) {
        this.position = position;
    }

    public static ArrayList<Platform> getPlatforms() {
        return platforms;
    }


    public static void addPlattform(Platform p){
        System.out.println(p.getPosition().y + ": "+p);
        platforms.add(p);
    }

    public static void remove(Platform p){
        platforms.remove(p);
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.drawRoundRect(position.x-LENGTH/2, position.y,LENGTH,HEIGHT,15,15 );

    }

}
