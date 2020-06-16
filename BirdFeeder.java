/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to draw a bird feeder. While not an official animated object, it implements Runnable as it has minor animations.
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class BirdFeeder {
    private Console c;
    private GlobalMethods g;

    // draws the bird feeder
    public void draw() {
    	//local colour variables 
        final Color DARKGRAY = new Color(80, 80, 80);
        final Color GLASSBLUE = new Color(200, 230, 250);

        // seed colours
        final Color SEEDBEIGE = new Color(245, 220, 185);
        final Color SEEDBROWN = new Color(140, 80, 40);
        final Color YELLOW = new Color(240, 240, 50);

        // seeds
        final Color SEEDCOLOURS[] = {SEEDBROWN,SEEDBEIGE,YELLOW};
        // fills the birdfeeder with seeds; the positions and colours are somewhat random
        for (int x=272;x<=365;x+=g.randInt(6,8)) {
            for (int y=183;y<=234;y+=g.randInt(3,5)) {
                c.setColor(SEEDCOLOURS[g.randInt(0,2)]);
                g.lineFillOval(x,y,7,4);
            }
        }
        // glass of birdfeeder
        c.setColor(GLASSBLUE);
        g.lineFillRect(270,160,3,80);
        g.lineFillRect(370,160,3,80);

        c.setColor(DARKGRAY);
        // pole
        g.lineFillRect(310,260,20,240);
        // base
        g.lineFillRect(180,240,300,20);

        // roof of birdfeeder
        for (int x=240;x<=260;x++) {
            c.drawLine(x,180,x+60,120);
            c.drawLine(640-x,180,580-x,120);
        }
        for (int x=300;x<=340;x++) 
            c.drawLine(320,100,x,120);
    }

    // BirdFeeder constructor
    public BirdFeeder(Console con) {
    	c = con;
        g = new GlobalMethods(con);
    }
}


