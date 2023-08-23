import java.awt.*;

public class PlatformBus {

    private final PlatformDat platformDat;


    public PlatformBus(Point position) {platformDat = new PlatformDat(position);}

    public Point getPosition() {return platformDat.getPosition();}

    public void move(int heightDiff){
        platformDat.setPosition(new Point(platformDat.getPosition().x,platformDat.getPosition().y+heightDiff));
    }



}
