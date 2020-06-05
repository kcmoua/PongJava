import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

import java.util.Random;
import java.util.Timer;

public class Pong extends Canvas
{
	Point delta;
	Ellipse2D.Double ball;
	Rectangle paddle1, paddle2;
	static int bluePoints = 0;
	static int redPoints = 0;
	
	public static void main( String[] args )
	{
		JFrame win = new JFrame("Pong");
		win.setSize(1010,735);
		win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		win.add( new Pong() );
		win.setVisible(true);
	}

	public Pong()
	{
		enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
		requestFocus();
				
		ball = new Ellipse2D.Double(500,350,20,20);
		delta = new Point(-5,5);
		paddle1 = new Rectangle(50,250,20,200);
		paddle2 = new Rectangle(940,250,20,200);
	
		Timer t = new Timer(true);
		t.schedule( new java.util.TimerTask()
		{
			public void run()
			{
				doStuff();
				repaint();
			}
		}, 10, 10);

	}

	public void paint( Graphics g )
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.black);
		g2.fill(ball);
		
		g2.setColor(Color.blue);
		g2.fill(paddle1);
		
		g2.setColor(Color.red);
		g2.fill(paddle2);
		// added drawString for showing the score
		g.drawString("Blue score: " + bluePoints, 30, 20);
		g.drawString("Red score: " + redPoints, 900, 20);
	}

	public void processKeyEvent(KeyEvent e)
	{
		if ( e.getID() == KeyEvent.KEY_PRESSED )
		{
			if ( e.getKeyCode() == KeyEvent.VK_W )
			{	// keeps the paddle in the screen (top side)
				if ( paddle1.y > 0 ) {
					paddle1.y -= 30; 
				} else {
					paddle1.y = 0;
				}
			}
			if ( e.getKeyCode() == KeyEvent.VK_S )
			{	// keeps the paddle in the screen (bottom side)
				if ( paddle1.y < 515 ) {
					paddle1.y += 30;
				} else {
					paddle1.y = 515;
				}
			}
			if ( e.getKeyCode() == KeyEvent.VK_UP )
			{	// keeps the paddle in the screen (top side)
				if ( paddle2.y > 0 ) {
					paddle2.y -= 30; 
				} else {
					paddle2.y = 0;
				}
			}
			if ( e.getKeyCode() == KeyEvent.VK_DOWN )
			{	// keeps the paddle in the screen (bottom side)
				if ( paddle2.y < 515 ) {
					paddle2.y += 30;
				} else {
					paddle2.y = 515;
				}
			}
		}
	}
	
	public void doStuff()
	{
		ball.x += delta.x;
		ball.y += delta.y;

		// and bounce if we hit top/bottom wall
		if ( ball.y < 0 || ball.y+20 > 700 )
			delta.y = -delta.y;	// only bounces off top and bottom walls
			
		// check if the ball is hitting the paddle
		if ( ball.intersects(paddle1) ) {
			delta.x = -delta.x;
		}
		if ( ball.intersects(paddle2) ) {
			delta.x = -delta.x;
		}
		// check for scoring
		if ( ball.x > 1000 )
		{
			bluePoints += 1;	// increments blue score
			ball.x = 500;
			ball.y = 350;
			delta = new Point(-5,5);
		}
		if ( ball.x < 0 )
		{
			redPoints += 1;	// implements red score
			ball.x = 500;
			ball.y = 350;
			delta = new Point(-5,5);
		}
	}
	
	public boolean isFocusable() { return true;	}
	
}