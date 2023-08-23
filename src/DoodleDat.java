import java.awt.*;

public class DoodleDat {

    private final Dimension PANELSIZE = new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/3,Toolkit.getDefaultToolkit().getScreenSize().height);
    private final int baseHeight = PANELSIZE.height/4*3;
    private Point currentPosition = new Point(PANELSIZE.width/2, baseHeight);
    private final int MAXVELOCITY=2;
    private int velocity = MAXVELOCITY;

    private int horizontalVelocity=0;
    private final int MAXACCELERATIONCOUNTER=60;

    public void setCurrentPosition(Point currentPosition) {this.currentPosition = currentPosition;}

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public Dimension getPANELSIZE() {
        return PANELSIZE;
    }

    public int getBaseHeight() {
        return baseHeight;
    }

    public int getMAXVELOCITY() {
        return MAXVELOCITY;
    }


    public int getHorizontalVelocity() {
        return horizontalVelocity;
    }

    public int getMAXACCELERATIONCOUNTER() {
        return MAXACCELERATIONCOUNTER;
    }
}
