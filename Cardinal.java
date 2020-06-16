/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to animate a cardinal
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class Cardinal extends Thread {
    private Console c; // the output console
    
    // local colours for the class
    // variables are final as the assigned colour will not change
    private final Color BLACK = new Color(0, 0, 0);
    private final Color RED = new Color(210, 30, 25);
    private final Color MUTEDRED = new Color(175, 100, 100);
    private final Color SKYBLUE = new Color(150, 200, 250);
    private final Color DARKGRAY = new Color(80, 80, 80);

    // coordinate positions of the bird
    private int birdX=-155,birdY=125;
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

    // animates the bird falling upon death
    public void falling() {
        try {
            while (birdY>-12) { // before the bird lands
                dead(true); // drawn
                Thread.sleep(16);
                dead(false); // erase

                // redraws the birdfeeder base that the sick bird covered
                c.setColor(DARKGRAY);
                if (178-birdY<=80) c.fillRect(180,240,100,Math.min(178-birdY,21)); 
                // if statements prevents unnecessary drawing of the base (i.e. the bird is past it)

                // move bird
                birdY-=3; // the bird is drawn flipped, hence why the number is decremented as opposed to incremented
                birdX-=1;
            }
            // after the bird finished moving, draw its final position
            dead(true); 
        } catch (Exception e) {};
    }
    
    // animates the bird pecking at the birdfeeder for the given duration (in terms of no. of pecks)
    // fill determines whether the bird is being fill or erased
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
            // before the cardinal falls, it is standing (not pecking the seed)
            still(true);
            Thread.sleep(600);
            still(false);
        } catch (Exception e) {};
    }

    // draws one frame of the bird diving to its place
    // fill determines whether the bird is being fill or erased
    public void dive(boolean fill) {
        synchronized(c) {
            // if the bird is being erased, then all colour is to match the sky
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {birdX+112,birdX+107,birdX+122}; 
            int beakY[] = {birdY+30,birdY+41,birdY+41};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legY=birdY+35;legY<=birdY+45;legY+=6) {
                c.drawLine(birdX+50,legY+12,birdX+20,legY+4);
                c.drawLine(birdX+20,legY,birdX+24,legY+4);
                c.drawLine(birdX+20,legY+8,birdX+24,legY+6);
            }

            // belly
            if (fill) c.setColor(RED);
            c.fillArc(birdX+75,birdY+20,20,25,170,180);
            c.fillArc(birdX+40,birdY+25,60,30,165,170);

            // front wing
            if (fill) c.setColor(MUTEDRED);
            c.fillOval(birdX+55,birdY+20,50,23);
            c.fillOval(birdX+30,birdY+20,50,20);
            int fwingX[] = {birdX+10,birdX+50,birdX+43};
            int fwingY[] = {birdY+30,birdY+20,birdY+48};
            c.fillPolygon(fwingX,fwingY,3);

            // back wing 
            int bwingX[] = {birdX+90,birdX+45,birdX+38};
            int bwingY[] = {birdY+30,birdY+20,birdY};
            c.fillPolygon(bwingX,bwingY,3);

            // head
            if (fill) c.setColor(RED);
            int headX[] = {birdX+85,birdX+93,birdX+110};
            int headY[] = {birdY+30,birdY+10,birdY+30};
            c.fillPolygon(headX,headY,3);
            c.fillOval(birdX+83,birdY+22,30,25);

            // eye mask
            if (fill) c.setColor(BLACK);
            int maskX[] = {birdX+105,birdX+103,birdX+111,birdX+113};
            int maskY[] = {birdY+30,birdY+38,birdY+33,birdY+30};
            c.fillPolygon(maskX,maskY,maskX.length);
        }
    }

    // draws the bird in a standing stationary position (once)
    // fill determines whether the bird is being fill or erased
    public void dead(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {birdX+100,birdX+100,birdX+110}; 
            int beakY[] = {yAxis-birdY-15,yAxis-birdY-25,yAxis-birdY-19};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,yAxis-birdY-50,legX,yAxis-birdY-63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,yAxis-birdY-63,i,yAxis-birdY-67);
            }
        }
        synchronized(c) {
            // belly
            if (fill) c.setColor(RED);
            c.fillOval(birdX+45,yAxis-birdY-55,48,30);
            c.fillOval(birdX+75,yAxis-birdY-50,20,30);
        }
        synchronized(c) {
            // wings
            if (fill) c.setColor(MUTEDRED);
            int wingX[] = {birdX,birdX+60,birdX+80};
            int wingY[] = {yAxis-birdY-60,yAxis-birdY-20,yAxis-birdY-40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+50,yAxis-birdY-43,38,23);
        }
        synchronized(c) {
            // head
            if (fill) c.setColor(RED);
            int headX[] = {birdX+75,birdX+83,birdX+100};
            int headY[] = {yAxis-birdY-15,yAxis-birdY+5,yAxis-birdY-15};
            c.fillPolygon(headX,headY,3);
            c.fillOval(birdX+73,yAxis-birdY-32,30,25);
        }
        synchronized(c) {
            // eye mask
            if (fill) c.setColor(BLACK);
            int maskX[] = {birdX+95,birdX+93,birdX+101,birdX+103};
            int maskY[] = {yAxis-birdY-15,yAxis-birdY-23,yAxis-birdY-27,yAxis-birdY-15};
            c.fillPolygon(maskX,maskY,maskX.length);
        }
    }    

    // draws the bird in a standing stationary position (once)
    // fill determines whether the bird is being fill or erased
    public void still(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {birdX+100,birdX+100,birdX+110}; 
            int beakY[] = {birdY+15,birdY+25,birdY+19};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+50,legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+63,i,birdY+67);
            }

            // belly
            if (fill) c.setColor(RED);
            c.fillOval(birdX+45,birdY+25,48,30);
            c.fillOval(birdX+75,birdY+20,20,30);

            // wings
            if (fill) c.setColor(MUTEDRED);
            int wingX[] = {birdX,birdX+60,birdX+80};
            int wingY[] = {birdY+60,birdY+20,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+50,birdY+20,38,23);

            // head
            if (fill) c.setColor(RED);
            int headX[] = {birdX+75,birdX+83,birdX+100};
            int headY[] = {birdY+15,birdY-5,birdY+15};
            c.fillPolygon(headX,headY,3);
            c.fillOval(birdX+73,birdY+7,30,25);

            // eye mask
            if (fill) c.setColor(BLACK);
            int maskX[] = {birdX+95,birdX+93,birdX+101,birdX+103};
            int maskY[] = {birdY+15,birdY+23,birdY+27,birdY+15};
            c.fillPolygon(maskX,maskY,maskX.length);
        }
    }    
    
    // draws the bird leaned forward to peck at birdfeeder (once)
    // fill determines whether the bird is being fill or erased
    public void peck(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);
            
            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {birdX+110,birdX+110,birdX+120}; 
            int beakY[] = {birdY+15,birdY+25,birdY+19};
            c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+50,legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+63,i,birdY+67);
            }

            // belly
            if (fill) c.setColor(RED);
            c.fillOval(birdX+75,birdY+20,20,30);
            c.fillArc(birdX+45,birdY+22,55,33,200,200);

            // wings
            if (fill) c.setColor(MUTEDRED);
            int wingX[] = {birdX,birdX+70,birdX+90};
            int wingY[] = {birdY+60,birdY+20,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+55,birdY+20,40,23);

            // head
            if (fill) c.setColor(RED);
            int headX[] = {birdX+85,birdX+93,birdX+110};
            int headY[] = {birdY+15,birdY-5,birdY+15};
            c.fillPolygon(headX,headY,3);
            c.fillOval(birdX+83,birdY+7,30,25);

            // eye mask
            if (fill) c.setColor(BLACK);
            int maskX[] = {birdX+105,birdX+103,birdX+111,birdX+113};
            int maskY[] = {birdY+16,birdY+23,birdY+27,birdY+16};
            c.fillPolygon(maskX,maskY,maskX.length);
        }
    }
    // Cardinal constructor
    // the bird does not die
    public Cardinal(Console con) {
        c = con;
    }
    // what the Thread calls upon start
    public void run() {
        diving(30,10,2); // the bird enters the screen
        pecking(3); 
        yAxis=2*birdY+75; // initializing the yAxis
        falling(); // the bird dies
    }
}