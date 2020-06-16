/*
Celeste Luo
Ms. Krasteva
October 28, 2019
Class to draw and animate the outro
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class Outro implements Runnable {
    private Console c; // the output console
    
    // local colours for the class
    // variables are final as the assigned colour will not change
    private final Color BLACK = new Color(0, 0, 0);
    private final Color SKYBLUE = new Color(150, 200, 250);

    // animates a string entering and resting on the console
    // takes a String to print out (str), font size (fs), and ending y-coordinate position (y)
    public void play(String str, int fs,int y) {
        c.setFont(new Font("Courier New",Font.BOLD,fs));
        // each iteration of the loop draws the string lower and lower until it reaches the desired position (y)
        for (int yPos=-40;yPos<=y;yPos+=4) {
            // erase the previous text drawing
            synchronized (c) {
                c.setColor(SKYBLUE);
                c.fillRect(40,yPos-10-fs/2,2*fs*str.length()/3,fs+10);

                // draw the string
                c.setColor(BLACK);
                c.drawString(str,40,yPos);
                try {
                    Thread.sleep(16);
                } catch (Exception e) {};
            }
        }
    }

    // Outro constructor
    public Outro(Console con) {
        c = con;
    }
    // what the Thread calls upon start
    public void run() {
        // animates and draws the following onto the Console: 
        play("10/28/2019",26,145); // the date
        play("Celeste Luo",26,105); // name 
        play("The Birdfeeder",38,60); // title
    }
}