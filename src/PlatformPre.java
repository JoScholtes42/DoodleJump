import java.awt.*;

public class PlatformPre {

    private final PlatformBus platformBus;

    public PlatformPre(Point position){
        platformBus= new PlatformBus(position);
    }

    public Point getPosition() {return platformBus.getPosition();}

    public void move(int heightDifference){
        if(platformBus.getPosition().y>Toolkit.getDefaultToolkit().getScreenSize().height){
            PlatformDat.remove(this);
        }
        platformBus.move(heightDifference);
    }

    public void draw(Graphics g){
        Point position = getPosition();
        final int LENGTH= PlatformDat.getLENGTH();
        final int HEIGHT= PlatformDat.getHEIGHT();
        g.setColor(Color.blue);
        g.drawRoundRect(position.x-LENGTH/2, position.y,LENGTH,HEIGHT,15,15 );
    }
}
