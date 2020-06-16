/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to draw the background
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class Background {
    private Console c;
    private GlobalMethods g;
    private final Color GRASSGREEN = new Color(66, 156, 16);
    private final Color SKYBLUE = new Color(150, 200, 250);
    private final Color SKYGREY = new Color(200, 200, 200);
    
    // method to draw the background
    public void drawSky(Color sky) {
        // the sky colour
        c.setColor(sky);
        g.lineFillRect(0,0,c.getWidth(),c.getHeight());
    }
    public void drawSky() {
        drawSky(SKYBLUE);
    }
    public void drawSky(boolean gray) {
        drawSky(SKYGREY);
    }
    public void birdfeeder() {
        BirdFeeder bf = new BirdFeeder(c);
    }
    public void drawSky(int gradient) {
        for (int i=0;i<=50;i+=5) {
        
            drawSky(new Color(150+i,200,250-i));
            drawBushes();
            birdfeeder();
            drawGrass();
               
            try {
                Thread.sleep(16);
            } catch (Exception e) {};
        }

    }
    public void drawBushes() {
        // the bushes
        c.setColor(GRASSGREEN);
        // left bush
        g.lineFillOval(-90,330,160,160);
        g.lineFillOval(40,400,90,90); 
        g.lineFillRect(0,450,130,100);

        // right bush
        g.lineFillOval(490,400,90,90);
        g.lineFillOval(530,350,120,120);
        g.lineFillOval(570,350,150,150);
        g.lineFillRect(490,440,200,100);
    }
    public void drawGrass() {
        // the grass
        // two layers of grass is drawn; one has an average height that is 20px shorter than the other
        // hdiff controls the height range of the grass; the front layer of grass (2nd loop iteration) is shorter than the back layer of grass (1st loop iteration)
        for (int hdiff=0;hdiff<=20;hdiff+=20) {
            for (int x=124;x<=490;x+=g.randInt(4,6)) {
                c.setColor(new Color(66, 156, 16));
                int h = g.randInt(40-hdiff,60-hdiff);
                for (int w=x;w<x+5;w++) c.drawLine(w,500,w,500-h);
            }
        }
    }
    public void draw(boolean a) {
        drawSky(1);
        drawBushes();
    }
    public void draw() {
        drawSky();
        drawBushes();
    }

    // Background constructor
    public Background(Console con) {
    	c = con;
        g = new GlobalMethods(con);
    }
}


