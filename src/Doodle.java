
import java.awt.*;


public class Doodle {

    private final Dimension SCREENSIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private final int WIDTH=SCREENSIZE.width/3;
    private final int HEIGHT=SCREENSIZE.height;
    private int baseHeight = HEIGHT/6*5;
    private Point currentPosition = new Point(WIDTH/2, baseHeight);
    private final int MAXVELOCITY=3;
    private int velocity = MAXVELOCITY;
    private int counter = 0;
    private int acceleration = -1;
    private final int SIZE=25;




   public void jump () {
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
        g.fillOval(currentPosition.x-(SIZE/2), currentPosition.y-SIZE,SIZE,SIZE);
        g.setColor(Color.black);
        g.drawOval(currentPosition.x-(SIZE/2), currentPosition.y-SIZE,SIZE,SIZE);
    }
}