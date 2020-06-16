/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to animate a canary. It can take different colours and positions
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class Canary extends Thread {
    private Console c; // the output console
    
    // local colours for the class
    // variables are final as the assigned colour will not change
    // on the other hand, birdColour and underWing do change; thus they are not final
    private Color birdColour, underWing;
    private final Color BLACK = new Color(0, 0, 0);
    private final Color SKYBLUE = new Color(150, 200, 250);
    private final Color DARKGRAY = new Color(80, 80, 80);

    // coordinate positions of the bird
    private int birdX=-80,birdY=25; //(-85,25) is the default starting position
    // x-axis of reflection; used when the bird is leaving the birdfeeder.
    private int xAxis;
    
    // animates the bird diving to its place for the given duration (in terms of no. of pecks)
    public void diving(int dur,int rateX,int rateY) {
        try {
            for (int itr=0;itr<dur;itr++) {
                dive(true); // draw bird
                Thread.sleep(16);
                dive(false); // erase bird

                // move the bird
                birdX+=rateX;
                birdY+=rateY;
            }
        } catch (Exception e) {};
    }

    // animates the bird leaving the birdfeeder
    public void leaving(int rateX,int rateY) {
        try {
            while (birdX<915) {
                leave(true); // draw
                Thread.sleep(16);
                leave(false); // erase

                // move bird
                birdX+=rateX;
                birdY+=rateY;
            }
        } catch (Exception e) {};
    }

    // animates the bird pecking at the birdfeeder for the given duration (in terms of no. of pecks)
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
            // before the canary exits, it is standing (not pecking the seed)
            still(true); // draw
            Thread.sleep(600);
            still(false); // erase
        } catch (Exception e) {};
    }
    // the bird had entered from the right side of the screen and is facing towards the left to peck at the sseds
    public void pecking(int dur,boolean right) {
        try {
            for (int itr=0;itr<dur;itr++) {
                still(true,true); // draw standing and facing to the left

                Thread.sleep(800);
                still(false,true); // erase standing and facing to the left
                peck(true,true); // draw pecking and facing to the left

                Thread.sleep(300);
                peck(false,true); // erase pecking and facing to the left
            }
            // before the canary exits, it is standing (not pecking the seed)
            still(true,true);
            Thread.sleep(600);
            still(false,true);
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
            int beakX[] = {birdX+112,birdX+112,birdX+122}; 
            int beakY[] = {birdY+38,birdY+43,birdY+45};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legY=birdY+35;legY<=birdY+45;legY+=6) {
                c.drawLine(birdX+50,legY+12,birdX+20,legY+4);
                c.drawLine(birdX+20,legY,birdX+24,legY+4);
                c.drawLine(birdX+20,legY+8,birdX+24,legY+6);
            }

            // eye
            // the eye is not drawn if the bird is being erased as it is redundant
            if (drawn) {
                c.fillOval(birdX+104,birdY+35,3,3);
            }

            // head
            if (drawn) c.setColor(birdColour);
            c.fillOval(birdX+83,birdY+25,30,25);

            // belly
            c.fillArc(birdX+75,birdY+20,20,25,170,180);
            c.fillArc(birdX+40,birdY+25,60,30,165,170);

            // front wing
            c.fillOval(birdX+55,birdY+20,50,23);
            c.fillOval(birdX+30,birdY+20,50,20);
            int fwingX[] = {birdX+10,birdX+50,birdX+43};
            int fwingY[] = {birdY+30,birdY+20,birdY+48};
            c.fillPolygon(fwingX,fwingY,3);

            // back wing 
            if (drawn) c.setColor(underWing);
            int bwingX[] = {birdX+90,birdX+45,birdX+38};
            int bwingY[]= {birdY+30,birdY+20,birdY};
            c.fillPolygon(bwingX,bwingY,3);

            // wing curve
            if (drawn) c.setColor(BLACK);
            c.drawArc(birdX+35,birdY+15,56,30,250,100);
        }
    }

    // draws one frame of the bird leaving the birdfeeder
    // drawn determines whether the bird is being drawn or erased
    public void leave(boolean drawn) {
        synchronized(c) {
            // if the bird is being erased, then all colour is to match the sky
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

            // back wing
            if (drawn) c.setColor(underWing);
            int bwingX[]={xAxis-birdX-90,xAxis-birdX-45,xAxis-birdX-38};
            int bwingY[]={birdY+30,birdY+20,birdY};
            c.fillPolygon(bwingX,bwingY,3);

            // front wing
            if (drawn) c.setColor(birdColour);
            c.fillOval(xAxis-birdX-105,birdY+20,50,23);
            c.fillOval(xAxis-birdX-80,birdY+20,50,20);
            int fwingX[] = {xAxis-birdX-10,xAxis-birdX-50,xAxis-birdX-43};
            int fwingY[] = {birdY+30,birdY+20,birdY+48};
            c.fillPolygon(fwingX,fwingY,3);

            // belly
            if (drawn) c.setColor(birdColour);
            c.fillArc(xAxis-birdX-95,birdY+20,20,25,170,180);
            c.fillOval(xAxis-birdX-100,birdY+25,60,30);

            // wing curve
            if (drawn) c.setColor(BLACK);
            c.drawArc(xAxis-(birdX+35)-56,birdY+15,56,30,250,100);

            // head
            if (drawn) c.setColor(birdColour);
            c.fillOval(xAxis-birdX-113,birdY+25,30,25);

            // eye
            // the eye is not drawn if the bird is being erased as it is redundant
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
            // if the bird is being erased, then all colour is to match the sky
            if (!drawn) c.setColor(SKYBLUE);

	        // beak
	        if (drawn) c.setColor(BLACK);
	        int beakX[] = {birdX+100,birdX+100,birdX+110}; 
	        int beakY[] = {birdY+17,birdY+22,birdY+19};
	        c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+55,legX,birdY+68); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+68,i,birdY+72);
            }

            // belly
            if (drawn) c.setColor(birdColour);
            c.fillOval(birdX+45,birdY+25,48,30);
            c.fillOval(birdX+75,birdY+20,20,30);

            // head
            c.fillOval(birdX+73,birdY+7,30,25);

            // wings
            int wingX[] = {birdX,birdX+60,birdX+80};
            int wingY[] = {birdY+70,birdY+20,birdY+45};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+50,birdY+20,38,23);

            // wing curve
            if (drawn) c.setColor(BLACK);
            c.drawArc(birdX+35,birdY+15,50,30,250,100);

	        // eye
            // regardless of colour the eye need not be drawn if the bird is being erased
	        if (drawn) c.fillOval(birdX+94,birdY+17,3,3);
	    }
    }
    // overloaded method with additional boolean parameter
    // this indicates that the bird has come from the right side of the screen 
    // and as a result is facing to the left
    public void still(boolean drawn,boolean right) {
        synchronized(c) {
            // if the bird is being erased, then all colour is to match the sky
            if (!drawn) c.setColor(SKYBLUE);

            // beak
            if (drawn) c.setColor(BLACK);
            int beakX[] = {xAxis-birdX-100,xAxis-birdX-100,xAxis-birdX-110}; 
            int beakY[] = {birdY+17,birdY+22,birdY+19};
            c.fillPolygon(beakX,beakY,3);

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(xAxis-legX,birdY+55,xAxis-legX,birdY+68); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(xAxis-legX,birdY+68,xAxis-i,birdY+72);
            }

            // belly
            if (drawn) c.setColor(birdColour);
            c.fillOval(xAxis-birdX-93,birdY+25,48,30);
            c.fillOval(xAxis-birdX-95,birdY+20,20,30);

            // head
            c.fillOval(xAxis-birdX-103,birdY+7,30,25);

            // wings
            int wingX[] = {xAxis-birdX+10,xAxis-birdX-45,xAxis-birdX-70};
            int wingY[] = {birdY+70,birdY+25,birdY+45};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(xAxis-birdX-95,birdY+20,55,20);

            // wing curve
            if (drawn) c.setColor(BLACK);
            c.drawArc(xAxis-birdX-85,birdY+15,50,30,250,70);

            // eye
            c.fillOval(xAxis-birdX-97,birdY+17,3,3);
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
                c.drawLine(legX,birdY+55,legX,birdY+68); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+68,i,birdY+72);
            }

            // belly
            if (drawn) c.setColor(birdColour);
            c.fillOval(birdX+75,birdY+20,20,30);
            c.fillArc(birdX+45,birdY+22,55,33,200,200);

            // wings
            int wingX[] = {birdX,birdX+70,birdX+90};
            int wingY[] = {birdY+60,birdY+20,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+55,birdY+20,50,23);

            // wing curve
            if (drawn) c.setColor(BLACK);
            c.drawArc(birdX+35,birdY+15,56,30,250,100);

            // head
            if (drawn) c.setColor(birdColour);
            c.fillOval(birdX+83,birdY+10,30,25);

	        // eye
	        if (drawn) c.setColor(BLACK);
	        c.fillOval(birdX+104,birdY+20,3,3);
		}
    }

    // overloaded method with additional boolean parameter
    // this indicates that the bird has come from the right side of the screen 
    // and as a result is facing to the left
    public void peck(boolean drawn,boolean right) {
        synchronized(c) {
            // if the bird is being erased, then all colour is to match the sky
            if (!drawn) c.setColor(SKYBLUE);

            // beak
            if (drawn) c.setColor(BLACK);
            int beakX[] = {xAxis-birdX-112,xAxis-birdX-112,xAxis-birdX-122}; 
            int beakY[] = {birdY+20,birdY+25,birdY+22};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(xAxis-legX,birdY+55,xAxis-legX,birdY+68); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(xAxis-legX,birdY+68,xAxis-i,birdY+72);
            }

            // belly
            if (drawn) c.setColor(birdColour);
            c.fillOval(xAxis-(birdX+75)-20,birdY+20,20,30);
            c.fillOval(xAxis-(birdX+45)-55,birdY+22,55,33/*,200,200*/);

            // wings
            int wingX[] = {xAxis-birdX,xAxis-(birdX+70),xAxis-(birdX+90)};
            int wingY[] = {birdY+60,birdY+25,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(xAxis-(birdX+55)-50,birdY+20,50,23);

            // wing curve
            if (drawn) c.setColor(BLACK);
            c.drawArc(xAxis-(birdX+35)-56,birdY+15,56,30,250,50);

            // head
            if (drawn) c.setColor(birdColour);
            c.fillOval(xAxis-birdX-113,birdY+10,30,25);

            // eye
            if (drawn) c.setColor(BLACK);
            c.fillOval(xAxis-birdX-107,birdY+20,3,3);
        }
    }
    
    // Canary class overloaded constructors
    // the bird is yellow, and uses a specified starting position
    public Canary(Console con,int startX,int startY) {
        c = con;
        birdColour = new Color(240, 240, 50); // yellow
        underWing = new Color(245, 220, 110); // muted yellow
        birdX=startX;
        birdY=startY;
        xAxis=2*birdX;
    }

    // specified colours + starting position; the bird will not die
    public Canary(Console con, Color fill, Color under, int startX, int startY) {
        c = con;
        birdColour = fill;
        underWing = under;
        birdX=startX;
        birdY=startY;
        xAxis=2*birdX;
    }

    // specified colours; the bird uses the default start position
    public Canary(Console con, Color fill, Color under) {
        c = con;
        birdColour = fill;
        underWing = under;
        xAxis=2*birdX;
    }

    // what the Thread calls upon start
    public void run() {
        if (birdX<700) leftRun();
        else rightRun();
    }
    // run method if the canary enters from the right side
    public void rightRun() {
        leaving(6,4); // the canary enters the screen; in spite of the contradictory name, the leaving method is intentionally used
        xAxis=2*birdX-420; // set the axis of reflection
        pecking(3,true); // pecking at the birdfeeders
        birdX-=540; // adjustment
        // cover the birdfeeder base that the bird covered
        c.setColor(DARKGRAY);
        c.fillRect(birdX+30,240,45,10);
        diving(39,6,-4); // the canary exits the screen; in spite of the contradictory name, the diving method is intentionally used
        // leaving and diving are "swapped"
        // as when coming from the right side of the screen the direction that the bird faces
        // and is heading towards matches the "swap" of the two methods
    }
    // run method if the canary enters from the left side
    public void leftRun() {
        diving(19,12,8); // the canary enters the screen
        pecking(4); // pecking at the birdfeeders
        // cover the birdfeeder base that the bird covered
        c.setColor(DARKGRAY);
        c.fillRect(birdX+60,240,45,10);
        xAxis=2*birdX+120; // set the axis of reflection
        leaving(12,-8); // the canary exits the screen
    }
}