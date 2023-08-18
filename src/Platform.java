import java.awt.*;
import java.util.ArrayList;

public class Platform {

    private static ArrayList<Platform> platforms = new ArrayList<>();
    private static final int LENGTH =20;
    private static final int HEIGHT =5;
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

    public Platform(Point position) {
        this.position = position;
    }

    public static ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public static int getHighestPlatformHeight(){
        Platform highest = platforms.get(0);
        for(Platform p :platforms){
            if(p.getPosition().y<highest.getPosition().y){
                highest=p;
            }
        }
        return highest.getPosition().y;
    }

    public static void addPlattform(Platform p){
        platforms.add(p);
    }

    public static void remove(Platform p){
        platforms.remove(p);
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.drawRoundRect(position.x-LENGTH/2, position.y- HEIGHT,LENGTH,HEIGHT,10,10 );

    }

}
