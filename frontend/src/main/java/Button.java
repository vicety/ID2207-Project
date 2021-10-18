import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import javax.swing.JButton;

/**
 *	Title: Button.java
 *	Description: Gui buttons class. The four corners of the button are curved.
 */
public class Button extends JButton  {
	 private Shape shape = null;
	 private Color color;
	 
	 /**
	  * Constructor. Set white as the default color.
	  * @param s	Text on the button.
	  */
	 public Button(String s) {
		 super(s);
		 color = new Color(255, 255, 255);
		 setContentAreaFilled(false);
		 setFocusPainted(false);
	 }
	 
	 /**
	  * Constructor. Could customize color.
	  * @param s	Text on the button.
	  * @param color	Color of the button
	  */
	 public Button(String s, Color color) {
		 super(s);
		 this.color = color;
		 setContentAreaFilled(false);
		 setFocusPainted(false);
	 }	 
	 
	 @Override
	 public void paintComponent(Graphics g) {
		 g.setColor(color);
		 g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
		 super.paintComponent(g);
	 }
	 
	 @Override
	 public void paintBorder(Graphics g) {
		 g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
	 }

}