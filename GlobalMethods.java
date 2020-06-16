/*
Celeste Luo
Ms. Krasteva
October 28, 2019
A class containing global methods to ease the process of drawing the background within two classes. 
*/
import java.awt.*;
import hsa.Console;

public class GlobalMethods {
    private Console c;

    // method to draw an oval using outlines 
    // parameters:
    // (x,y) --  top left corner coordinate of the oval
    // w -- width
    // h -- height
    public void lineFillOval(int x, int y, int w, int h) {
        for (int i=0;i<Math.max(w,h)-1;i++)
            c.drawOval(x+i,y+i,Math.max(w-2*i+1,0),Math.max(h-2*i+1,0));
    }

    // method to draw a rectangle using lines 
    // parameters:
    // (x,y) --  top left corner coordinate of the oval
    // w -- width
    // h -- height
    public void lineFillRect(int x, int y, int w, int h) {
        for (int i=0;i<w;i++)
            c.drawLine(x+i,y,x+i,y+h);
    }

    // method to generate a random integer given the lower and upper bounds (inclusive)
    // the method will return the aforementioned integer
    // parameters:
    // lower -- lower bound (inclusive) of the random integer
    // upper -- upper bound (inclusive) of the random integer
    public int randInt(int lower, int upper) {
	   return (int)Math.floor(Math.random()*(upper+1-lower))+lower;
    }

    // GlobalMethods constructor
    public GlobalMethods(Console con) {
    	c = con;
    }
}


