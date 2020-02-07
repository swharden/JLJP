import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.applet.*;
import java.text.*;

/** Graphical interface for the Jljp classes. */

public class Jljp extends JFrame {

	public Jljp() {
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		IonSetPanel is = new IonSetPanel();
		LJPPanel p = new LJPPanel(is, null);
		getContentPane().add(p);
	}

	public static void main(String[] args) {
		Jljp j = new Jljp();
		j.setTitle("JLJP by Doriano Brogioli");
		j.setVisible(true);
	}

}