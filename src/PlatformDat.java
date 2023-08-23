import java.awt.*;
import java.util.ArrayList;

public class PlatformDat {
    private static final ArrayList<PlatformPre> platforms = new ArrayList<>();
    private static final int LENGTH =80;
    private static final int HEIGHT =15;
    private Point position;


    public PlatformDat(Point position) {
        this.position = position;
    }

    public Point getPosition() {return position;}

    public void setPosition(Point position) {this.position = position;}


    public static int getHEIGHT() {return HEIGHT;}
    public static int getLENGTH() {return LENGTH;}

    public static synchronized ArrayList<PlatformPre> getPlatforms() {return platforms;}

    public static synchronized boolean applyMethod(ApplyFunction method){
        for(PlatformPre p: platforms){
            method.apply(p);
        }
        return true;
    }

    public static synchronized void clearPlatforms(){
        platforms.clear();
    }

    public static synchronized void addPlattform(PlatformPre p){platforms.add(p);}

    public static synchronized void remove(PlatformPre p){
        platforms.remove(p);
    }

    public static PlatformPre getNewPlatform(int heightOfHeighestPlatform, Dimension framesize){
        int x = (int)(Math.random()*(600-PlatformDat.getLENGTH())+PlatformDat.getLENGTH()/2);
        int y= heightOfHeighestPlatform-
                ((int)(Math.random()*(framesize.height/2- framesize.height/4-200))+20);
        return new PlatformPre(new Point(x,y));
    }
}