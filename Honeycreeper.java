/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to animate a honeycreeper bird
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class Honeycreeper extends Thread {
    private Console c; // the output console
    
    // local colours for the class
    // variables are final as the assigned colour will not change
    private final Color BLACK = new Color(0, 0, 0);
    private final Color SKYBLUE = new Color(150, 200, 250);
    private final Color GREEN = new Color(130, 200, 140);
    private final Color MUTEDGREEN = new Color(165, 220, 100);
    private final Color DARKGRAY = new Color(80, 80, 80);

    // coordinate positions of the bird
    private int birdX=795,birdY=30;
    // x-axis of reflection; used when the bird is leaving the birdfeeder.
    private int xAxis=2*birdX;
    // line of reflection on the y axis, used as an aid to draw the falling bird
    private int yAxis; 

    // animates the bird diving to its place for the given duration (in terms of no. of pecks)
    public void diving(int dur,int rateX,int rateY) {
        try {
            for (int itr=0;itr<dur;itr++) {
                dive(true); // drawn
                Thread.sleep(16);
                dive(false); // erase

                // move bird
                birdX+=rateX;
                birdY+=rateY;
            }
        } catch (Exception e) {};
    }

    // animates the bird pecking at the birdfeeder for the given duration (in terms of no. of pecks)
    // fill determines whether the bird is being drawn or erased
    public void pecking(int dur) {
        try {
            for (int itr=0;itr<dur;itr++) {
                still(true); // draw standing

                Thread.sleep(800);
                still(false); // erase standing
                peck(true); // draw pecking

                Thread.sleep(300);
                peck(false); // erase pecking
            }
            // before the blue bird exits, it is standing (not pecking the seed)
            still(true);
            Thread.sleep(600);
            still(false);
        } catch (Exception e) {};
    }

    // animates the bird falling upon death
    public void falling() {
        try {
            while (birdY>-25) { // before the bird lands
                dead(true); // draw falling 
                Thread.sleep(16);
                dead(false); // erase falling

                // redraws the birdfeeder base that the sick bird covered
                c.setColor(DARKGRAY);
                if (180-birdY<=80) c.fillRect(370,240,100,Math.min(180-birdY,21)); // do not continue to draw the base when unnecessary (i.e. the bird is past it)
                
                // move bird
                // the bird is drawn flipped, hence why the number is decremented as opposed to incremented
                birdY-=4; 
            }
            // after the bird finished moving, draw its final position
            dead(true);
        } catch (Exception e) {};
    }

    // draws one frame of the bird diving to its place
    // drawn determines whether the bird is being drawn or erased
    public void dive(boolean drawn) {
        synchronized(c) {
            if (!drawn) c.setColor(SKYBLUE);

            // beak
            if (drawn) c.setColor(BLACK);
            int beakX[] = {xAxis-birdX-112,xAxis-birdX-112,xAxis-birdX-122}; 
            int beakY[] = {birdY+38,birdY+43,birdY+45};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legY=birdY+35;legY<=birdY+45;legY+=6) {
                c.drawLine(xAxis-birdX-50,legY+12,xAxis-birdX-20,legY+4);
                c.drawLine(xAxis-birdX-20,legY,xAxis-birdX-24,legY+4);
                c.drawLine(xAxis-birdX-20,legY+8,xAxis-birdX-24,legY+6);
            }

            // belly
            if (drawn) c.setColor(MUTEDGREEN);
            c.fillArc(xAxis-birdX-95,birdY+20,20,25,170,180);
            c.fillArc(xAxis-birdX-100,birdY+25,60,30,165,170);

            // front wing
            if (drawn) c.setColor(GREEN);
            c.fillOval(xAxis-birdX-105,birdY+20,50,23);
            c.fillOval(xAxis-birdX-80,birdY+20,50,20);
            int wingX[] = {xAxis-birdX-10,xAxis-birdX-50,xAxis-birdX-43};
            int wingY[] = {birdY+30,birdY+20,birdY+48};
            c.fillPolygon(wingX,wingY,3);

            // back wing
            if (drawn) c.setColor(MUTEDGREEN);
            // change the coordinates to match those of the back wing
            wingX[0]=xAxis-birdX-90;
            wingY[0]=birdY+30;
            wingX[1]=xAxis-birdX-45;
            wingY[1]=birdY+20;
            wingX[2]=xAxis-birdX-38;
            wingY[2]=birdY;
            c.fillPolygon(wingX,wingY,3);

            // head
            if (drawn) c.setColor(GREEN);
            c.fillOval(xAxis-birdX-123,birdY+25,30,25);

            // eye
            // the eye is not drawn if the bird is being erased as it is redundant
            if (drawn) {
                c.setColor(BLACK);
                c.fillOval(xAxis-birdX-107,birdY+35,3,3);
            }
        }
    }

    // draws the bird in a dead stationary position (once)
    // fill determines whether the bird is being drawn or erased
    public void dead(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {xAxis-birdX-100,xAxis-birdX-100,xAxis-birdX-110}; 
            int beakY[] = {yAxis-birdY-17,yAxis-birdY-22,yAxis-birdY-19};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(xAxis-legX,yAxis-birdY-60,xAxis-legX,yAxis-birdY-73); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(xAxis-legX,yAxis-birdY-73,xAxis-i,yAxis-birdY-77);
            }

            // belly
            if (fill) c.setColor(MUTEDGREEN);
            c.fillOval(xAxis-birdX-93,yAxis-birdY-55,48,30);
            c.fillOval(xAxis-birdX-95,yAxis-birdY-50,20,30);

            // wings
            if (fill) c.setColor(GREEN);
            int wingX[] = {xAxis-birdX,xAxis-birdX-60,xAxis-birdX-80};
            int wingY[] = {yAxis-birdY-60,yAxis-birdY-20,yAxis-birdY-40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(xAxis-birdX-88,yAxis-birdY-43,38,23);

            // head
            c.fillOval(xAxis-birdX-103,yAxis-birdY-32,30,25);

            // eye
            if (fill) {
                c.setColor(BLACK);
                c.fillOval(xAxis-birdX-97,yAxis-birdY-20,3,3);
            }
        }
    }    

    // draws the bird in a standing stationary position (once)
    // fill determines whether the bird is being drawn or erased
    public void still(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {xAxis-birdX-100,xAxis-birdX-100,xAxis-birdX-110}; 
            int beakY[] = {birdY+17,birdY+22,birdY+19};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(xAxis-legX,birdY+50,xAxis-legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(xAxis-legX,birdY+63,xAxis-i,birdY+67);
            }

            // belly
            if (fill) c.setColor(MUTEDGREEN);
            c.fillOval(xAxis-birdX-93,birdY+25,48,30);
            c.fillOval(xAxis-birdX-95,birdY+20,20,30);

            // wings
            if (fill) c.setColor(GREEN);
            int wingX[] = {xAxis-birdX,xAxis-(birdX+60),xAxis-(birdX+80)};
            int wingY[] = {birdY+60,birdY+20,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(xAxis-birdX-88,birdY+20,38,23);

            // head
            c.fillOval(xAxis-birdX-103,birdY+7,30,25);

            // eye
            if (fill) {
                c.setColor(BLACK);
                c.fillOval(xAxis-birdX-97,birdY+17,3,3);
            }
        }
    }    
    
    // draws the bird leaned forward to peck at birdfeeder (once)
    // fill determines whether the bird is being drawn or erased
    public void peck(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {xAxis-birdX-112,xAxis-birdX-112,xAxis-birdX-122}; 
            int beakY[] = {birdY+20,birdY+25,birdY+22};
            c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(xAxis-legX,birdY+50,xAxis-legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(xAxis-legX,birdY+63,xAxis-i,birdY+67);
            }

            // belly
            if (fill) c.setColor(MUTEDGREEN);
            c.fillOval(xAxis-birdX-95,birdY+20,20,30);
            c.fillArc(xAxis-birdX-100,birdY+22,55,33,150,200);

            // wings
            if (fill) c.setColor(GREEN);
            int wingX[] = {xAxis-birdX,xAxis-birdX-70,xAxis-birdX-90};
            int wingY[] = {birdY+60,birdY+20,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(xAxis-birdX-105,birdY+20,50,23);

            // head
            c.fillOval(xAxis-birdX-113,birdY+10,30,25);

            // eye
            if (fill) {
                c.setColor(BLACK);
                c.fillOval(xAxis-birdX-97,birdY+20,3,3);
            }
        }
    }

    // Honeycreeper constructor
    public Honeycreeper(Console con) {
        c = con; 
    }

    // what the Thread calls upon start
    public void run() {
        diving(30,10,5);
        pecking(3);
        birdX-=685; // adjustment
        xAxis-=685; // initialize xAxis
        yAxis=birdY+275; // initialize yAxis
        falling();
    }
}