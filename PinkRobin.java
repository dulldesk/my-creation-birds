/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to animate a pink robin
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class PinkRobin extends Thread {
    private Console c; // the output console
        
    // local colours for the class
    // variables are final as the assigned colour will not change
    private final Color BLACK = new Color(0, 0, 0);
    private final Color PINK = new Color(245, 110, 200);
    private final Color MUTEDPINK = new Color(210, 190, 200);
    private final Color BLUEGRAY = new Color(95, 100, 130);
    private final Color SKYBLUE = new Color(150, 200, 250);
    private final Color DARKGRAY = new Color(80, 80, 80);

    // coordinate positions of the bird
    private int birdX=-90,birdY=115;
    // line of reflection on the y axis, used as an aid to draw the falling bird
    private int yAxis;
    // x-axis of reflection; used when the bird is leaving the birdfeeder. 
    private int xAxis=2*birdX+50;
    
    // animates the bird diving to its place for the given duration (in terms of no. of pecks)
    public void diving(int dur,int rateX,int rateY) {
        try {
            for (int itr=0;itr<dur;itr++) {
                dive(true); // draw
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
            while (birdY>-55) { // before the bird lands
                dead(true); // draw falling
                Thread.sleep(16);
                dead(false); // erase falling

                // redraws the birdfeeder base that the sick bird covered
                c.setColor(DARKGRAY);
                if (178-birdY<=80) c.fillRect(180,240,100,Math.min(178-birdY,21)); // do not continue to draw the base when unnecessary (i.e. the bird is past it)

                //move the bird
                birdX++;
                birdY-=6; 
                // the bird is drawn vertically flipped, hence why the number is decremented as opposed to incremented
            }
            // after the bird finished moving, draw its final position
            dead(true);
        } catch (Exception e) {};
    }

    // draws one frame of the bird diving to its place
    // fill determines whether the bird is being drawn or erased
    public void dive(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {birdX+112,birdX+112,birdX+122}; 
            int beakY[] = {birdY+40,birdY+45,birdY+42};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legY=birdY+45;legY<=birdY+55;legY+=6) {
                c.drawLine(birdX+65,legY+12,birdX+35,legY+4);
                c.drawLine(birdX+35,legY,birdX+39,legY+4);
                c.drawLine(birdX+35,legY+8,birdX+39,legY+6);
            }
            // belly
            if (fill) c.setColor(PINK);
            c.fillArc(birdX+75,birdY+30,20,35,180,250);
            c.fillArc(birdX+55,birdY+32,45,35,180,190);

            // wings
            if (fill) c.setColor(BLUEGRAY);
            int wingX[] = {birdX+35,birdX+60,birdX+80};
            int wingY[] = {birdY+44,birdY+40,birdY+57};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+60,birdY+37,38,20);

            // head
            c.fillOval(birdX+83,birdY+30,30,25);

            // eye
            if (fill) {
                c.setColor(BLACK);
                c.fillOval(birdX+104,birdY+40,3,3);
            }
        }
    }

    // draws the bird in a standing stationary position (once)
    // fill determines whether the bird is being drawn or erased
    public void dead(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);

            // beak
            if (fill) c.setColor(BLACK);
            int beakX[] = {birdX+100,birdX+100,birdX+110}; 
            int beakY[] = {yAxis-birdY-17,yAxis-birdY-22,yAxis-birdY-19};
            c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,yAxis-birdY-50,legX,yAxis-birdY-63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,yAxis-birdY-63,i,yAxis-birdY-67);
            }

            // belly
            if (fill) c.setColor(PINK);
            c.fillArc(birdX+45,yAxis-birdY-57,48,32,0,155);
            c.fillOval(birdX+75,yAxis-birdY-53,20,33);

            // wings
            if (fill) c.setColor(BLUEGRAY);
            int wingX[] = {birdX+40,birdX+60,birdX+80};
            int wingY[] = {yAxis-birdY-60,yAxis-birdY-30,yAxis-birdY-40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+60,yAxis-birdY-43,38,23);

            // head
            c.fillOval(birdX+73,yAxis-birdY-32,30,25);

            // eye
            if (fill) {
                c.setColor(BLACK);
                c.fillOval(birdX+94,yAxis-birdY-20,3,3);
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
	        int beakX[] = {birdX+100,birdX+100,birdX+110}; 
	        int beakY[] = {birdY+17,birdY+22,birdY+19};
	        c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+50,legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+63,i,birdY+67);
            }

	        // belly
	        if (fill) c.setColor(PINK);
	        c.fillArc(birdX+45,birdY+25,48,35,210,150);
	        c.fillOval(birdX+75,birdY+20,20,35);

	        // wings
	        if (fill) c.setColor(BLUEGRAY);
	        int wingX[] = {birdX+40,birdX+60,birdX+80};
	        int wingY[] = {birdY+60,birdY+30,birdY+40};
	        c.fillPolygon(wingX,wingY,3);
	        c.fillOval(birdX+60,birdY+20,38,23);

	        // head
	        c.fillOval(birdX+73,birdY+7,30,25);

	        // eye
	        if (fill) {
                c.setColor(BLACK);
    	        c.fillOval(birdX+94,birdY+17,3,3);
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
	        int beakX[] = {birdX+112,birdX+112,birdX+122}; 
	        int beakY[] = {birdY+20,birdY+25,birdY+22};
	        c.fillPolygon(beakX,beakY,3);


            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+50,legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+63,i,birdY+67);
            }


	        // belly
	        if (fill) c.setColor(PINK);
	        c.fillOval(birdX+75,birdY+20,20,35);
	        c.fillArc(birdX+45,birdY+22,55,38,210,190);

            // wings
            if (fill) c.setColor(BLUEGRAY);
            int wingX[] = {birdX+40,birdX+65,birdX+85};
            int wingY[] = {birdY+60,birdY+30,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+65,birdY+20,38,23);
            
            // head
            c.fillOval(birdX+83,birdY+10,30,25);

	        // eye
	        if (fill) {
                c.setColor(BLACK);
    	        c.fillOval(birdX+104,birdY+20,3,3);
            }
		}
    }
    
    // PinkRobin constructor
    public PinkRobin(Console con) {
        c = con;
	}

    // what the Thread calls upon start
    public void run() {
        diving(20,12,3); // the bird enters the screen
	    pecking(3);
        yAxis=2*birdY+80; //initializing the yAxis
        falling(); // the bird dies
    }
}