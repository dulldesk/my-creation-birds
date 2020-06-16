/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to animate a sick bird
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class SickBird implements Runnable {
    private Console c; // the output console
    
    // local colours for the class
    // variables are final as the assigned colour will not change
    private final Color BLACK = new Color(0, 0, 0);
    private final Color WHITE = new Color(255, 255, 255);
    private final Color SKYBLUE = new Color(150, 200, 250);
    private final Color DARKGRAY = new Color(80, 80, 80);

    // coordinate positions of the bird
    private int birdX=-97,birdY=60;
    // line of reflection on the y axis, used as an aid to draw the falling bird
    private int yAxis; 
    
    // animates the bird diving to its place for the given duration (in terms of no. of pecks)
    public void diving(int dur,int rateX,int rateY) {
        try {
            for (int itr=0;itr<dur;itr++) {
                dive(true); // draw 
                Thread.sleep(6);
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
            Thread.sleep(800);
            still(false);
        } catch (Exception e) {};
    }

    // animates the bird falling upon death
    public void falling() {
        try {
            while (birdY>-55) {
                dead(true); // draw
                Thread.sleep(16);
                dead(false); // erase

                // redraws the birdfeeder base that the sick bird covered
                c.setColor(DARKGRAY);
                if (178-birdY<=80) c.fillRect(180,240,100,Math.min(178-birdY,21)); // do not continue to draw the base when unnecessary (i.e. the bird is past it)

                // increment the y-position to keep the bird moving. 
                birdY-=3; // the bird is drawn flipped, hence why the number is decremented as opposed to incremented
            }
            dead(true);
        } catch (Exception e) {};
    }
    
    // draws one frame of the bird diving to its place
    // fill determines whether the bird is being drawn or erased
    public void dive(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);
            else c.setColor(BLACK); //entire bird, excluding the specks on top, is black
            
            // beak
            int beakX[] = {birdX+115,birdX+110,birdX+125}; 
            int beakY[] = {birdY+28,birdY+36,birdY+38};
            c.fillPolygon(beakX,beakY,3);
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legY=birdY+35;legY<=birdY+45;legY+=6) {
                c.drawLine(birdX+50,legY+12,birdX+20,legY+4);
                c.drawLine(birdX+20,legY,birdX+24,legY+4);
                c.drawLine(birdX+20,legY+8,birdX+24,legY+6);
            }

            // belly
            c.fillOval(birdX+75,birdY+20,20,25);
            c.fillOval(birdX+40,birdY+25,60,30);

            // front wing
            c.fillOval(birdX+55,birdY+20,50,23);
            c.fillOval(birdX+30,birdY+20,50,20);
            int wingX[] = {birdX+10,birdX+50,birdX+43};
            int wingY[] = {birdY+30,birdY+20,birdY+48};
            c.fillPolygon(wingX,wingY,3);

            // back wing ; coordinates are edited to match
            wingX[0]=birdX+90;
            wingY[0]=birdY+30;
            wingX[1]=birdX+45;
            wingY[1]=birdY+20;
            wingX[2]=birdX+38;
            wingY[2]=birdY;
            c.fillPolygon(wingX,wingY,3);

            // head
            c.fillOval(birdX+83,birdY+15,35,25);

            // the white specks
            if (fill) {
                c.setColor(WHITE);
                c.fillOval(birdX+52,birdY+37,3,3);
                c.fillOval(birdX+69,birdY+26,3,3);
                c.fillOval(birdX+64,birdY+37,3,3);
                c.fillOval(birdX+66,birdY+48,3,3);
                c.fillOval(birdX+80,birdY+48,3,3);
                c.fillOval(birdX+90,birdY+38,3,3);
                c.fillOval(birdX+78,birdY+39,3,3);
                c.fillOval(birdX+83,birdY+26,3,3);
                c.fillOval(birdX+43,birdY+43,3,3);
                c.fillOval(birdX+95,birdY+20,3,3);
                c.fillOval(birdX+105,birdY+21,3,3);
                c.fillOval(birdX+96,birdY+29,3,3);
                c.fillOval(birdX+104,birdY+34,3,3);
                c.fillOval(birdX+112,birdY+29,3,3);
                c.fillOval(birdX+26,birdY+32,3,3);
                c.fillOval(birdX+41,birdY+33,3,3);
                c.fillOval(birdX+56,birdY+27,3,3);
                c.fillOval(birdX+45,birdY+8,3,3);
                c.fillOval(birdX+54,birdY+17,3,3);
            }
        } 
    }

    // draws the bird in a standing stationary position (once)
    // fill determines whether the bird is being drawn or erased
    public void still(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE); // erasing the bird
            else c.setColor(BLACK); //entire bird, excluding the specks on top, is black
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+50,legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+63,i,birdY+67);
            }
            
            // belly
            c.fillOval(birdX+45,birdY+25,48,30);
            c.fillOval(birdX+75,birdY+20,20,30);

            // wings
            int wingX[] = {birdX,birdX+60,birdX+80};
            int wingY[] = {birdY+60,birdY+20,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+50,birdY+20,38,23);

            // head
            c.fillOval(birdX+73,birdY+12,35,25);

            // beak
            int beakX[] = {birdX+105,birdX+105,birdX+115}; 
            int beakY[] = {birdY+22,birdY+27,birdY+24};
            c.fillPolygon(beakX,beakY,3);

            // white specks
            if (fill) {
                c.setColor(WHITE);
                c.fillOval(birdX+17,birdY+50,3,3);
                c.fillOval(birdX+30,birdY+44,3,3);
                c.fillOval(birdX+43,birdY+43,3,3);
                c.fillOval(birdX+55,birdY+40,3,3);
                c.fillOval(birdX+74,birdY+46,3,3);
                c.fillOval(birdX+65,birdY+39,3,3);
                c.fillOval(birdX+85,birdY+42,3,3);
                c.fillOval(birdX+75,birdY+35,3,3);
                c.fillOval(birdX+76,birdY+24,3,3);
                c.fillOval(birdX+64,birdY+26,3,3);
                c.fillOval(birdX+49,birdY+33,3,3);
                c.fillOval(birdX+88,birdY+28,3,3);
                c.fillOval(birdX+85,birdY+18,3,3);
                c.fillOval(birdX+98,birdY+17,3,3);
                c.fillOval(birdX+96,birdY+30,3,3);
                c.fillOval(birdX+102,birdY+24,3,3);
                c.fillOval(birdX+63,birdY+48,3,3);
            }
        }
    }    
    
    // draws the bird in a dead stationary position (once)
    // fill determines whether the bird is being drawn or erased
    public void dead(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE); // erasing the bird
            else c.setColor(BLACK); //entire bird, excluding the specks on top, is black
            
            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,yAxis-birdY-50,legX,yAxis-birdY-63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,yAxis-birdY-63,i,yAxis-birdY-67);
            }

            // belly
            c.fillOval(birdX+45,yAxis-birdY-55,48,30);
            c.fillOval(birdX+75,yAxis-birdY-50,20,30);

            // wings
            int wingX[] = {birdX,birdX+60,birdX+80};
            int wingY[] = {yAxis-birdY-60,yAxis-birdY-20,yAxis-birdY-40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+50,yAxis-birdY-43,38,23);

            // head
            c.fillOval(birdX+73,yAxis-birdY-37,35,25);

            // beak
            int beakX[] = {birdX+105,birdX+105,birdX+115}; 
            int beakY[] = {yAxis-birdY-22,yAxis-birdY-27,yAxis-birdY-24};
            c.fillPolygon(beakX,beakY,3);

            // white specks
            if (fill) {
                c.setColor(WHITE);
                c.fillOval(birdX+17,yAxis-(birdY+53),3,3);
                c.fillOval(birdX+30,yAxis-(birdY+47),3,3);
                c.fillOval(birdX+43,yAxis-(birdY+46),3,3);
                c.fillOval(birdX+55,yAxis-(birdY+43),3,3);
                c.fillOval(birdX+74,yAxis-(birdY+49),3,3);
                c.fillOval(birdX+65,yAxis-(birdY+42),3,3);
                c.fillOval(birdX+85,yAxis-(birdY+45),3,3);
                c.fillOval(birdX+75,yAxis-(birdY+38),3,3);
                c.fillOval(birdX+76,yAxis-(birdY+27),3,3);
                c.fillOval(birdX+64,yAxis-(birdY+29),3,3);
                c.fillOval(birdX+49,yAxis-(birdY+36),3,3);
                c.fillOval(birdX+88,yAxis-(birdY+31),3,3);
                c.fillOval(birdX+85,yAxis-(birdY+21),3,3);
                c.fillOval(birdX+98,yAxis-(birdY+20),3,3);
                c.fillOval(birdX+96,yAxis-(birdY+33),3,3);
                c.fillOval(birdX+102,yAxis-(birdY+27),3,3);
                c.fillOval(birdX+63,yAxis-(birdY+51),3,3);
            }
        }
    }
    // draws the bird leaned forward to peck at birdfeeder (once)
    // fill determines whether the bird is being drawn or erased
    public void peck(boolean fill) {
        synchronized(c) {
            if (!fill) c.setColor(SKYBLUE);
            else c.setColor(BLACK); //entire bird, excluding the specks on top, is black

            // legs
            // this loop will iterate twice, once for the left leg and once for the right leg
            for (int legX=birdX+65;legX<=birdX+80;legX+=15) {
                c.drawLine(legX,birdY+50,legX,birdY+63); // the main leg
                // draws the "feet" of the bird leg; the loop will iterate three times
                for (int i=legX-5;i<=legX+5;i+=5) c.drawLine(legX,birdY+63,i,birdY+67);
            }

            // belly
            c.fillOval(birdX+75,birdY+20,20,30);
            c.fillArc(birdX+45,birdY+22,55,33,200,200);

            // wings
            int wingX[] = {birdX,birdX+70,birdX+90};
            int wingY[] = {birdY+60,birdY+20,birdY+40};
            c.fillPolygon(wingX,wingY,3);
            c.fillOval(birdX+55,birdY+20,50,23);

            // head
            c.fillOval(birdX+83,birdY+15,35,25);

            // beak
            int beakX[] = {birdX+117,birdX+117,birdX+127}; 
            int beakY[] = {birdY+25,birdY+30,birdY+27};
            c.fillPolygon(beakX,beakY,3);

            if (fill) {
                c.setColor(WHITE);
                c.fillOval(birdX+52,birdY+37,3,3);
                c.fillOval(birdX+69,birdY+26,3,3);
                c.fillOval(birdX+64,birdY+37,3,3);
                c.fillOval(birdX+66,birdY+48,3,3);
                c.fillOval(birdX+80,birdY+48,3,3);
                c.fillOval(birdX+90,birdY+38,3,3);
                c.fillOval(birdX+78,birdY+39,3,3);
                c.fillOval(birdX+83,birdY+26,3,3);
                c.fillOval(birdX+43,birdY+43,3,3);
                c.fillOval(birdX+30,birdY+47,3,3);
                c.fillOval(birdX+19,birdY+52,3,3);
                c.fillOval(birdX+95,birdY+20,3,3);
                c.fillOval(birdX+105,birdY+21,3,3);
                c.fillOval(birdX+96,birdY+29,3,3);
                c.fillOval(birdX+104,birdY+34,3,3);
                c.fillOval(birdX+112,birdY+29,3,3);
            }
        }
    }
    // SickBird constructor
    // as the bird is guaranteed to die, there is only one constructor and it asks for the time it will take for the bird to die
    public SickBird(Console con) {
        c = con;
    }
    // what the Thread calls upon start
    public void run() {
        diving(60,4,2);
        pecking(4);
        yAxis=2*birdY+75;
        falling();
    }
}