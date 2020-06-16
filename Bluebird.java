/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to animate a blue bird
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class Bluebird implements Runnable {
    private Console c; // the output console
    
    // local colours for the class
    // variables are final as the assigned colour will not change
    private final Color BLACK = new Color(0, 0, 0);
    private final Color WHITE = new Color(255, 255, 255);
    private final Color LIGHTBLUE = new Color(150, 150, 255);
    private final Color MUTEDBLUE = new Color(110, 120, 245);
    private final Color SKYBLUE = new Color(150, 200, 250);
    private final Color DARKGRAY = new Color(80, 80, 80);

    // coordinate positions of the bird
    private int birdX=-150,birdY=60;
    // x-axis of reflection; used when the bird is leaving.
    private int xAxis;

    // animates the bird diving to its place for the given duration (in terms of no. of pecks)
    public void diving(int dur,int rateX,int rateY) {
        try {
            for (int itr=0;itr<dur;itr++) {
                dive(true); // draw
                Thread.sleep(30);
                dive(false); // erase

                // move the bird
                birdX+=rateX;
                birdY+=rateY;
            }
        } catch (Exception e) {};
    }

    // animates the bird exiting the screen
    public void leaving(int dur,int rateX,int rateY) {
        try {
            for (int itr=0;itr<dur;itr++) {
                leave(true); // draw
                Thread.sleep(30);
                leave(false); // erase

                // move the bird
                birdX+=rateX;
                birdY+=rateY;
            }
        } catch (Exception e) {};
    }

    // animates the bird pecking at the birdfeeder for the given duration (in terms of no. of pecks)
    public void pecking(int dur) {
        try {
            for (int itr=0;itr<dur;itr++) {
                still(true); // standing; drawn

                Thread.sleep(600);
                still(false); // erase standing
                peck(true); // pecking; drawn

                Thread.sleep(200);
                peck(false); // erase pecking
            }
            // before the blue bird exits, it is standing (not pecking the seed)
            still(true);
            Thread.sleep(600);
            still(false);
        } catch (Exception e) {};
    }

    // draws one frame of the bird diving to its place
    // drawn determines whether the bird is being drawn or erased
    public void dive(boolean drawn) {
        synchronized(c) {
            // if the bird is being erased, then all colour is to match the sky
            if (!drawn) c.setColor(SKYBLUE);
            
            // beak
            if (drawn) c.setColor(BLACK); // only change the colour if the bird is being drawn
            int beakX[] = {birdX+112,birdX+110,birdX+122}; 
            int beakY[] = {birdY+38,birdY+43,birdY+45};
            c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legY=birdY+35;legY<=birdY+45;legY+=6) {
                c.drawLine(birdX+50,legY+12,birdX+20,legY+4);
                c.drawLine(birdX+20,legY,birdX+24,legY+4);
                c.drawLine(birdX+20,legY+8,birdX+24,legY+6);
            }

            // belly
            if (drawn) c.setColor(WHITE);
            c.fillArc(birdX+75,birdY+23,20,30,170,180);
            c.fillArc(birdX+40,birdY+25,60,35,165,170);

            // back wing 
            if (drawn) c.setColor(MUTEDBLUE);
            int bwingX[] = {birdX+90,birdX+45,birdX+38};
            int bwingY[] = {birdY+30,birdY+20,birdY};
            c.fillPolygon(bwingX,bwingY,3);

            // front wing
            if (drawn) c.setColor(LIGHTBLUE);
            c.fillOval(birdX+30,birdY+20,50,20);
            c.fillOval(birdX+55,birdY+25,50,23);
            int fwingX[] = {birdX+12,birdX+65,birdX+34};
            int fwingY[] = {birdY+15,birdY+21,birdY+35};
            c.fillPolygon(fwingX,fwingY,3);

            // head
            c.fillOval(birdX+83,birdY+25,30,25);

            // eye
            if (drawn) {
                c.setColor(BLACK);
                c.fillOval(birdX+104,birdY+35,3,3);
            }
        }
    }

    // draws one frame of the bird exiting the screen
    // drawn determines whether the bird is being drawn or erased
    public void leave(boolean drawn) {
        synchronized(c) {
            // if the bird is being erased, then all colour is to match the sky
            if (!drawn) c.setColor(SKYBLUE);
            
            // beak
            if (drawn) c.setColor(BLACK);
            int beakX[] = {xAxis-birdX-112,xAxis-birdX-110,xAxis-birdX-122}; 
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
            if (drawn) c.setColor(WHITE);
            c.fillArc(xAxis-birdX-95,birdY+23,20,30,170,180);
            c.fillArc(xAxis-birdX-100,birdY+25,60,35,165,170);

            // back wing
            if (drawn) c.setColor(MUTEDBLUE);
            int bwingX[] = {xAxis-birdX-90,xAxis-birdX-45,xAxis-birdX-38};
            int bwingY[] = {birdY+30,birdY+20,birdY};
            c.fillPolygon(bwingX,bwingY,3);

            // front wing
            if (drawn) c.setColor(LIGHTBLUE);
            c.fillOval(xAxis-birdX-80,birdY+20,50,20);
            c.fillOval(xAxis-birdX-105,birdY+25,50,23);
            int fwingX[] = {xAxis-birdX-12,xAxis-birdX-65,xAxis-birdX-34};
            int fwingY[] = {birdY+15,birdY+21,birdY+35};
            c.fillPolygon(fwingX,fwingY,3);

            // head
            c.fillOval(xAxis-birdX-113,birdY+25,30,25);

            // eye
            if (drawn) {
                c.setColor(BLACK);
                c.fillOval(xAxis-birdX-107,birdY+35,3,3);
            }
        }
    }

    // draws the bird in a standing stationary position (once)
    // drawn determines whether the bird is being drawn or erased
    public void still(boolean drawn) {
    	synchronized(c) {
    		if (!drawn) c.setColor(SKYBLUE);
	        // beak
	        if (drawn) c.setColor(BLACK);
	        int beakX[] = {birdX+100,birdX+100,birdX+110}; 
	        int beakY[] = {birdY+17,birdY+22,birdY+19};
	        c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+55,legX,birdY+64); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+64,i,birdY+66);
            }

	        // belly
	        if (drawn) c.setColor(WHITE);
	        c.fillOval(birdX+45,birdY+25,48,35);
	        c.fillOval(birdX+75,birdY+20,20,35);

	        // wings
	        if (drawn) c.setColor(LIGHTBLUE);
	        int wingX[] = {birdX+20,birdX+60,birdX+80};
	        int wingY[] = {birdY+60,birdY+20,birdY+40};
	        c.fillPolygon(wingX,wingY,3);
	        c.fillOval(birdX+50,birdY+20,38,23);

	        // head
	        c.fillOval(birdX+73,birdY+7,30,25);

	        // eye
            // the eye is not drawn if the bird is being erased as it is redundant
	        if (drawn) {
                c.setColor(BLACK);
    	        c.fillOval(birdX+94,birdY+17,3,3);
            }
	    }
    }

    // draws the bird leaned forward to peck at birdfeeder (once)
    // drawn determines whether the bird is being drawn or erased
    public void peck(boolean drawn) {
    	synchronized(c) {
            // if the bird is being erased, then all colour is to match the sky
    		if (!drawn) c.setColor(SKYBLUE);

	        // beak
	        if (drawn) c.setColor(BLACK);
	        int beakX[] = {birdX+112,birdX+112,birdX+122}; 
	        int beakY[] = {birdY+20,birdY+25,birdY+22};
	        c.fillPolygon(beakX,beakY,3);
	        
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+55,legX,birdY+64); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+64,i,birdY+66);
            }

	        // belly
	        if (drawn) c.setColor(WHITE);
	        c.fillOval(birdX+75,birdY+20,20,35);
	        c.fillArc(birdX+45,birdY+22,55,38,200,200);

	        // wings
	        if (drawn) c.setColor(LIGHTBLUE);
	        int wingX[] = {birdX+20,birdX+70,birdX+90};
	        int wingY[] = {birdY+60,birdY+20,birdY+40};
	        c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+55,birdY+20,50,23);

            // head
            c.fillOval(birdX+83,birdY+10,30,25);

	        // eye
            // the eye is not drawn if the bird is being erased as it is redundant
            if (drawn) {
                c.setColor(BLACK);
                c.fillOval(birdX+104,birdY+20,3,3);
            }
		}
    }
    
    // Bluebird constructor
    // If only the console is passed in, then the bird does not die
    public Bluebird(Console con) {
        c = con;
	}

    // what the Thread calls upon start
    public void run() {
	    diving(20,15,6); // the bluebird enters the screen
        pecking(5); // pecking at the birdfeeder seed
        // redraw the birdfeeder base that the bird had landed on
        c.setColor(DARKGRAY);
        c.fillRect(birdX+55,240,40,10);
        xAxis=2*birdX+120; // set x-axis based on current x-position
        leaving(20,15,-6); // the bluebird exits the screen
    }
}