/*
Celeste Luo
Ms. Krasteva
October 28, 2019
The driver class for the animation. Story line: It was a clear day, 
and birds of various species are happily feasting from the bird 
feeder. One day, a diseased bird arrives at and eats from the bird 
feeder, passing the disease to it. Afterwards, all birds that eat 
from the bird feeder become diseased as well; they die, falling to 
the ground. Eventually, birds cease to feed from the bird feeder.
*/
/*
CITATION OF EXTERNAL KNOWLEDGE:
synchronized threads -- Derek Zhang and Java Docs 
	https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html
if statements -- Java Docs
	https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html
*/
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class MyCreation {
	Console c; // the output console
	// draws the background
	public void background() {
		Background b = new Background(c);
		b.draw();
		birdfeeder();
		b.drawGrass();
	}
	// draws the birdfeeder
	public void birdfeeder() {
		BirdFeeder bf = new BirdFeeder(c);
		bf.draw();
	}
	// draws and animates the blue bird
	public void bluebird() {
		Bluebird b = new Bluebird(c);
		b.run();
	}
	// draws and animates the honeycreeper
	public void honey() {
		Honeycreeper h = new Honeycreeper(c);
		h.start();
		try {
			Thread.sleep(1000);
			cardinal();
			h.join(); // the honeycreeper and cardinal run concurrently
		} catch (Exception e){};
	}
	// draws and animates the sick bird
	public void sick() {
		SickBird sb = new SickBird(c);
		sb.run();
	}
	// draws and animates the three canaries
	public void canaries() {
		// local colour variables of the birds. While they are only used once, the colour of the canary is clear
		final Color GREENGREY = new Color(100, 140, 70);
		final Color MUTEDGREENGREY = new Color(140, 150, 130);
		final Color REDORANGE = new Color(255, 75, 0);
		final Color MUTEDREDORANGE = new Color(215, 130, 100);

		Canary cy = new Canary(c,720,45); // yellow canary
		Canary cg = new Canary(c,GREENGREY,MUTEDGREENGREY); // green canary
		Canary cr = new Canary(c,REDORANGE,MUTEDREDORANGE,714,40); // red-orange canary
		cy.start();
		try {
			Thread.sleep(500);
			cg.start();
			Thread.sleep(5000);
			cr.start();
			cr.join(); // the canaries run concurrently
		} catch (Exception e){};
	}
	// draws and animates the pink robin
	public void robin() {
		PinkRobin p = new PinkRobin(c);
		p.start();
		try {
			Thread.sleep(1000);
			p.join();
		} catch (Exception e){};
	}
	// draws and animates the cardinal
	public void cardinal() {
		Cardinal car = new Cardinal(c);
		car.start();
	}
	// method to create an Outro object and play the outro
	public void outro() {
		Outro o = new Outro(c);
		o.run();
	}
	// method to sleep a thread. Its purpose is to avoid writing the try/catch statement each time
	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {};
	}
	// creates a new Console window 
	public MyCreation() {
		c = new Console(25,80,"The Birdfeeder");
	}
	// the main method, where the animation is controlled
	public static void main(String [] args) { 
		MyCreation mc = new MyCreation(); // creates new instance of MyCreation, the driver method of the animation
		// draws the background, including the birdfeeder
		mc.background();
		// the healthy birds enter and exit
		// mc.bluebird();
		mc.sleep(800);
		mc.canaries();
		mc.sleep(800);
		// the sick bird enters and dies
		mc.sick();
		mc.sleep(800);
		// more birds enter and die
		mc.robin();
		mc.sleep(300);
		mc.honey();
		mc.sleep(2500);
		// the outro "screen"
		mc.outro();
	}
}
